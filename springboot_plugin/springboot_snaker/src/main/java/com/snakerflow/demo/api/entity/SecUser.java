package com.snakerflow.demo.api.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuxzh
 * @since 2018-12-20
 */
public class SecUser extends Model<SecUser> {

    private static final long serialVersionUID = 1L;

    private String address;

    private Integer age;

    private String email;

    private String enabled;

    private String fullname;

    private String password;

    @TableField("plainPassword")
    private String plainPassword;

    private String salt;

    private String sex;

    private Integer type;

    private String username;

    private Integer org;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getOrg() {
        return org;
    }

    public void setOrg(Integer org) {
        this.org = org;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "SecUser{" +
        "address=" + address +
        ", age=" + age +
        ", email=" + email +
        ", enabled=" + enabled +
        ", fullname=" + fullname +
        ", password=" + password +
        ", plainPassword=" + plainPassword +
        ", salt=" + salt +
        ", sex=" + sex +
        ", type=" + type +
        ", username=" + username +
        ", org=" + org +
        "}";
    }
}
