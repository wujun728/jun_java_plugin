package com.redis.model;

import java.io.Serializable;

/**
 * @author: jujun chen
 * @description:
 * @date: 2019/2/14
 */
public class Person implements Serializable{
    private String name;

    private String age;

    private String sex;

    public Person(String name, String age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
