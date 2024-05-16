package com.cailei.demo.enums;

/**
 * @author ：蔡磊
 * @date ：2022/10/20 18:55
 * @description：枚举类对象
 */

public enum StatusEnum {

    // 公共
    RESULT_SUCCESS(200, "请求成功"),
    RESULT_FAIL(400, "请求失败"),
    RESULT_NULL(404, "未找到资源"),

    // 登录
    LOGIN_EXPIRE(2001, "未登录或者登录失效"),
    LOGIN_CODE_ERROR(2002, "登录验证码错误"),
    LOGIN_ERROR(2003, "用户名不存在或密码错误"),
    LOGIN_USER_STATUS_ERROR(2004, "用户状态不正确"),
    LOGOUT_ERROR(2005, "退出失败，token不存在"),
    LOGIN_USER_NOT_EXIST(2006, "该用户不存在"),
    LOGIN_USER_EXIST(2007, "该用户已存在");

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

