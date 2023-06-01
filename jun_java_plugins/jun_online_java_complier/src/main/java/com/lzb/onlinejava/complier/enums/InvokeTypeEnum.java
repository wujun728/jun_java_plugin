package com.lzb.onlinejava.complier.enums;

public enum InvokeTypeEnum {
    intType(1, int.class),
    stringType(2, String.class),
    intArrayType(3, int[].class),
    stringArrayType(4, String[].class);


    private int code;
    private Class cls;

    InvokeTypeEnum(int code, Class cls) {
        this.code = code;
        this.cls = cls;
    }

    public Class getCls() {
        return this.cls;
    }

    public int getCode() {
        return this.code;
    }

}
