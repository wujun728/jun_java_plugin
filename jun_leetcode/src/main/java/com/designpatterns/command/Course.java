package com.designpatterns.command;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class Course {
    String name;

    public Course(String name) {
        this.name = name;
    }

    public void open(){
        System.out.println(name+"开放");
    }

    public void close(){
        System.out.println(name + "关闭");
    }
}
