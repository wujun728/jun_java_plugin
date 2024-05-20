package com.jun.plugin.common.util;

import com.alibaba.fastjson2.JSONObject;

/**
 * Json和Object的互相转换，转List必须Json最外层加[]，转Object，Json最外层不要加[]
 * @date 2018/8/9 15:37
 */
public class JsonConvertUtil {

    private JsonConvertUtil() {}

    /**
     * JSON 转 Object
     */
    public static <T> T jsonToObject(String pojo, Class<T> clazz) {
        return JSONObject.parseObject(pojo, clazz);
    }

    /**
     * Object 转 JSON
     */
    public static <T> String objectToJson(T t){
        return JSONObject.toJSONString(t);
    }
}
