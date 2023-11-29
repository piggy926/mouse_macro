package com.piggy.controller;

import com.piggy.config.MacroConfig;
import com.piggy.pojo.WeaponData;
import com.piggy.util.MyRobot;
import com.piggy.util.MacroUtil;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 宏控制器
 * 监控键盘组合键
 * ctrl+数字键，选择武器（数字0清除武器选择）
 * 监控鼠标左键，判断当前选择的武器，触发不同的鼠标动作
 */
public class MacroController {
    private static final Logger logger = LoggerFactory.getLogger(MacroController.class);

    /**
     * 开火！
     *
     * tips:鼠标事件通常是在事件分派线程（EDT）中处理的，
     * 如果在鼠标事件处理方法中直接执行长时间运行的操作（如while循环），这可能会阻塞EDT
     * 使用子线程将其从EDT中释放出来
     */
    public static void openFire(){
        // 没有武器时，不触发
        if (null == MacroConfig.currentWeapon){
            return;
        }
        MacroConfig.running = true;
        CompletableFuture.runAsync(() -> {
            // 每发子弹上扬像素
            List<Integer> bulletRises = MacroConfig.currentWeapon.getBulletRises();
            // 子弹射速间隔
            int bulletRate = MacroConfig.currentWeapon.getBulletRate();
            for (int rise : bulletRises) {
                // 是否偏移x轴
                int offset = 0;
                if (MacroConfig.weaponRoot.isxOffset()){
                    offset = MacroUtil.getRandomOffset();
                }
                // 调用机器人
                MyRobot.getInstance().move(offset,rise);
                try {
                    Thread.sleep(bulletRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!MacroConfig.running) break;
            }
        });
    }


    /**
     * 切换武器
     * @param code 按键编码
     */
    public static void switchWeapon(int code){
        // 武器归零
        if (MacroConfig.ctrlIsPress && code == NativeKeyEvent.VC_0) {
            logger.info("武器配置已清空!");
            MacroConfig.currentWeapon = null;
            return;
        }
        // 切换武器配置
        if (MacroConfig.ctrlIsPress && code <= NativeKeyEvent.VC_0 && code >= NativeKeyEvent.VC_1){
            Map<Integer, WeaponData> weapon = MacroConfig.weaponRoot.getWeapon();
            if (code - 1 > weapon.size()){
                logger.info("未配置该武器！");
                return;
            }
            MacroConfig.currentWeapon = weapon.get(code - 1);
            logger.info("当前武器已切换成{}",MacroConfig.currentWeapon.getName());
        }
    }

}
