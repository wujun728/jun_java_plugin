package com.jun.plugin.picturemanage.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/11/1 16:00
 */
@Data
public class UserEntity implements Serializable {

    private String username;

    private String password;

}
