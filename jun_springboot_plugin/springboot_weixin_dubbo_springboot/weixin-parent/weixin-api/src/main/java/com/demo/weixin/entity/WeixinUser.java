package com.demo.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 微信用户资料
 */
public class WeixinUser implements Serializable {

    private static final long serialVersionUID = -3703491852417733717L;

    @JSONField(name = "openid")
    private String openId; // 微信用户openId

    @JSONField(name = "nickname")
    private String nickName;

    @JSONField(name = "sex")
    private int sex; // 0 未知 1 男生 2 女生

    @JSONField(name = "province")
    private String province; //普通用户个人资料填写的省份

    @JSONField(name = "city")
    private String city; //普通用户个人资料填写的城市

    @JSONField(name = "country")
    private String country;//国家，如中国为CN

    @JSONField(name = "headimgurl")
    private String headimgurl;//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空

    @JSONField(name = "privilege")
    private String privilege;//用户特权信息，json数组，如微信沃卡用户为（chinaunicom）

    @JSONField(name = "unionid")
    private String unionid;//用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }


}
