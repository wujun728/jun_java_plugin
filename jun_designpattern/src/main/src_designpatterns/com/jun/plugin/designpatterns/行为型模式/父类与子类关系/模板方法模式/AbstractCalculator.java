package com.jun.plugin.designpatterns.行为型模式.父类与子类关系.模板方法模式;


/**
 * 
 * 在AbstractCalculator类中定义一个主方法calculate，calculate()调用spilt()等，Plus继承AbstractCalculator类，通过对AbstractCalculator的调用实现对子类的调用
 * 
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 下午4:44:10
 */
public abstract class AbstractCalculator {

    /* 主方法，实现对本类其它方法的调用 */
    public final int calculate(String exp, String opt) {
        int array[] = split(exp, opt);
        return calculate(array[0], array[1]);
    }

    //被子类重写的方法
    abstract public int calculate(int num1, int num2);

    
    //解析获取数值
    public int[] split(String exp, String opt) {
        String array[] = exp.split(opt);
        int arrayInt[] = new int[2];
        arrayInt[0] = Integer.parseInt(array[0]);
        arrayInt[1] = Integer.parseInt(array[1]);
        return arrayInt;
    }
}