package com.designpatterns.singleton.code;

import org.junit.platform.commons.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 容器单例
 * @author BaoZhou
 * @date 2018/12/27
 */
public class ContainerSingleton {
    private static Map<String,Object> singletonMap = new ConcurrentHashMap<>();
    public static void putInstance(String key,Object instance){
        if(StringUtils.isNotBlank(key) && instance!=null){
            if(!singletonMap.containsKey(key)){
                singletonMap.put(key,instance);
            }
        }
    }

    public static Object getInstance(String key){
        return singletonMap.get(key);
    }
}
