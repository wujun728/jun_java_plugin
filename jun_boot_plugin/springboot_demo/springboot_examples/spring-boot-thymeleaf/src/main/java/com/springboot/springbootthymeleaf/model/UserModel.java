package com.springboot.springbootthymeleaf.model;

/**
 * @Author: shiyao.wei
 * @Date: 2019/9/16 16:49
 * @Version: 1.0
 * @Desc:
 */
public class UserModel {
    private Long id;
    private String name;
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
