package com.jun.plugin.dto;

import java.util.HashMap;
import java.util.Map;

public class ResponseData {

    private final String message;
    private final int code;
    private final Map<String, Object> data = new HashMap<>();

    private ResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseData ok() {
        return new ResponseData(200, "Ok");
    }

    public static ResponseData notFound() {
        return new ResponseData(404, "Not Found");
    }

    // 错误请求
    public static ResponseData badRequest() {
        return new ResponseData(400, "Bad Request");
    }

    // 禁止
    public static ResponseData forbidden() {
        return new ResponseData(403, "Forbidden");
    }

    // 无权限
    public static ResponseData unauthorized() {
        return new ResponseData(401, "unauthorized");
    }

    // 服务器内部错误
    public static ResponseData serverInternalError() {
        return new ResponseData(500, "Server Internal Error");
    }

    public static ResponseData customerError() {
        return new ResponseData(1001, "customer Error");
    }


    public ResponseData putDataValue(String key, Object value) {
        data.put(key, value);
        return this;
    }

    // getters
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
