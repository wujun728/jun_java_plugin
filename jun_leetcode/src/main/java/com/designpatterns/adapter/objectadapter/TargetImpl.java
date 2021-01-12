package com.designpatterns.adapter.objectadapter;


/**
 * @author BaoZhou
 * @date 2018/12/28
 */
public class TargetImpl implements Target {
    @Override
    public void request() {

        System.out.println("TargetImpl的方法");
    }
}
