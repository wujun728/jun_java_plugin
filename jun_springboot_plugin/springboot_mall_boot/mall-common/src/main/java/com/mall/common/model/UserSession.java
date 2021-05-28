package com.mall.common.model;

import java.io.Serializable;

/**
 * 用户session信息
 * @author Wujun
 * @version 1.0
 * @create_at 2018/11/2421:31
 */
public class UserSession implements Serializable {

    private String sessionId;
    private String userId;
    private String loginName;
    private String mobile;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
