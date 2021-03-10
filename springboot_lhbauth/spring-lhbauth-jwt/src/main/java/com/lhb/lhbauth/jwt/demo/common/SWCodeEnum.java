package com.lhb.lhbauth.jwt.demo.common;

/**
 * @author Wujun
 * @Date 2018/11/20 23:10
 */
public enum SWCodeEnum {
    CODE_OK_0(0, "Success"),
    CODE_OK(200, "OK"),
    CODE_FAIL(-1, "操作失败"),
    CODE_RPC_ERROR(-2, "远程调度失败"),
    CODE_UNKNOWN(-3, "未知错误"),
    CODE_REDIS_ERROR(-4, "存储redis错误"),
    CODE_CPBEAN_ERROR(-5, "复制bean失败"),
    CODE_PARAM_ERROR(460, "请求参数错误"),
    CODE_20004(20004,"退出失败，不存在该token");
    private int code;
    private String msg;

    SWCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static SWCodeEnum codeOf(int code) {
        for (SWCodeEnum state : values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        return SWCodeEnum.CODE_UNKNOWN;
    }
}
