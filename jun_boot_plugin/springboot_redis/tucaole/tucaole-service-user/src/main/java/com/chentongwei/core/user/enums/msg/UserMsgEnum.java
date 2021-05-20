package com.chentongwei.core.user.enums.msg;

import com.chentongwei.common.enums.IBaseEnum;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 用户部分字符串常量
 */
public enum UserMsgEnum implements IBaseEnum {

    /**
     * 用户部分
     */
    PWD_AND_ENSUREPWD_NOT_SAMED(2000, "两次密码输入不一致"),
    EMAIL_ALREADY_EXISITS(2001, "该邮箱已存在"),
    EMAIL_NULL_ERROR(2002, "电子邮箱不能为空"),
    EMAIL_TIMEOUT_ERROR(2003, "激活失败，邮箱已经超过有效期（2小时）"),
    USER_ALREADY_ACTIVE(2004, "用户已激活，无需重复激活"),
    USER_NOT_ACTIVE(2005, "用户未激活"),
    USER_IS_DISABLED(2006, "用户被禁用"),
    EMAIL_OR_PWD_ERROR(2007, "用户名或密码错误"),
    USER_NOT_EXISTS(2008, "用户尚未注册"),
    USER_INCOMPLETE(2009, "用户信息不完整"),
    EMAIL_NOT_REGIST(2100, "此邮箱未注册吐槽了"),
    COUNT_EXCEED(2101, "今日发送邮件次数超限，请明天再试"),
    OLD_PASSWORD_ERROR(2102, "原密码不正确"),
    NEW_EMAIL_SAME_AS_OLD_EMAIL_ERROR(2103, "新邮箱不能和旧邮箱相同")
    ;

    //状态码
    private int code;
    //消息
    private String msg;

    UserMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
