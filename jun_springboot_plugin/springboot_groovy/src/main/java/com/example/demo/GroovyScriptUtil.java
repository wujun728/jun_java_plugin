package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2019-12-10.
 * Description:
 * Copyright (c) 2018, 成都冰鉴信息科技有限公司
 * All rights reserved.
 *
 * @author zhangbo
 */
public class GroovyScriptUtil {
    private GroovyScriptUtil() {}
    private static Logger logger = LoggerFactory.getLogger(GroovyScriptUtil.class);
    private static Cache<String, GroovyObject> caffeineCache;

    static {
        ///脚本缓存
        caffeineCache = Caffeine
                .newBuilder()
                .initialCapacity(24)
                .maximumSize(1000)
                .expireAfterAccess(2, TimeUnit.DAYS)
                .build();
    }


    public static JSONObject runScript (String script,String params) {
        ///从缓存中获取groovy脚本，没有则创建一个
        GroovyObject groovyObject = caffeineCache.get(script, key -> {
            ClassLoader parent = Thread.currentThread().getContextClassLoader();
            GroovyClassLoader loader = new GroovyClassLoader(parent);
            Class groovyClass = loader.parseClass(script);
            GroovyObject cacheObj = null;
            try {
                cacheObj = (GroovyObject) groovyClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cacheObj;
        });
        if (ObjectUtils.isEmpty(groovyObject)) {
            return new JSONObject();
        }
        try {
            Object run = groovyObject.invokeMethod("run", params);
            if (run instanceof JSONObject) {
                return (JSONObject) run;
            }
        } catch (Exception e) {
            logger.error("",e);
        }
        return new JSONObject();
    }

    public static void main(String[] args) {
        String script = "package groovy;\n" +
                "\n" +
                "import com.alibaba.fastjson.JSON;\n" +
                "import com.alibaba.fastjson.JSONObject;\n" +
                "public class Test001 {\n" +
                "    public JSONObject run (String map) {\n" +
                "       return  JSON.parseObject(map);\n" +
                "    }\n" +
                "}\n";
        String param = "        {\"1\":2,\"3\":\"4\"}";
        long time1 = System.currentTimeMillis();
        int i = 0;
        for (;;) {
            if (i > 1000000) {
                break;
            }
            //调用
            runScript(script,param);
            i++;
        }
        System.out.println(System.currentTimeMillis()-time1);
    }
}
