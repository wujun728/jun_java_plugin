package com.designpatterns.prototype;

/**
 * @author BaoZhou
 * @date 2018/12/28
 */
public class Brand {
    public Brand(String city, String name) {
        this.city = city;
        this.name = name;
    }

    String city;
    String name;

    @Override
    public String toString() {
        return "Brand{" +
                "city='" + city + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
