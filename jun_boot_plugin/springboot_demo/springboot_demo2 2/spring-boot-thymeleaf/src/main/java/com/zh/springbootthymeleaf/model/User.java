package com.zh.springbootthymeleaf.model;

import lombok.Data;

/**
 * @author Wujun
 * @date 2019/5/29
 */
@Data
public class User {

    private Integer id;

    private String name;

    private Integer age;

    public User() {}

    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
