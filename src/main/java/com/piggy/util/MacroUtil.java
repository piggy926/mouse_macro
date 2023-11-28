package com.piggy.util;

import java.util.Random;

public class MacroUtil {

    /**
     * 获取随机偏移量，用于x轴
     */
    public static int getRandomOffset() {
        Random random = new Random();
        return random.nextBoolean() ? 1 : -1;
    }
}
