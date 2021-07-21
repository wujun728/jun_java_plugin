package com.wf.ew.common;

import java.util.HashMap;

/**
 * 返回结果对象
 *
 * @author wangfan
 * @date 2017-6-10 上午10:10:07
 */
public class JsonResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
	
    private JsonResult() {
    }

    /**
     * 返回成功
     */
    public static JsonResult ok() {
        return ok("操作成功");
    }

    /**
     * 返回成功
     */
    public static JsonResult ok(String message) {
        return ok(200, message);
    }

    /**
     * 返回成功
     */
    public static JsonResult ok(int code, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.put("code", code);
        jsonResult.put("msg", message);
        return jsonResult;
    }

    /**
     * 返回失败
     */
    public static JsonResult error() {
        return error("操作失败");
    }

    /**
     * 返回失败
     */
    public static JsonResult error(String messag) {
        return error(500, messag);
    }

    /**
     * 返回失败
     */
    public static JsonResult error(int code, String message) {
        return ok(code, message);
    }

    /**
     * 设置code
     */
    public JsonResult setCode(int code) {
        super.put("code", code);
        return this;
    }

    /**
     * 设置message
     */
    public JsonResult setMessage(String message) {
        super.put("msg", message);
        return this;
    }

    /**
     * 放入object
     */
    @Override
    public JsonResult put(String key, Object object) {
        super.put(key, object);
        return this;
    }
}