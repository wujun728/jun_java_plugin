package com.designpatterns.prototype;

import java.util.Date;

/**
 * 原型模式
 * @author BaoZhou
 * @date 2018/12/28
 */
public class Shape implements Cloneable {
    String type;
    String color;
    Date date;
    Brand brand;

    public Shape() {
        System.out.println("构造器");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        System.out.println("浅拷贝");
        return super.clone();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    @Override
    public String toString() {
        return "Shape{" +
                "type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", date=" + date +
                ", brand=" + brand +
                '}';
    }
}
