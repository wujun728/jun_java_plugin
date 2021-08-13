package com.mycompany.myproject.spring.xml;

/**
 * @auther barry
 * @date 2019/2/18
 */
public class Car {
    private String brand;
    private double price;
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "Car [brand=" + brand + ", price=" + price + "]";
    }
}
