package com.itstyle.restful.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 实体类
 */
@Data
@Entity
public class User {
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, length = 20)
    private Long userId;

    /**
     * 用户名
     */
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    /**
     * 密码
     */
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    /**
     * 姓名(昵称)
     */
    @Column(name = "nickname", length = 50)
    private String nickname;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 100)
    private String email;

    /**
     * 手机号
     */
    @Column(name = "mobile", length = 100)
    private String mobile;

}
