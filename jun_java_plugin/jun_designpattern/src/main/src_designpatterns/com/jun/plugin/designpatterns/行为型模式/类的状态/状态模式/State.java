package com.jun.plugin.designpatterns.行为型模式.类的状态.状态模式;

/**
 * 状态类的核心类
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午9:42:53
 */
public class State {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //第一种状态所要执行的方法
    public void method1() {
        System.out.println("执行第一个状态选择!");
    }

    //第二种状态所要执行的方法
    public void method2() {
        System.out.println("执行第二个状态选择!");
    }
}