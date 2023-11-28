package com.piggy.pojo;

import java.util.List;

public class WeaponData {
    private String name;
    private List<Integer> bulletRises;
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
