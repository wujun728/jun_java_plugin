package com.zhaodui.springboot.buse.model;

/**
 * 接口返回状态码
 *
 */
public enum ResponseMessageCodeEnum {

    SUCCESS("0"),
    SUCCESSWXBD("W0"),

    SUCCESSWXNBD("W1"),

    ERROR("-1"),
    VALID_ERROR("1000"),//校验失败
    SAVE_SUCCESS("r0001"),
    UPDATE_SUCCESS("r0002"),
    REMOVE_SUCCESS("r0003");


    private String code;

    ResponseMessageCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
