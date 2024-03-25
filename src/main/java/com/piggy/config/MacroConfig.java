package com.piggy.config;

import com.piggy.pojo.WeaponData;
import com.piggy.pojo.WeaponRoot;

/**
 * 宏全局参数
 */
public class MacroConfig {

    // 全局武器配置
    public static WeaponRoot weaponRoot = null;
    // 当前武器配置
    public static WeaponData currentWeapon = null;
    // 鼠标左键是否按下
    public static boolean running = false;
    // ctrl是否按下
    public static boolean ctrlIsPress = false;
    // alt是否按下
    public static boolean altIsPress = false;

}
