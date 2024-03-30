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
    // 射击模式，fullAuto:全自动扫射, rapidFire:快速点射, burstFire: 三连发模式
    private String shootMode;
}
