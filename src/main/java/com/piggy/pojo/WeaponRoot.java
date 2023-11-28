package com.piggy.pojo;

import java.util.Map;

public class WeaponRoot {
    private Map<Integer, WeaponData> weapon;
    private boolean xOffset;

    // getter 和 setter 方法
    public Map<Integer, WeaponData> getWeapon() {
        return weapon;
    }

    public void setWeapon(Map<Integer, WeaponData> weapon) {
        this.weapon = weapon;
    }

    public boolean isxOffset() {
        return xOffset;
    }

    public void setxOffset(boolean xOffset) {
        this.xOffset = xOffset;
    }
}
