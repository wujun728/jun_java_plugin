package com.designpatterns.visitor;

/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public class FreeCourse implements Course {
    private int price;
    private String name;

    public FreeCourse(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
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
