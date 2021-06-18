package com.gosalelab.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gosalelab.properties.CacheProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public abstract class AbstractCacheProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected CacheProperties cacheProperties;

    @SuppressWarnings("unchecked")
    protected Object handleReturnValue(String cacheValue, Type returnType) {
        Object result = null;

        try {
            if (returnType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) returnType;
                Type rawType = parameterizedType.getRawType();
                Type[] actualTypes = parameterizedType.getActualTypeArguments();

                if (((Class) rawType).isAssignableFrom(List.class)) {
                    result = JSON.parseArray(cacheValue, (Class)actualTypes[0]);
                }

                if (((Class) rawType).isAssignableFrom(Map.class)) {
                    result = parseToMap(cacheValue, (Class)actualTypes[0], (Class)actualTypes[1]);
                }
            } else {
                result = JSON.parseObject(cacheValue, returnType);
            }
        } catch (Exception e) {
            logger.error("JSON to Object error:", e);
        }

        return result;
    }

    private <K, V> Map<K, V> parseToMap(String json, Class<K> keyType, Class<V> valueType) {
        return JSON.parseObject(json,
                new TypeReference<Map<K, V>>(keyType, valueType) {
                });
    }
}
