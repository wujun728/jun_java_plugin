package com.designpatterns.adapter.objectadapter;

/**
 * @author BaoZhou
 * @date 2018/12/28
 */
public class Adapter implements Target {
    private Adaptee adaptee = new Adaptee();
    @Override
    public void request() {
        adaptee.adapteeRequest();
    }
}
