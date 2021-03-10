package com.lyx.test;

/**
 * Created with IntelliJ IDEA. User: ASUS Date: 14-9-10 Time: 下午10:39 To change
 * this template use File | Settings | File Templates.
 */
public class Student {

    public String name;
    public int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" + "name='" + this.name + '\'' + ", age=" + this.age
                + '}';
    }
}