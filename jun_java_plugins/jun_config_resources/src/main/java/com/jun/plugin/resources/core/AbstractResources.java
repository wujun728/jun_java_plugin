package com.jun.plugin.resources.core;

import java.util.HashMap;
import java.util.Map;

import com.jun.plugin.resources.ReadResources;
import com.jun.plugin.resources.Resources;
import com.jun.plugin.resources.convert.impl.*;

/**
 * Created by Hong on 2017/12/26.
 */
public abstract class AbstractResources implements ReadResources, Resources {

    protected Map<Object, Object> map = new HashMap<>();

    @Override
    public Map<Object, Object> getResources() {
        return map;
    }

    @Override
    public void writeLocalProperties() {
        for (Object key : this.getResources().keySet()) {
            System.setProperty((String) key, (String) this.getResources().get(key));
        }
    }

    @Override
    public String getValue(String key) {
        return getResources() == null ? null : new StringConvert().convert(getResources().get(key));
    }

    @Override
    public Boolean getBooleanValue(String key) {
        return getResources() == null ? null : new BooleanConvert().convert(getResources().get(key));
    }

    @Override
    public Integer getIntegerValue(String key) {
        return getResources() == null ? null : new IntegerConvert().convert(getResources().get(key));
    }

    @Override
    public Float getFloatValue(String key) {
        return getResources() == null ? null : new FloatConvert().convert(getResources().get(key));
    }

    @Override
    public Double getDoubleValue(String key) {
        return getResources() == null ? null : new DoubleConvert().convert(getResources().get(key));
    }

    @Override
    public Character getCharValue(String key) {
        return getResources() == null ? null : new CharConvert().convert(getResources().get(key));
    }

    @Override
    public Object get(String key) {
        return getResources() == null ? null : getResources().get(key);
    }

    @Override
    public void clear() {
        if (getResources() != null) {
            getResources().clear();
        }
    }
}
