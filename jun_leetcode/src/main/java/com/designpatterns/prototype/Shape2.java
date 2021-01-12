package com.designpatterns.prototype;

import java.util.Date;

/**
 * 原型模式
 *
 * @author BaoZhou
 * @date 2018/12/28
 */
public class Shape2 implements Cloneable {
    public Shape2() {
        System.out.println("构造器");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;
    String color;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    Date date;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        //深拷贝
        System.out.println("clone");
        Shape2 shape2 = (Shape2) super.clone();
        shape2.date = (Date) shape2.date.clone();
        return shape2;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Shape2{" +
                "type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", date=" + date +
                '}';
    }
}
