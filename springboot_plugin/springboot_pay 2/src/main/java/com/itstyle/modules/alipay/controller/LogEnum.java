package com.itstyle.modules.alipay.controller;

/**
 * 本地日志枚举
 * @author Wujun
 *
 */
public enum  LogEnum {


    BUSSINESS("bussiness"),

    PLATFORM("platform"),

    DB("db"),

    EXCEPTION("exception"),

    ;


    private String category;


    LogEnum(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}