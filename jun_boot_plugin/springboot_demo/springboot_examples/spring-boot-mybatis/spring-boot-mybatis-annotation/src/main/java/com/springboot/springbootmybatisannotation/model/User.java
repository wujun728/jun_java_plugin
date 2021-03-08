package com.springboot.springbootmybatisannotation.model;

import lombok.Data;

import java.util.Date;

/**
 * @Version: 1.0
 * @Desc:
 */
@Data
public class User {
    private String id;

    private String nickName;

    private int age;

    private Date createDate;

}
