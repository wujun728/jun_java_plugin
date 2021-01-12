package com.designpatterns.threefactory.factory;

/**
 * @author BaoZhou
 * @date 2018/12/27
 */
public class VegetableFood implements Food {
    @Override
    public String type() {
        return "vegetable";
    }
}
