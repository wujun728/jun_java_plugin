package com.jun.plugin.picturemanage.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.MapUtils;

import java.io.Serializable;
import java.util.HashMap;


/**
 * @Author EddyChen
 * @Email 835033913@qq.com
 * @Create 2019/8/19 11:31
 */
public class JsonResult<T> extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SUCCESSCODE = "0";
    public static final String SUCCESSMSG = "ok";
    public static final String FAILEDCODE = "400";

    /**
     * 判断是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        String code = (String) this.get("errorCode");
        return code.equals(SUCCESSCODE);
    }


    public static JsonResult success(Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.put("result", data);
        jsonResult.put("errorCode", JsonResult.SUCCESSCODE);
        return jsonResult;
    }

    public static JsonResult success() {
        JsonResult jsonResult = new JsonResult();
        jsonResult.put("errorCode", JsonResult.SUCCESSCODE);
        jsonResult.put("result", JsonResult.SUCCESSMSG);
        return jsonResult;
    }


    public static JsonResult error(String errorMsg) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.put("errorMessage", errorMsg);
        jsonResult.put("errorCode", JsonResult.FAILEDCODE);
        return jsonResult;
    }

    public static JsonResult error(String errorMsg, int code) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.put("errorMessage", errorMsg);
        jsonResult.put("errorCode", code + "");
        return jsonResult;
    }

    /**
     * 返回错误的信息
     *
     * @return
     */
    public String getErrorMessage() {
        return MapUtils.getString(this, "errorMessage");
    }

    /**
     * 返回错误的代码
     *
     * @return
     */
    public int getErrorCode() {
        return MapUtils.getIntValue(this, "errorCode");
    }

    /**
     * 返回数据
     *
     * @return
     */
    public T getResult() {
        return (T) this.get("result");
    }


    public String toJson() {
        return JSONObject.toJSONString(this);
    }

    /**
     * 参数不完整
     *
     * @return
     */
    public static JsonResult errorForEmpty() {
        return JsonResult.error("参数不完整");
    }

    public static JsonResult actionSuccess() {
        return JsonResult.success("操作成功!");
    }

    public static JsonResult actionFailure() {
        return JsonResult.error("操作失败!");
    }
}
