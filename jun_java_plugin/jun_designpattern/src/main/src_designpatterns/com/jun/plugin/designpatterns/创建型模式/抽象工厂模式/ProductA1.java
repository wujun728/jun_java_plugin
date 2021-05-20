package com.jun.plugin.designpatterns.创建型模式.抽象工厂模式;

/**
 * 具体产品类 A1，继承抽象类 Product
 * 
 * 作者: zhoubang 
 * 日期：2015年10月27日 上午10:31:26
 */
public class ProductA1 extends Product {

    @Override
    public void dosomething() {
        System.out.println("这是产品A1");
    }

}
