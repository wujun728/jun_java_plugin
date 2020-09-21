package com.chentongwei.common.enums.constant;

/**
 * @author TongWei.Chen 2017-12-15 13:41:54
 * @Project tucaole
 * @Description: 队列枚举
 */
public enum QueueEnum {
    /** 队列最大长度 */
    MAX_LENGTH(1024)
    ;

    //状态
    private int value;

    QueueEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
