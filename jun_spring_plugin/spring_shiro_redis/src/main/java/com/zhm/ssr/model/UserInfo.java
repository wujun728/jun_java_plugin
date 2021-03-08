package com.zhm.ssr.model;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

/**
 * Created by zhm on 2015/7/10.
 */
public class UserInfo implements Serializable{

    private long id;
    private String username;
    private String password;
    private String email;
    private String mobile;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
