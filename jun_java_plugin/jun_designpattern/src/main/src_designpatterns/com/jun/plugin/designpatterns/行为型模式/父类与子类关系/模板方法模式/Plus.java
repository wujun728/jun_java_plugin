package com.jun.plugin.designpatterns.行为型模式.父类与子类关系.模板方法模式;

/**
 * 加法操作类，继承抽象类AbstractCalculator，具体实现由该类实现
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 下午4:46:16
 */
public class Plus extends AbstractCalculator {

    //实现calculate方法，这里是自己处理加法运算，返回结果
    @Override
    public int calculate(int num1, int num2) {
        return num1 + num2;
    }
}