package com.jun.plugin.utils.pojo;


import java.io.Serializable;

public class Location implements Serializable {


    private  String city;

    public Location() {

    }

    public Location(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                '}';
    }
}
