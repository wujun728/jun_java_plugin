package com.zccoder.java.book2.ch3.basic;

/**
 * <br>
 * 标题: 常量<br>
 * 描述: 3.4.2<br>
 * 时间: 2018/10/17<br>
 *
 * @author zc
 */
public class Constants {

    private static final double CM_PER_INCH = 2.54;

    public static void main(String[] args) {
        double paperWidth = 8.5;
        double paperHeight = 11;
        System.out.println("Paper size in centimeters:" + paperWidth * CM_PER_INCH + " by " + paperHeight * CM_PER_INCH);
    }
}
