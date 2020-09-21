package com.chentongwei.common.enums.constant;

/**
 * @author TongWei.Chen 2017-12-16 15:22:17
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
