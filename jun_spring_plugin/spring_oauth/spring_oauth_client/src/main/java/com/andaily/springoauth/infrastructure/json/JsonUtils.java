package com.andaily.springoauth.infrastructure.json;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertySetStrategy;

/**
 * @author Wujun
 */
public abstract class JsonUtils {


    @SuppressWarnings("unchecked")
    public static <T> T textToBean(T expectInstance, String jsonText) {
        return textToBean(expectInstance, jsonText, JavaPropertySetStrategy.INSTANCE);
    }


    @SuppressWarnings("unchecked")
    public static <T> T textToBean(T expectInstance, String jsonText, PropertySetStrategy propertySetStrategy) {
        final JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(expectInstance.getClass());
        jsonConfig.setPropertySetStrategy(propertySetStrategy);

        final JSONObject jsonObject = JSONObject.fromObject(jsonText);
        return (T) JSONObject.toBean(jsonObject, jsonConfig);
    }


    @SuppressWarnings("unchecked")
    public static <T> T textToBean(T expectInstance, String jsonText, CustomJsonConfigurer jsonConfigurer) {
        final JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(expectInstance.getClass());
        jsonConfig.setPropertySetStrategy(JavaPropertySetStrategy.INSTANCE);
        //callback
        jsonConfigurer.config(jsonConfig);

        final JSONObject jsonObject = JSONObject.fromObject(jsonText);
        return (T) JSONObject.toBean(jsonObject, jsonConfig);
    }


    public static <T> String beanToText(T object) {
        final JSONObject jsonObject = JSONObject.fromObject(object);
        return jsonObject.toString();
    }


    private JsonUtils() {
    }


}