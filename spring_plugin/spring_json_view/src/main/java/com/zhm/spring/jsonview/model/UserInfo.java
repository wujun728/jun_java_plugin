package com.zhm.spring.jsonview.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Created by zhm on 2015/7/13.
 */
public class UserInfo {
    private int id;
    private String username;
    private String password;

    public UserInfo(int id, String username, String password) {
        this.id=id;
        this.username=username;
        this.password=password;
    }
    @JsonView(View.UserInfoWithOutPassword.class)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @JsonView(View.UserInfoWithOutPassword.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @JsonView(View.UserInfoWithPassword.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
