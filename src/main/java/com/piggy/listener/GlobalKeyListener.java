package com.piggy.listener;

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
        // 切换武器
        MacroController.switchWeapon(e.getKeyCode(),true);
    }

    /**
     * 键盘按下
     */
    public void nativeKeyReleased(NativeKeyEvent e) {
//        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        MacroController.switchWeapon(e.getKeyCode(),false);
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
//        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

}
