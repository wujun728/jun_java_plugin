package org.itcast.demo.util;

/**
 * @description
 * @auther: CDHong
 * @date: 2019/5/2-13:56
 **/
public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    PARAME_ERROR(2,"PARAME_ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    SYS_ERROR(11,"系统错误~");

    private final int code;
    private final String desc;

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }}

