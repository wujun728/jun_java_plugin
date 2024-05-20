package com.jun.plugin.generate.core.enums;

import lombok.Getter;

/**
 * 查询类型的枚举
 *
 * @author yubaoshan
 * @date 2021/2/8 20:31
 */
@Getter
public enum QueryTypeEnum {

    eq("eq", "等于"),
    like("like", "模糊"),
    gt("gt", "大于"),
    lt("lt", "小于"),
    ne("ne", "不等于"),
    ge("ge", "大于等于"),
    le("le", "小于等于"),
    isNotNull("isNotNull", "不为空");

    private final String code;

    private final String message;

    QueryTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
