package com.demo.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/*
 * @Description: QQ用户信息
 * @version V1.0
 */
public class QQUser implements Serializable{

    private static final long serialVersionUID = 8317415942448591880L;

    @JSONField(name="openid")
    public String openId;

    @JSONField(name="ret")
    public int ret; // 返回码

    @JSONField(name="msg")
    public String msg; // 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。

    @JSONField(name="nickname")
    public String nickname; // 用户在QQ空间的昵称

    @JSONField(name="figureurl")
    public String figureurl; // 大小为30×30像素的QQ空间头像URL

    @JSONField(name="figureurl_1")
    public String figureurl1; // 大小为50×50像素的QQ空间头像URL。

    @JSONField(name="figureurl_2")
    public String figureurl2; // 大小为100×100像素的QQ空间头像URL。

    @JSONField(name="figureurl_qq_1")
    public String figureurlQq1; // 大小为40×40像素的QQ头像URL。

    @JSONField(name="figureurl_qq_2")
    public String figureurlQq2; // 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。

    @JSONField(name="gender")
    public String gender; // 性别。 如果获取不到则默认返回"男"

    @JSONField(name="is_yellow_vip")
    public boolean yellowVip; // 标识用户是否为黄钻用户（0：不是；1：是）。

    @JSONField(name="vip")
    public boolean vip; // 标识用户是否为黄钻用户（0：不是；1：是）

    @JSONField(name="yellow_vip_level")
    public String yellowVipLevel; // 黄钻等级

    @JSONField(name="level")
    public int level; // 黄钻等级

    @JSONField(name="is_yellow_year_vip")
    public boolean yellowYearVip; // 标识是否为年费黄钻用户（0：不是； 1：是）

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl1() {
        return figureurl1;
    }

    public void setFigureurl1(String figureurl1) {
        this.figureurl1 = figureurl1;
    }

    public String getFigureurl2() {
        return figureurl2;
    }

    public void setFigureurl2(String figureurl2) {
        this.figureurl2 = figureurl2;
    }

    public String getFigureurlQq1() {
        return figureurlQq1;
    }

    public void setFigureurlQq1(String figureurlQq1) {
        this.figureurlQq1 = figureurlQq1;
    }

    public String getFigureurlQq2() {
        return figureurlQq2;
    }

    public void setFigureurlQq2(String figureurlQq2) {
        this.figureurlQq2 = figureurlQq2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isYellowVip() {
        return yellowVip;
    }

    public void setYellowVip(boolean yellowVip) {
        this.yellowVip = yellowVip;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public String getYellowVipLevel() {
        return yellowVipLevel;
    }

    public void setYellowVipLevel(String yellowVipLevel) {
        this.yellowVipLevel = yellowVipLevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isYellowYearVip() {
        return yellowYearVip;
    }

    public void setYellowYearVip(boolean yellowYearVip) {
        this.yellowYearVip = yellowYearVip;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
