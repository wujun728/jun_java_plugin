package com.chentongwei.core.user.enums.status;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 极验证状态
 */
public enum GeetestStatusEnum {
    /** 验证码已过期 */
    EXPIRED_GEETEST(-1),
    /** 验证码不正确 */
    ERROR_GEETEST(0),
    /** 验证成功 */
    SUCCESS_GEETEST(1)
    ;

    private int value;

    GeetestStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
