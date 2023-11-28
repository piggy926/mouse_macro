package com.piggy.controller;

import com.piggy.util.ConfigLoader;
import com.piggy.listener.GlobalKeyListener;
import com.piggy.listener.GlobalMouseListener;
import com.piggy.pojo.WeaponData;
import com.piggy.pojo.WeaponRoot;
import com.piggy.util.MyRobot;
import com.piggy.util.MacroUtil;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

/**
 * 宏控制器
 * 监控键盘组合键
 * ctrl+数字键，选择武器（数字0清除武器选择）
 * 监控鼠标左键，判断当前选择的武器，触发不同的鼠标动作
 */
public class MacroController {
    private static final Logger logger = LoggerFactory.getLogger(MacroController.class);

    // 全局武器配置
    private static WeaponRoot weaponRoot;
    // 当前武器配置
    private static WeaponData currentWeapon;
    private static boolean running = false;
    private static boolean ctrlIsPress = false;

    public static void main(String[] args) {
        // 加载yml文件配置
        weaponRoot = ConfigLoader.loadWeaponYml();
        // 注册全局钩子
        try {
            logger.info("注册全局钩子");
            GlobalScreen.registerNativeHook();
            // 设置全局钩子日志不打印
            java.util.logging.Logger hookLog = java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
            hookLog.setLevel(Level.OFF);
            // 设置鼠标监控
            GlobalMouseListener mouseListener = GlobalMouseListener.getInstance();
            GlobalScreen.addNativeMouseListener(mouseListener);
            // 设置键盘监控
            GlobalKeyListener keyListener = GlobalKeyListener.getInstance();
            GlobalScreen.addNativeKeyListener(keyListener);
        } catch (NativeHookException e) {
            logger.error("注册全局钩子失败:{}",logger);
        }
    }

    /**
     * 鼠标左键按下
     *
     * tips:鼠标事件通常是在事件分派线程（EDT）中处理的，
     * 如果在鼠标事件处理方法中直接执行长时间运行的操作（如while循环），这可能会阻塞EDT
     * 使用子线程将其从EDT中释放出来
     */
    public static void leftMousePressed(){
        // 没有武器时，不触发
        if (null == currentWeapon){
            return;
        }
        running = true;
        CompletableFuture.runAsync(() -> {
            List<Integer> bulletRises = currentWeapon.getBulletRises();
            int bulletRate = currentWeapon.getBulletRate();
            for (int rise : bulletRises) {
                int offset = 0;
                if (weaponRoot.isxOffset()){
                    offset = MacroUtil.getRandomOffset();
                }
                // 调用机器人
                MyRobot.getInstance().move(offset,rise);
                try {
                    Thread.sleep(bulletRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!running) break;
            }
        });
    }

    public static void leftMouseReleased(){
        running = false;
//        logger.info("鼠标左键已释放");
    }


    /**
     * 切换武器
     * @param code 按键编码
     * @param isPressed 是否按下
     */
    public static void switchWeapon(int code,boolean isPressed){
        // 按下ctrl
        if (code == NativeKeyEvent.VC_CONTROL && isPressed) {
            ctrlIsPress = true;
        }
        if (code == NativeKeyEvent.VC_CONTROL && !isPressed){
            ctrlIsPress = false;
        }
        // 武器归零
        if (ctrlIsPress && code == NativeKeyEvent.VC_0 && isPressed) {
            logger.info("武器归零");
            currentWeapon = null;
            return;
        }
        // 切换武器配置
        if (ctrlIsPress && code <= NativeKeyEvent.VC_0 && code >= NativeKeyEvent.VC_1 && isPressed){
            Map<Integer, WeaponData> weapon = weaponRoot.getWeapon();
            if (code - 1 > weapon.size()){
                logger.info("未配置该武器！");
                return;
            }
            currentWeapon = weapon.get(code - 1);
            logger.info("当前武器已切换成{}",currentWeapon.getName());
        }
    }

    public static void rightMousePressed() {
//        logger.info("鼠标右键键已按下");
    }

    public static void rightMouseReleased() {
//        logger.info("鼠标右键已释放");
    }
}
