package com.jun.plugin.json.jackson2.pojo;

/**
 * Created by luozhen on 2018/6/21.
 */
public class MyValue {

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

    @Override
    public String toString() {
        return "MyValue{" +
            "name='" + name + '\'' +
            ", age=" + age +
            '}';
    }
}
