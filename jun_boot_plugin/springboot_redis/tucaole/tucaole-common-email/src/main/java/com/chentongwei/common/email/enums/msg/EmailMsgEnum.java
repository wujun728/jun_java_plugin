package com.chentongwei.common.email.enums.msg;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 字符串常量
 */
public enum EmailMsgEnum {

    //邮件标题
    EMAIL_TITLE("吐槽了")
    ;

    private String value;

    EmailMsgEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
