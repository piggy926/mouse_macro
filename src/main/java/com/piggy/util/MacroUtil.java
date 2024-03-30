package com.piggy.util;

import java.util.Random;

public class MacroUtil {

    private static Random random = null;

    /**
     * 懒汉单例
     */
    static {
        synchronized (MacroUtil.class){
            if (random == null){
                random = new Random();
            }
        }
    }

    /**
     * 获取随机偏移量，用于x轴
     */
    public static int getRandomOffset() {
        return random.nextBoolean() ? 1 : -1;
    }

    /**
     * 获取0-3ms的延迟
     */
    public static int getRandomDelay() {
        return random.nextInt(4);
    }
}
