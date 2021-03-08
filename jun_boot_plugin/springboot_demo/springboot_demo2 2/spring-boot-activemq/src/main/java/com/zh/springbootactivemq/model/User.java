package com.zh.springbootactivemq.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Wujun
 * @date 2019/5/29
 */
@Data
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 3466562964054157094L;

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
