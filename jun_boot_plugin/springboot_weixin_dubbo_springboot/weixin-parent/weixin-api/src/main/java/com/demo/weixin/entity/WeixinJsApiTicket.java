package com.demo.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * jsapi_ticket
 * 公众号用于调用微信JS接口的临时票据
 */
public class WeixinJsApiTicket extends BaseWeixinResponse{

    private static final long serialVersionUID = -5265315650627210945L;
    private String appId;

    @JSONField(name = "ticket")
    private String ticket;

    @JSONField(name = "expires_in")
    private long expiresInSecond;

    private Date createTime = new Date();

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public long getExpiresInSecond() {
        return expiresInSecond;
    }

    public void setExpiresInSecond(long expiresInSecond) {
        this.expiresInSecond = expiresInSecond;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    // 是否已经过期
    public boolean isExpires() {
        if(ticket == null || ticket.trim().length() <= 0){
            return true;
        }

        long currentTimeSecond = (new Date()).getTime() / 1000;
        long createTimeSecond = createTime.getTime() / 1000;

        long pastSecond = currentTimeSecond - createTimeSecond;

        boolean expires = ((pastSecond + 700) > expiresInSecond);

        return expires ;
    }
}
