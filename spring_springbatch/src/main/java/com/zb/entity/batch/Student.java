package com.zb.entity.batch;

import java.io.Serializable;


public class Student implements Serializable {

    private static final long serialVersionUID = 938168315365875166L;
    private int id;
    private String name;
    private int age;
    private int score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", age=" + age
                + ", score=" + score + "]";
    }

}
