package com.mall.common.model;

import java.util.Date;

/**
 * @author Wujun
 * @version 1.0
 * @create_at 2018/11/2416:19
 */
public class Token {

    private String id;
    private Date createDate;
    private long expirationTime;     //失效时间,以秒为单位

    public Token (String id, long expirationTime) {
        this.id = id;
        this.expirationTime = expirationTime;
    }

    public long getExpirationTime(){
        return expirationTime;
    }

    public String getId(){
        return id;
    }
}
