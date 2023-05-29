package com.jun.plugin.resources.encrypt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.jun.plugin.resources.utils.AES;

/**
 * 资源解密
 * Created by Hong on 2019/4/21
 */
public final class ResourceEncrypt {

    private static String key;

    /**
     * 设置密钥
     *
     * @param key 密钥
     */
    public static void use(String key) {
        ResourceEncrypt.key = key;
    }

    /**
     * 解密Value值
     *
     * @param value Value
     * @return 解密后的值
     */
    public static Object value(Object value) {
        if (key == null || value == null) {
            return value;
        }
        if (!(value instanceof String)) {
            return value;
        }
        String valueStr = (String) value;
        if (valueStr.startsWith("E|")) {
            valueStr = valueStr.substring(2);
            return AES.decrypt(valueStr, key);
        }
        return valueStr;
    }

    /**
     * 解密全部Value值
     *
     * @param resources 资源组
     * @return 解密后的新资源
     */
    public static Map<Object, Object> value(Map<Object, Object> resources) {
        if (resources == null || resources.isEmpty()) {
            return resources;
        }
        Map<Object, Object> newResources = new HashMap<>();
        Iterator<Object> iterator = resources.keySet().iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            Object value = resources.get(key);
            newResources.put(key, ResourceEncrypt.value(value));
        }
        return newResources;
    }

}
