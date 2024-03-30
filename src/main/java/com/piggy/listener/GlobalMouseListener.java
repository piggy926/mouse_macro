package com.piggy.listener;

import com.piggy.config.MacroConfig;
import com.piggy.controller.MacroController;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

/**
 * 全局鼠标监听器
 */
public class GlobalMouseListener implements NativeMouseInputListener {
    // 懒汉单例
    private static volatile GlobalMouseListener instance = null;
    public static GlobalMouseListener getInstance(){
        if (instance == null){
            synchronized (GlobalMouseListener.class){
                instance = new GlobalMouseListener();
            }
        }
        return instance;
    }

    /**
     * 鼠标点击事件
     */
    public void nativeMouseClicked(NativeMouseEvent e) {
//        System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    /**
     * 鼠标按下
     */
    public void nativeMousePressed(NativeMouseEvent e) {
        int button = e.getButton();
        // 没有武器时，不触发
        if (null == MacroConfig.currentWeapon){
            return;
        }
        MacroConfig.running = true;
        if (button == 1 && MacroConfig.currentWeapon.getShootMode().equals("fullAuto")) {// 鼠标左键
            // 开火
            MacroController.openFire();
        } else if (button == 2 && MacroConfig.currentWeapon.getShootMode().equals("rapidFire")) {// 鼠标右键
            // rapidFire:快速点射
            MacroController.fixedFire();
        }

    }

    /**
     * 鼠标松开
     */
    public void nativeMouseReleased(NativeMouseEvent e) {
        if (MacroConfig.currentWeapon == null){
            MacroConfig.running = false;
            return;
        }
        int button = e.getButton();
        if (button == 1 && MacroConfig.currentWeapon.getShootMode().equals("fullAuto")) {
            MacroConfig.running = false;
        } else if (button == 2 && MacroConfig.currentWeapon.getShootMode().equals("rapidFire")) {
            MacroConfig.running = false;
        }
    }

    /**
     * 鼠标移动事件
     */
    public void nativeMouseMoved(NativeMouseEvent e) {
//        System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
    }

    /**
     * 鼠标滚动事件
     */
    public void nativeMouseDragged(NativeMouseEvent e) {
//        System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }
}
