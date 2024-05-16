package com.cailei.demo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author ：蔡磊
 * @date ：2022/10/20 21:49
 * @description：TODO
 */

public enum SexEnum {
    MALE(1, "男"),
    FEMALE(2, "女");

    @EnumValue
    private Integer sexCode;
    private String sexName;

    public Integer getSexCode() {
        return sexCode;
    }

    public String getSexName() {
        return sexName;
    }

    SexEnum(Integer sexCode, String sexName) {
        this.sexCode = sexCode;
        this.sexName = sexName;
    }
}
