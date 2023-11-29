package com.piggy.pojo;

import java.util.List;

public class WeaponData {
    private String name;
    // 每发子弹上扬像素
    private List<Integer> bulletRises;
    // 子弹射速间隔
    private Integer bulletRate;

    // getter 和 setter 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getBulletRises() {
        return bulletRises;
    }

    public void setBulletRises(List<Integer> bulletRises) {
        this.bulletRises = bulletRises;
    }

    public Integer getBulletRate() {
        return bulletRate;
    }

    public void setBulletRate(Integer bulletRate) {
        this.bulletRate = bulletRate;
    }
}
