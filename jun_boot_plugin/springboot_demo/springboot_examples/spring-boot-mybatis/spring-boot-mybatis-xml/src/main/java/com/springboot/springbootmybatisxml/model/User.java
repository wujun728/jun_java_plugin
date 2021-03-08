package com.springboot.springbootmybatisxml.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Version: 1.0
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;

    private String nickName;

    private int age;

    private Date createDate;

}
