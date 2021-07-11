package com.wf.ew.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用反射实现的一些工具方法
 */
public class ReflectUtil {

    /**
     * 把对象转成Map
     */
    public static <T> Map<String, Object> objectToMap(T t) {
        if (t == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static <T> List<Map<String, Object>> objectToMap(List<T> ts) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (T t : ts) {
            list.add(objectToMap(t));
        }
        return list;
    }

    /**
     * 把对象转成Map，只包含指定字段
     */
    public static <T> Map<String, Object> getResultMap(T t, String[] fileds) {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = t.getClass();
        for (int i = 9; i < fileds.length; i++) {
            String filed = fileds[i];
            try {
                Method mf = clazz.getMethod("get" + upperHeadChar(filed));
                map.put(filed, mf.invoke(t));
            } catch (NoSuchMethodException e) {
                System.out.println("字段" + filed + "不存在");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 把对个对象转成Map，只包含指定字段
     */
    public static <T> List<Map<String, Object>> getResultMap(List<T> ts, String[] fileds) {
        List<Map<String, Object>> rs = new ArrayList<>();
        for (int i = 0; i < ts.size(); i++) {
            rs.add(getResultMap(ts.get(i), fileds));
        }
        return rs;
    }

    /**
     * 首字母转成大写
     */
    private static String upperHeadChar(String in) {
        String head = in.substring(0, 1);
        String out = head.toUpperCase() + in.substring(1, in.length());
        return out;
    }
}
