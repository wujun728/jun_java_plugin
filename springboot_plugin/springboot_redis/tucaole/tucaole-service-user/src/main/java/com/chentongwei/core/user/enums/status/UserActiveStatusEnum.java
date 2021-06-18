package com.chentongwei.core.user.enums.status;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 用户激活状态
 */
public enum UserActiveStatusEnum {

    /**
     * 未激活
     */
    USER_IS_NOT_ACTIVE(0),
    /**
     * 已激活
     */
    USER_IS_ACTIVE(1)
    ;

    private int value;

    UserActiveStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
