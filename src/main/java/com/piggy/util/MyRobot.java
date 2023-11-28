package com.piggy.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class MyRobot {
    private static final Logger logger = LoggerFactory.getLogger(MyRobot.class);

    private static volatile MyRobot instance = null;
    private static volatile Robot robot = null;
    // 懒汉单例
    public static MyRobot getInstance(){
        if (instance == null){
            synchronized (MyRobot.class){
                instance = new MyRobot();
                try {
                    robot = new Robot();
                } catch (AWTException e) {
                    logger.error("机器人创建失败：",e);
                }
            }
        }
        return instance;
    }

    public void move(int x, int y){
        // 获取当前鼠标坐标
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point point = pointerInfo.getLocation();
        int currentX = (int) point.getX();
        int currentY = (int) point.getY();

        // 鼠标移动到新的位置
        robot.mouseMove(currentX + x, currentY + y);
    }
}
