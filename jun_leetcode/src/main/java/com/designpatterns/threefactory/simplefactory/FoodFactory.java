package com.designpatterns.threefactory.simplefactory;

import com.designpatterns.threefactory.factory.Food;

/**
 * 简单工厂模式(反射)
 * @author BaoZhou
 * @date 2018/12/27
 */
public class FoodFactory {
   public static Food FoodFactory(Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return (Food)Class.forName(clazz.getName()).newInstance();
    }
}