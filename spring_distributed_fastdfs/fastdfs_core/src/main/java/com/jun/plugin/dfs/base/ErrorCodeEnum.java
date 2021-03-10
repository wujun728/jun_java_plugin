package com.jun.plugin.dfs.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    OK(200, "正常"),

    APP_NOT_EXIST(300, "应用不存在"),

    APP_STOPPED(301, "应用已停用"),

    TIMESTAMP_ERROR(302, "时间戳过期"),

    APP_AUTH_FAILURE(303, "鉴权校验失败"),

    AUTH_PARAM_ERROR(304, "鉴权参数错误"),

    ACCESS_DENIED(305, "拒绝访问"),

    OPERATION_FAILURE(306, "操作失败"),

    PARAM_ERROR(400, "参数错误"),

    RESOURCE_NOT_FOUND(404, "资源找不到"),

    SERVER_ERROR(500, "服务器内部错误"),
    ;

    private final int code;

    private final String message;
}
