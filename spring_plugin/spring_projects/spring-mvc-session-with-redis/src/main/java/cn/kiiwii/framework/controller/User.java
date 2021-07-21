package cn.kiiwii.framework.controller;

import java.io.Serializable;

/**
 * Created by zhong on 2016/9/18.
 */
public class User implements Serializable{
    private String name;
    private int age;
    private float heigth;

    public User(String name, int age, float heigth) {
        this.name = name;
        this.age = age;
        this.heigth = heigth;
    }

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

    public float getHeigth() {
        return heigth;
    }

    public void setHeigth(float heigth) {
        this.heigth = heigth;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", heigth=" + heigth +
                '}';
    }
}
