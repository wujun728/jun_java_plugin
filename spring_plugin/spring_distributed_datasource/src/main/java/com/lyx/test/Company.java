package com.lyx.test;

/**
 * Created by Lenovo on 2014/12/4.
 */

@MyClassAnnotation(uri = "clazzsd", desc = "you")
public class Company {

    @MyFieldAnnotation(uri = "sdsd", desc = "uuuu")
    private String name;
    @MyFieldAnnotation(uri = "jell;o", desc = "uusdsduu")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
