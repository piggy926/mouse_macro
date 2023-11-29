package com.piggy;

import com.piggy.config.MacroConfig;
import com.piggy.listener.GlobalKeyListener;
import com.piggy.listener.GlobalMouseListener;
import com.piggy.util.ConfigLoader;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

public class MacroApplication {
    private static final Logger logger = LoggerFactory.getLogger(MacroApplication.class);

    public static void main(String[] args) {
        // 加载yml文件配置
        ConfigLoader.loadWeaponYml();
        if (MacroConfig.weaponRoot.isDynamicLoading()){
            ConfigLoader.watchFileChange();
        }
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
}
