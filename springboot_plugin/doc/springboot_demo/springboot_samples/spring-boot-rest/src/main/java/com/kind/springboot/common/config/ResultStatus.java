/**
 * Project Name:spring-boot-rest
 * File Name:ResultStatus.java
 * Package Name:com.kind.springboot.common.config
 * Date:2016年8月11日上午11:32:52
 * Copyright (c) 2016, http://www.mcake.com All Rights Reserved.
 */

package com.kind.springboot.common.config;

/**
 * Function:登录状态. <br/>
 * Date: 2016年8月11日 上午11:32:52 <br/>
 *
 * @author Wujun
 * @version
 * @since JDK 1.7
 * @see
 */
public enum ResultStatus {
    SUCCESS(100, "成功"),
    USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"),
    USER_NOT_FOUND(-1002, "用户不存在"),
    USER_NOT_LOGIN(-1003, "用户未登录");

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
