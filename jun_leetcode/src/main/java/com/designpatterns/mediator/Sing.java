package com.designpatterns.mediator;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class Sing implements Action<String>{

    @Override
    public String doSomething() {
        return "唱歌";
    }
}
