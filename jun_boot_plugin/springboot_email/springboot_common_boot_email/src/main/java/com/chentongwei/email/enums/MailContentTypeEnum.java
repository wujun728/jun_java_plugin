package com.chentongwei.email.enums;

/**
 * @Description: 邮件内容的类型
 *
 * @author Wujun
 * @Project common-boot-email
 */
public enum MailContentTypeEnum {

    //text格式
    TEXT("text"),
    //html格式
    HTML("html"),
    //模板
    TEMPLATE("template")
    ;

    private String value;

    MailContentTypeEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
