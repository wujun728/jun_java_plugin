package com.chentongwei.common.enums.status;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 是否删除状态
 */
public enum DelFlagEnum {
    /** 已删除 */
    IS_DELETE(0),
    /** 未删除 */
    NO_DELETE(1)
    ;

    //状态
    private int value;

    DelFlagEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
