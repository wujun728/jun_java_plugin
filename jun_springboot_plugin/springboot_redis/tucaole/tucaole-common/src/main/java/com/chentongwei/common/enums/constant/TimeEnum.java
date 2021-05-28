package com.chentongwei.common.enums.constant;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 时间常量枚举
 */
public enum TimeEnum {
    /** 2小时 */
    TWO_HOUR(2),
    /** 15秒 */
    FIFTEEN_SECOND(15),
    /** 3次 */
    THREE_TIME(3),
    /** 5次 */
    FIVE_TIME(5),
    /** 30次 */
    THRITY_TIME(30),
    /** 100次 */
    HUNDRED_TIME(100)
    ;

    //状态
    private int value;

    TimeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
