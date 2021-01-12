package com.designpatterns.adapter.classAdapter;

/**
 * 享元模式
 * @author BaoZhou
 * @date 2018/12/28
 */
public class Adapter extends  Apdatee implements Target {
    @Override
    public void request() {
       super.adapteeRequest();
    }
}
