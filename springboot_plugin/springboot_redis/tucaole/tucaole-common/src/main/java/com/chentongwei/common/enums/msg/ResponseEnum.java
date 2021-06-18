package com.chentongwei.common.enums.msg;

import com.chentongwei.common.enums.IBaseEnum;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 通用响应常量
 */
public enum ResponseEnum implements IBaseEnum {

    /**
     * 系统部分
     */
    SUCCESS(0, "处理成功"),
    FAILED(1, "系统异常"),

    /**
     * 异常部分
     */
    PARAM_ERROR(3, "参数传递异常"),
    NULL(4, "数据为空"),
    OPERATE_ERROR(5, "操作失败"),
    TOKEN_IS_NULL(6, "token不能为空"),
    VERIFICATION_CODE_ERROR(7, "验证码不正确"),
    VERIFICATION_ALREADY_EXPIRE(8, "验证码已失效"),
    USER_NOT_LOGIN(9, "用户未登录"),
    NOT_ONE_SELF_DATA(10, "不能操作非自己创建的数据")
    ;

    //状态码
    private int code;
    //消息
    private String msg;

    ResponseEnum(int code, String msg) {
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
