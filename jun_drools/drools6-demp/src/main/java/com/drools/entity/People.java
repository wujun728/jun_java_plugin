package com.drools.entity;

/**
 * Created by LANCHUNQIAN on 2016/10/14.
 */
public class People {
    String name;
    int age;
    int phone;

    public People(String name, int age, int phone){
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public People(){}

    public String toString(){
        return "name = " + name + "age = " + age + "phone = " + phone;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

}
