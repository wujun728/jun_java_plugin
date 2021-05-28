package com.chentongwei.common.enums.constant;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 年月日格式枚举
 */
public enum DateTimeFormatEnum {
    YEAR("yyyy"),
    YEAR_MONTH("yyyy-MM"),
    YEAR_MONTH_DAY("yyyy-MM-dd"),
    DATE_TIME("yyyy-MM-dd HH:mm:ss")
    ;

    private String parttern;

    public String parttern() {
        return parttern;
    }

    DateTimeFormatEnum(String parttern) {
        this.parttern = parttern;
    }
}
