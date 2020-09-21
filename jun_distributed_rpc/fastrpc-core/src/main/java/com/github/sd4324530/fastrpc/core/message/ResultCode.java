package com.github.sd4324530.fastrpc.core.message;

/**
 * 消息返回码
 *
 * @author peiyu
 */
public enum ResultCode {

    SUCCESS(0, "成功"),
    TIMEOUT(1001, "超时"),
    OTHER(9999, "其他错误");

    private int    code;
    private String desc;

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
