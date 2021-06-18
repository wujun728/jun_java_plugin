package com.chentongwei.common.email.enums.template;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 模板名称枚举
 */
public enum EmailTemplateEnum {
    /**
     * 注册模板
     */
    REGIST_TEMPLATE("regist-template"),
    /**
     * 找回密码模板
     */
    FORGET_PASSWORD_TEMPLATE("forget-password-template"),
    /**
     * 更改邮箱
     */
    UPDATE_EMAIL_TEMPLATE("update-email-template")
    ;

    EmailTemplateEnum(String value) {
        this.value = value;
    }

    private String value;

    public String value() {
        return value;
    }

}
