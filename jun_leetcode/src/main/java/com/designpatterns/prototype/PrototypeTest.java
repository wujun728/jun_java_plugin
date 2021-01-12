package com.designpatterns.prototype;

import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * 原型模式
 * @author BaoZhou
 * @date 2018/12/28
 */
public class PrototypeTest {
    @Test
    void test() throws CloneNotSupportedException {
        Shape shape = new Shape();
        shape.setColor("red");
        shape.setType("初始化模版");
        for (int i = 0; i < 10; i++) {
            Shape shape1 = (Shape) shape.clone();
            shape1.setColor(i + "");
            shape1.setType("triangle   " + i);
            ShapeUtil.discribe(shape1);
        }

    }

    /**
     * 深拷贝
     *
     * @throws CloneNotSupportedException
     */
    @Test
    void test2() throws CloneNotSupportedException {
        Shape2 shape = new Shape2();
        shape.setColor("red");
        shape.setType("深拷贝");
        shape.setDate(new Date(0L));
        Shape2 clone = (Shape2) shape.clone();
        System.out.println(shape);
        System.out.println(clone);
        shape.setColor("unknown");
        shape.getDate().setTime(11564564L);
        System.out.println(shape);
        System.out.println(clone);
    }

    /**
     * 浅拷贝
     *
     * @throws CloneNotSupportedException
     */
    @Test
    void test3() throws CloneNotSupportedException {
        Shape shape = new Shape();
        shape.setColor("red");
        shape.setType("浅拷贝");
        shape.setDate(new Date(0L));
        shape.setBrand(new Brand("A","B"));
        Shape clone = (Shape) shape.clone();
        System.out.println(shape);
        System.out.println(clone);
        shape.setColor("unknown");
        shape.getDate().setTime(11564564L);
        shape.getBrand().setCity("HangZhou");
        System.out.println(shape);
        System.out.println(clone);
    }
}
