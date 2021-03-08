package com.zh.springbootshiro.enums;


/**
 * @author Wujun
 * @date 2019/6/5
 */
public enum AppResultCode {

    SUCCESS(200,"成功"),

    FAIL(-1, "操作失败,请稍候再试!"),

    USER_NOT_EXIST(100, "用户不存在!"),
    PASSWORD_INCORRECT(101, "密码错误!"),
    UNAUTHORIZED(102, "权限不足!");

    private final int code;

    private final String msg;

    AppResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
