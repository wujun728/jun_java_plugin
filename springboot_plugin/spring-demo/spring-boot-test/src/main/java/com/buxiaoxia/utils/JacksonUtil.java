package com.buxiaoxia.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Created by xw on 2017/9/14.
 * 2017-09-14 14:27
 */
public class JacksonUtil {

    public static ObjectMapper mapper = new ObjectMapper();

    static {
        //jackson反序列化时忽略不需要的字段
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * @return String    返回类型
     * @Title: map2str
     * @Description: 把map对象转为str
     * @parma:@param map
     * @parma:@return
     */
    public static String map2str(Map<?, ?> map) {
        String jsonfromMap = "";
        try {
            jsonfromMap = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            System.err.println("jackson map to str error");
            e.printStackTrace();
        }
        return jsonfromMap;
    }


    /**
     * @return String    返回类型
     * @Title: map2str
     * @Description: 把map对象转为str
     * @parma:@param map
     * @parma:@return
     */
    public static String stringify(Object obj) {
        String jsonfromMap = "";
        try {
            jsonfromMap = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.err.println("jackson map to str error");
            e.printStackTrace();
        }
        return jsonfromMap;
    }


    public static <T> T parse(String json, Class<T> clazz) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            System.err.println("jackson map to str error");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 复杂类型处理
     *
     * @param json
     * @param javaType
     * @param <T>
     * @return
     */
    public static <T> T parse(String json, JavaType javaType) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            return mapper.readValue(json, javaType);
        } catch (Exception e) {
            System.err.println("jackson map to str error");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 处理复杂类型Collection如 List<YourBean>，那么就需要先反序列化复杂类型 为泛型的Collection Type
     *
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
