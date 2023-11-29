package com.piggy.listener;

import com.piggy.config.MacroConfig;
import com.piggy.controller.MacroController;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {
    // 懒汉单例
    private static volatile GlobalKeyListener instance = null;
    public static GlobalKeyListener getInstance(){
        if (instance == null){
            synchronized (GlobalKeyListener.class){
                instance = new GlobalKeyListener();
            }
        }
        return instance;
    }

    /**
     * 键盘按下
     */
    public void nativeKeyPressed(NativeKeyEvent e) {
        // 按下ctrl
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            MacroConfig.ctrlIsPress = true;
        }
        // 切换武器
        MacroController.switchWeapon(e.getKeyCode());
    }

    /**
     * 键盘释放
     */
    public void nativeKeyReleased(NativeKeyEvent e) {
        // 松开ctrl
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL){
            MacroConfig.ctrlIsPress = false;
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
//        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

}
