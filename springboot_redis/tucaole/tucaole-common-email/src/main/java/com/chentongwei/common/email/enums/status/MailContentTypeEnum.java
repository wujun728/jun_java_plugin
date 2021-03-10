package com.chentongwei.common.email.enums.status;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 邮件内容的类型
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
