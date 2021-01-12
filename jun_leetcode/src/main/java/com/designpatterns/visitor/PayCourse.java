package com.designpatterns.visitor;

import com.designpatterns.observer.sample1.Teacher;

/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public class PayCourse implements Course {
    private int price;

    public String getName() {
        return name;
    }

    public int getPv() {
        return pv;
    }

    private String name;
    private int pv;

    public PayCourse(int price, String name, int pv) {
        this.price = price;
        this.name = name;
        this.pv = pv;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
