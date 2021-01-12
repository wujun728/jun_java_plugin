package com.java8.lambda;

/**
 * @author BaoZhou
 * @date 2018/7/29
 */
public class Employee {
    String name;
    Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Employee(String name, Integer age) {

        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "lambda.Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
