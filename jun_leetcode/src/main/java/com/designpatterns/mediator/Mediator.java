package com.designpatterns.mediator;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class Mediator {
    public static void doSomething(User user,Action action){
        System.out.println(user.getName()+action.doSomething());
    }
}
