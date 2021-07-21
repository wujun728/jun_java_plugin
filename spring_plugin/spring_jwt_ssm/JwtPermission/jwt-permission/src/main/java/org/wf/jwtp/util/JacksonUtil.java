package org.wf.jwtp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 处理Json工具类
 */
public class JacksonUtil {

    /**
     * Object转JSON字符串
     */
    public static String toJSONString(Object object) {
        if (object == null) return null;
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JSON字符串转List
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        if (json == null || json.trim().isEmpty()) return null;
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(json, mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JSON字符串转Object
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (json == null || json.trim().isEmpty()) return null;
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * objectListToArray
     */
    public static Object[] objectListToArray(List<Object> list) {
        if (list == null) return null;
        return list.toArray(new Object[0]);
    }

    /**
     * stringListToArray
     */
    public static String[] stringListToArray(List<String> list) {
        if (list == null) return null;
        return list.toArray(new String[0]);
    }

    /**
     * setToArray
     */
    public static String[] stringSetToArray(Set<String> set) {
        if (set == null) return null;
        return set.toArray(new String[0]);
    }

}
