package com.chentongwei.core.user.enums.status;

/**
 * @author TongWei.Chen 2017-12-04 14:10:09
 * @Project tucaole
 * @Description: 用户禁用状态
 */
public enum UserDisabledStatusEnum {

    /**
     * 禁用
     */
    USER_DISABLED(0),
    /**
     * 启用
     */
    USER_ENABLED(1)
    ;

    private int value;

    UserDisabledStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
