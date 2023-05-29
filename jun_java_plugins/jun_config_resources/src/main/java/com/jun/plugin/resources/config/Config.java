package com.jun.plugin.resources.config;

import java.util.Map;

/**
 * Created by Hong on 2017/12/27.
 */
public interface Config {

    String getValue(String key);

    Boolean getBooleanValue(String key);

    Integer getIntegerValue(String key);

    Float getFloatValue(String key);

    Double getDoubleValue(String key);

    Character getCharValue(String key);

    Object get(String key);

    Map<Object, Object> getConfig();

    void clear();
}
