package com.demo.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 微信公众号token
 */
public class WeixinAccessToken extends BaseWeixinResponse{

    private static final long serialVersionUID = -8282326095784761548L;
    private String appId;

    @JSONField(name="access_token")
    private String accessToken;

    @JSONField(name = "expires_in")
    private long expiresInSecond;

    private Date createTime = new Date();

    public WeixinAccessToken() {
    }

    public WeixinAccessToken(String accessToken, long expiresInSecond) {
        this.accessToken = accessToken;
        this.expiresInSecond = expiresInSecond;
    }

    // 是否已经过期
    public boolean isExpires() {
        if(accessToken == null || accessToken.trim().length() <= 0){
            return true;
        }

        long currentTimeSecond = (new Date()).getTime() / 1000;
        long createTimeSecond = createTime.getTime() / 1000;

        long pastSecond = currentTimeSecond - createTimeSecond;

        // 使用700秒保险
        boolean expires = ((pastSecond + 700) > expiresInSecond);

        return expires ;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresInSecond() {
        return expiresInSecond;
    }

    public void setExpiresInSecond(long expiresInSecond) {
        this.expiresInSecond = expiresInSecond;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
