package com.zccoder.space.interview.thread;

import lombok.Getter;

/**
 * 国家枚举。枚举使用示例
 *
 * @author zc
 * @date 2020/05/03
 */
@Getter
public enum CountryEnum {

    /**
     * 齐国
     */
    ONE(1, "齐"),

    TWO(2, "楚"),

    THREE(3, "燕"),

    FOUR(4, "赵"),

    FIVE(5, "魏"),

    SIX(6, "韩");

    static CountryEnum getByCode(int code) {
        CountryEnum[] values = CountryEnum.values();
        for (CountryEnum value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new RuntimeException("无效的国家编号");
    }

    /**
     * 编号
     */
    private Integer code;
    /**
     * 国号
     */
    private String name;

    CountryEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}