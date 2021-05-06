package com.jun.plugin.designpatterns.行为型模式.通过中间类.中介者模式;

public class User2 extends User {

    public User2(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void work() {
        System.out.println("user2 exe!");
    }
}