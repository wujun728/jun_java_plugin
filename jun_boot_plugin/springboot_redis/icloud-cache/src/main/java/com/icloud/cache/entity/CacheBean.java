package com.icloud.cache.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 解决问题：
 *
 * @author Wujun
 * @version 1.0
 * @date 2018年5月5日
 * @since 1.7
 */
public class CacheBean {
    private String key = "";
    private String desc = "";
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    public CacheBean(String key, String desc, Date expireTime) {
        this.key = key;
        this.desc = desc;
        this.expireTime = expireTime;
    }

    public CacheBean(String key, Date expireTime) {
        this.key = key;
        this.expireTime = expireTime;
    }

    public CacheBean() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

}
