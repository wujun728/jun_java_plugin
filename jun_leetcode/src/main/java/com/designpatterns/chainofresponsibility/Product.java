package com.designpatterns.chainofresponsibility;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class Product {
    private String info;
    private String name;
    private Integer price;

    public Product(String info, String name, Integer price) {
        this.info = info;
        this.name = name;
        this.price = price;
    }


    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

}
