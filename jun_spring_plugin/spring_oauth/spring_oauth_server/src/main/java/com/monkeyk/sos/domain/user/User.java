package com.monkeyk.sos.domain.user;

import com.monkeyk.sos.domain.AbstractDomain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定义用户
 *
 * @author Shengzhao Li
 */
public class User extends AbstractDomain {


    private static final long serialVersionUID = -2921689304753120556L;


    private String username;
    private String password;

    private String phone;
    private String email;
    //Default user is initial when create database, do not delete
    private boolean defaultUser = false;

    private Date lastLoginTime;

    private List<Privilege> privileges = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, String phone, String email) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    public boolean defaultUser() {
        return defaultUser;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String phone() {
        return phone;
    }

    public String email() {
        return email;
    }

    public List<Privilege> privileges() {
        return privileges;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{username='").append(username).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", guid='").append(guid).append('\'');
        sb.append(", defaultUser='").append(defaultUser).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    public User phone(String phone) {
        this.phone = phone;
        return this;
    }


    public User username(String username) {
        this.username = username;
        return this;
    }


    public Date lastLoginTime() {
        return lastLoginTime;
    }

    public User lastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public User createTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }
}