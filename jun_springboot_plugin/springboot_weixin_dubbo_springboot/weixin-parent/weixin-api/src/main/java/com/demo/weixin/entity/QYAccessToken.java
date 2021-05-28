package com.demo.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/*
 * @Description: 企业号accessToken
 * @version V1.0
 */
public class QYAccessToken extends BaseWeixinResponse implements Serializable {

    private static final long serialVersionUID = -5190144351841793061L;

    @JSONField(name = "access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
