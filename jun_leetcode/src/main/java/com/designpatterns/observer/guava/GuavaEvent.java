package com.designpatterns.observer.guava;

import com.google.common.eventbus.Subscribe;

/**
 * @author BaoZhou
 * @date 2019/1/2
 */
public class GuavaEvent {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Subscribe
    public void subcribe(Question question) {
        System.out.println(question.getUserName() + "在" + name + "提出了一个问题："+question.getContent());
    }
}
