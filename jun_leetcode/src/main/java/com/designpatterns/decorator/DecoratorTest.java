package com.designpatterns.decorator;

import org.junit.jupiter.api.Test;

/**
 * 装饰模式
 * @author BaoZhou
 * @date 2018/12/28
 */
public class DecoratorTest {

    @Test
    void test() {
        AbstractObject dog = new Dog();
        dog = new ClotheDecorate(dog);
        dog = new FeatherDecorate(dog);
        dog = new MasterDecorate("小明",dog);
        System.out.println(dog.property()+" 重量是："+dog.weight());
    }
}
