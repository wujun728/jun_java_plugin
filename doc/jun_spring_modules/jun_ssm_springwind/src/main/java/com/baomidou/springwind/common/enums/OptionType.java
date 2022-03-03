package com.baomidou.springwind.common.enums;

/**
 * 说明 :
 * 作者 : WeiHui.jackson
 * 日期 : 2016/4/21 12:29
 * 版本 : 1.0.0
 */
public enum OptionType {

    OP_INSERT(1, "插入"),

    OP_EDIT(2, "更新");

    private final int op;

    private final String desc;

    OptionType(int op, String desc) {
        this.op = op;
        this.desc = desc;
    }

    public int getOp() {
        return op;
    }

    public String getDesc() {
        return desc;
    }
}
