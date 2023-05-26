package com.jun.entity;

import java.io.Serializable;
import java.util.Date;

/*************************
 *@ClassName User
 *@Description 用户实体类
 *@Author yejf
 *@Date 2019-11-29 8:14
 *@Version 1.0
 */
public class User implements Serializable {

    private Integer id;

    private String name;

    private String password;

    private Date enrollDate;

    private String realName;
    //

    public String toString() {
        return "";
    }
}
