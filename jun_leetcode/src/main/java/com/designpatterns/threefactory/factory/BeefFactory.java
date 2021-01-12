package com.designpatterns.threefactory.factory;

/**
 * 工厂模式
 * @author BaoZhou
 * @date 2018/12/27
 */
public class BeefFactory {
    public Food createFood() {
        return new BeefFood();
    }
}
