package com.designpatterns.mediator;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class User {
    String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void doSomething(Action action) {
        Mediator.doSomething(this, action);
    }
}
