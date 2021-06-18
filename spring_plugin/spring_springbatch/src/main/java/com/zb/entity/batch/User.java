package com.zb.entity.batch;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -5804106008997661279L;
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User() {
        // TODO Auto-generated constructor stub
    }

}
