package com.piggy.pojo;

import lombok.Data;

import java.util.List;

@Data
public class WeaponData {
    private String name;
    // 每发子弹上扬像素
    private List<Integer> bulletRises;
    // 子弹射速间隔
    private Integer bulletRate;
    // 蹲下时，子弹上扬的像素
    private List<Integer> crouch;

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

    public List<Integer> getCrouch() {
        return crouch;
    }

    public void setCrouch(List<Integer> crouch) {
        this.crouch = crouch;
    }
}
