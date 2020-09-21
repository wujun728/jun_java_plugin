package com.jun.plugin.designpatterns.创建型模式.抽象工厂模式;

/**
 * 抽象工厂类
 * 
 * 作者: zhoubang 
 * 日期：2015年10月27日 上午10:35:24
 */
public abstract class Creater {

    //抽象方法————创建产品A对象
    public abstract Product createProductA();

    //抽象方法————创建产品B对象
    public abstract Product createProductB();
}
