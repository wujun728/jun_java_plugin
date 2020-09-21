package com.jun.plugin.designpatterns.结构型模式.适配器模式.类的适配器模式;

/**
 * 适配器。
 * 
 * 作者: zhoubang 日期：2015年10月27日 下午5:04:41
 */
public class Adapter extends Source implements Targetable {

    @Override
    public void method2() {
        System.out.println("这是一个目标的方法。");
    }

}
