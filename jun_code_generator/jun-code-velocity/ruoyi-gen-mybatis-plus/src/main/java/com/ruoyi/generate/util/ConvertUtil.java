package com.ruoyi.generate.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author ALTER
 * @Date 2022/2/7 10:04
 * @Version 1.0
 */
public class ConvertUtil {

    private static Pattern linePattern = Pattern.compile("_([a-z])");

    private static Pattern humpPattern = Pattern.compile("\\B(\\p{Upper})(\\p{Lower}*)");

    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    public static Map object2Map(Object obj) {
        Map map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        @SuppressWarnings("rawtypes")
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Map转成实体对象
     *
     * @param map   map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static Object map2Object(Map map, Class clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线(Map)
     *
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Map map = JSONObject.parseObject(str);
        Map newMap =new HashMap();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            StringBuffer sb = new StringBuffer();
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            Matcher matcher = humpPattern.matcher(key);
            while (matcher.find()) {
                matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
            }
            matcher.appendTail(sb);
            newMap.put(sb.toString(), entry.getValue());
        }
        return JSON.toJSONString(newMap);

    }
}
