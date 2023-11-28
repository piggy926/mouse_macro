package com.piggy.listener;

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
        if (button == 1) {
            MacroController.leftMousePressed();
//        } else if (button == 2) {
//            MacroController.rightMousePressed();
        }

    }

    /**
     * 鼠标松开
     */
    public void nativeMouseReleased(NativeMouseEvent e) {
        int button = e.getButton();
        if (button == 1) {
            MacroController.leftMouseReleased();
//        } else if (button == 2) {
//            MacroController.rightMouseReleased();
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
