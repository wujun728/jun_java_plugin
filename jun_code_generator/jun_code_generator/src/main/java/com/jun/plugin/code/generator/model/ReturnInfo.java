package com.jun.plugin.code.generator.model;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * common returnT:公共返回封装类
 *
 * @author zhengkai.blog.csdn.net
 */
@Data
public class ReturnInfo extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public ReturnInfo() {
        put("code", 0);
        put("msg", "success");
    }

    public static ReturnInfo error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static ReturnInfo error(String msg) {
        return error(500, msg);
    }

    public static ReturnInfo error(int code, String msg) {
        ReturnInfo r = new ReturnInfo();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }
    public static ReturnInfo define(int code, String msg) {
        ReturnInfo r = new ReturnInfo();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }
    public static ReturnInfo ok(String msg) {
        ReturnInfo r = new ReturnInfo();
        r.put("msg", msg);
        return r;
    }

    public static ReturnInfo ok(Map<String, Object> map) {
        ReturnInfo r = new ReturnInfo();
        r.putAll(map);
        return r;
    }

    public static ReturnInfo ok() {
        return new ReturnInfo();
    }

    @Override
    public ReturnInfo put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
