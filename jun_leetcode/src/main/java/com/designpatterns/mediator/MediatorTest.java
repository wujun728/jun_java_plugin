package com.designpatterns.mediator;

import org.junit.jupiter.api.Test;

/**
 * 中介者模式
 * @author BaoZhou
 * @date 2019/1/4
 */
public class MediatorTest {
    @Test
    void test(){
        User baozhou = new User("baozhou");
        User chenweiting = new User("陈伟霆");
        Draw draw =new Draw();
        Sing sing = new Sing();
        baozhou.doSomething(draw);
        baozhou.doSomething(sing);
        chenweiting.doSomething(sing);
        chenweiting.doSomething(draw);
    }
}
