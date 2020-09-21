package com.opensource.nredis.proxy.monitor.platform;

 
public enum RestStatus {

    // 2**
    SUCCESS(200, "成功"), NO_RESULT(2001,"查询接口为空"),

    // 4**
    CLIENT_ERROR(400, "客户端错误"), PARAMETER_REQUIRED(4001, "必填项"), PARAMETER_INVALID_VALUE(4002, "%s 非法值"),EXIST_NODE(5010,"存在子节点,不能删除根节点"),

    // 5**
    SERVER_ERROR(500, "服务器内部错误"),NOT_RIGHT(5001,"用户名或者密码错误"),NOT_UNIQUE(5002,"用户名或者密码已经存在"),NOT_CURRENTUSER(5003,"当前用户不存在"),CAN_NOT_AUTH(5004,"不能删除");
    public int code;
    public String message;

    private RestStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String toString() {
        return this.code + ": " + this.message;
    }
}

