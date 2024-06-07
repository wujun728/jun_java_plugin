package com.jun.plugin.common.util;

import com.jun.plugin.db.record.FieldUtils;
import org.springframework.cglib.beans.BeanMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Map集合与Bean对象转换 工具类
 */
public class BeanMapUtil {
    /**
     * 将对象属性转化为map结合
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>(16);
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map集合中的数据转化为指定对象的同名属性中
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) throws Exception {
        T bean = clazz.newInstance();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T columnsMapToBean(Map<String, Object> map, Class<T> clazz){
    	T bean = null;
		try {
			bean = clazz.newInstance();
			BeanMap beanMap = BeanMap.create(bean);
			Map m = new HashMap<>();
			map.forEach((k,v)->{
				if(v instanceof java.sql.Timestamp) {
					m.put(FieldUtils.columnNameToFieldName(String.valueOf(k)), dateToStr((java.sql.Timestamp)v));
				}else {
					m.put(FieldUtils.columnNameToFieldName(String.valueOf(k)), v);
				}
			});
			beanMap.putAll(m);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
    	return bean;
    }

    public static String dateToStr(java.sql.Timestamp time) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String str = df.format(time);
        return str;
    }
    public static String dateToStr(java.sql.Timestamp time, String strFormat) {
        DateFormat df = new SimpleDateFormat(strFormat);
        String str = df.format(time);
        return str;
    }


    // -------------------   cglib BeanMap  -------------------
    //map对象转java
    public static <T> T MapToObject(Map<String, Object> map, Class<T> beanClass) throws Exception {
        T object = beanClass.newInstance();
        BeanMap beanMap = BeanMap.create(object);
        beanMap.putAll(map);
        return object;
    }

    //java对象转map
    public static Map<String, Object> ObjectToMap(Object obj) {
        Map<String, Object> map = new HashMap();
        if (obj != null) {
            BeanMap beanMap = BeanMap.create(obj);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }
}

