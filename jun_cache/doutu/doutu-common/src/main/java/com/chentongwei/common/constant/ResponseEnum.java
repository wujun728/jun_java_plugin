package com.chentongwei.common.constant;

/**
 * 通用响应常量
 *
 * @author TongWei.Chen 2017-05-16 13:32:19
 */
public enum ResponseEnum implements IBaseEnum {

    /**
     * 系统部分
     */
    LOGIN(-1, "请先登录"),
    SUCCESS(0, "处理成功"),
    FAILED(1, "系统异常"),

    /**
     * 异常部分
     */
    BUSSINESS_ERROR(2, "业务异常"),
    PARAM_ERROR(3, "参数传递异常"),
    NULL(4, "数据为空"),
    OPERATE_ERROR(5, "操作失败"),

    /*图片部分1x*/
    UPLOAD_ERROR(11, "文件上传异常"),
    EXISTS_FILE(12, "文件已存在"),
    FILE_FORMAT_ERROR(13, "仅支持图片格式"),
    ALREADY_REPORT(14, "您已经举报过此图片，请耐心等待反馈。"),
    NOT_WAIT_CHECK(15, "只能审核待审核状态的图片。"),
    ALREADY_UPVOTE(16, "您已经点赞过该图片"),
    NOT_SELECT_PICTURE(17, "请选择图片"),
    NOT_INPUT_TAG(18, "请输入标签"),

    /*用户部分2x*/
    LOGIN_ERROR(21, "用户名或密码错误"),
    USER_DISABLED(22, "用户被禁用"),
    IS_NOT_ADMIN(23, "当前用户没有权限访问后端管理系统"),
    AUTHCODE_ERROR(24, "验证码错误")
    ;

    //状态码
    private int code;
    //消息
    private String msg;

    private ResponseEnum(int code, String msg) {
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
