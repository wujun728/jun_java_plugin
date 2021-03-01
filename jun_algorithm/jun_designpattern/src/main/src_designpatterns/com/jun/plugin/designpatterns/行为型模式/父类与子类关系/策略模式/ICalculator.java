package com.jun.plugin.designpatterns.行为型模式.父类与子类关系.策略模式;

/**
 * 计算接口，每个计算类型都实现该接口
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 下午4:21:49
 */
public interface ICalculator {
    public int calculate(String exp);
}