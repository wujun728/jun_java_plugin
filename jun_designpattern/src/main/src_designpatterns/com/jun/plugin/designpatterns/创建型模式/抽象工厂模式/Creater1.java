package com.jun.plugin.designpatterns.创建型模式.抽象工厂模式;

/**
 * 具体工厂类,继承抽象工厂Creater
 * 
 * 作者: zhoubang 
 * 日期：2015年10月27日 上午10:37:41
 */
public class Creater1 extends Creater {

    //创建产品A1
    @Override
    public Product createProductA() {
        return new ProductA1();
    }

    //创建产品B1
    @Override
    public Product createProductB() {
        return new ProductB1();
    }

}
