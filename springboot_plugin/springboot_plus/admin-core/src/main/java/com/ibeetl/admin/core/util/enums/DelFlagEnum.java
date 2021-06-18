package com.ibeetl.admin.core.util.enums;

import org.beetl.sql.core.annotatoin.EnumMapping;

/**
 * 描述:数据是否被逻辑删除
 * @author Wujun
 */
@EnumMapping("value")
public enum DelFlagEnum {
    NORMAL(0), DELETED(1);

    private int value;

    DelFlagEnum(int value) {
        this.value = value;
    }

    public static DelFlagEnum getEnum(int value) {
        for (DelFlagEnum type : DelFlagEnum.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
