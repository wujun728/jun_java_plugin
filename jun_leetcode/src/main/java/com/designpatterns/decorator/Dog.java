package com.designpatterns.decorator;

/**
 * @author BaoZhou
 * @date 2018/12/28
 */
public class Dog implements AbstractObject {


    @Override
    public String property() {
        return "狗";
    }

    @Override
    public int weight() {
        return 10;
    }
}
