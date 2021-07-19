package com.jun.plugin.picturemanage.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Author PuaChen
 * @Create 2018-09-18 17:37
 */
public class ObjectUtil {

    /**
     * 对象序列化为Map
     *
     * @param obj
     * @return
     */
    public static CustomMap convertObjectToMap(Object obj) {
        String json = JSONObject.toJSONString(obj, SerializerFeature.WriteNullStringAsEmpty);
        return JSONObject.parseObject(json, CustomMap.class);
    }

    /**
     * 将多个对象序列化为一个Map
     *
     * @param objs
     * @return
     */
    public static CustomMap convertObjectsToMap(Object... objs) {
        CustomMap data = CustomMap.create();
        for (Object obj : objs) {
            CustomMap map = convertObjectToMap(obj);
            data.putAll(map);
        }
        return data;
    }


    /**
     * 转换为  BigDecimal
     *
     * @param str
     * @return
     */
    public static BigDecimal convertBigDecimal(Object str) {
        return new BigDecimal(String.valueOf(str));
    }


    /**
     * 转换为  BigDecimal
     *
     * @param str
     * @return
     */
    public static BigDecimal convertBigDecimal(Object str, String defaultVal) {
        if (str == null || String.valueOf(str).isEmpty()) {
            str = defaultVal;
            if (StringUtils.isBlank(defaultVal)) {
                throw new RuntimeException("默认值不能为空");
            }
        }
        return new BigDecimal(String.valueOf(str));
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>(1);
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 将字符串逗号分割 转化为数组
     *
     * @param str
     * @return
     */
    public static List<String> converStringForArray(String str) {
        ArrayList<String> array = new ArrayList<>();
        for (String s : str.split(",")) {
            if (StringUtils.isNotBlank(s)) {
                array.add(s);
            }
        }
        return array;
    }

    /**
     * 下划线转驼峰形式
     *
     * @param list
     * @return
     */
    public static List<Map<String, Object>> underlineToCamel(List<Map<String, Object>> list) {
        List<Map<String, Object>> dataList = new ArrayList<>();

        for (Map<String, Object> map : list) {
            Set<String> set = map.keySet();
            CustomMap newMap = CustomMap.create();
            for (String key : set) {
                String newKey = com.baomidou.mybatisplus.core.toolkit.StringUtils.underlineToCamel(key);
                newMap.put(newKey, map.get(key));
            }
            dataList.add(newMap);
        }
        return dataList;
    }

}
