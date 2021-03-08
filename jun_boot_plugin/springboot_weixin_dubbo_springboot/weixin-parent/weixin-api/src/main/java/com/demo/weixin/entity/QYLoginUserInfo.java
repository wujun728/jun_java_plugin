package com.demo.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 企业号登录用户信息
 */
public class QYLoginUserInfo extends BaseWeixinResponse {

    @JSONField(name = "UserId")
    private String userId; // 成员UserID

    @JSONField(name = "DeviceId")
    private String deviceId; // 手机设备号(由企业微信在安装时随机生成，删除重装会改变，升级不受影响)

    /**
     * 成员票据，最大为512字节。
     * scope为snsapi_userinfo或snsapi_privateinfo，且用户在应用可见范围之内时返回此参数。
     * 后续利用该参数可以获取用户信息或敏感信息。
     **/
    @JSONField(name = "user_ticket")
    private String userTicket;

    /**
     * user_token的有效时间（秒），随user_ticket一起返回
     */
    @JSONField(name = "expires_in")
    private Long expiresIn;

    @JSONField(name = "OpenId")
    private String openId;  // 非企业成员授权时返回 openID,不会返回UserId 和 UserTicket

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserTicket() {
        return userTicket;
    }

    public void setUserTicket(String userTicket) {
        this.userTicket = userTicket;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
