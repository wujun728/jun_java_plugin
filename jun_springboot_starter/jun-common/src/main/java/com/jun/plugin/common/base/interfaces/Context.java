package com.jun.plugin.common.base.interfaces;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文
 */
public class Context {

    private Map<String, Object> globalMap = new HashMap<String, Object>(); // 全局变量
    private Map<String, Object> stepMap = new HashMap<String, Object>(); // 步骤变量
    private Map<String, Object> tempMap = new HashMap<String, Object>(); // 临时变量


    public Context() {

    }

    public Context(Context context) {
        this.globalMap = new HashMap<>(context.getGlobalMap());
        this.stepMap = new HashMap<>(context.getStepMap());
        this.tempMap = new HashMap<>(context.getTempMap());
    }

    public Map<String, Object> getGlobalMap() {
        return this.globalMap;
    }

    public Map<String, Object> getStepMap() {
        return stepMap;
    }

    public Map<String, Object> getTempMap() {
        return tempMap;
    }


    public void setGlobal(String key, Object value) {
        globalMap.put(key, value);
    }

    public void setStep(String key, Object value) {
        stepMap.put(key, value);
    }

    public void setTemp(String key, Object value) {
        tempMap.put(key, value);
    }

    public Object getGlobal(String key) {
        return globalMap.get(key);
    }
    public String getGlobalStr(String key) {
        return (String) globalMap.get(key);
    }

    public Object getStep(String key) {
        return stepMap.get(key);
    }

    public Object getTemp(String key) {
        return tempMap.get(key);
    }


    public void setGlobalMap(Map<String, Object> globalMap) {
        this.globalMap = globalMap;
    }

    public void setStepMap(Map<String, Object> stepMap) {
        this.stepMap = stepMap;
    }

    public void setTempMap(Map<String, Object> tempMap) {
        this.tempMap = tempMap;
    }


}
