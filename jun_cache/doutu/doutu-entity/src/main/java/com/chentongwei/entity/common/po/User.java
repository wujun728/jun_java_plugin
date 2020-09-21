package com.chentongwei.entity.common.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @author TongWei.Chen 2017-6-10 15:07:24.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Long id;
    /** 登录名 */
    private String loginName;
    /** 密码 */
    private String password;
    /** 性别 1：男 2：女 3：保密*/
    private Integer gender;
    /** 出生日期 */
    private Date birthday;
    /** 头像 */
    private String avatar;
    /** 电子邮箱 */
    private String email;
    /** 状态 0：禁用 1：正常 */
    private Integer status;
    /** openId */
    private String openId;
    /** token */
    private String accessToken;
    /** 是否是管理员 0：普通用户 1：管理员 */
    private Integer isAdmin;
    /** 注册时间 */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer isAdmin() {
        return isAdmin;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
