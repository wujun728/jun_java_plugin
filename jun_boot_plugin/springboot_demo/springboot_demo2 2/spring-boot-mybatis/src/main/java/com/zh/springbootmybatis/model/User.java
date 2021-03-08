package com.zh.springbootmybatis.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author Wujun
 * @date 2019/5/29
 */
@Data
@ToString
public class User {

    private Integer id;

    private String name;

    private Integer age;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public User(Integer id, Integer age) {
        this.id = id;
        this.age = age;
    }
}
