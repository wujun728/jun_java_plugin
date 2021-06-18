package com.sky.bluesky.model.fact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：封装规则执行传参对象
 * CLASSPATH: com.sky.bluesky.model.fact.RuleExecutionObject
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/25
 */
//@SuppressWarnings("unchecked")
public class RuleExecutionObject implements Serializable {

    //fact集合
    private List<Object> factObjectList = new ArrayList<>();
    //全局对象集合
    private Map<String, Object> globalMap = new HashMap<>();
    //是否全部执行（默认全部）
    private boolean executeAll = true;
    //根据名称过滤要执行的规则
    private String ruleName;

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 添加fact对象
     *
     * @param factObject fact对象
     */
    public void addFactObject(Object factObject) {
        this.factObjectList.add(factObject);
    }

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 设置Global参数
     *
     * @param key   key
     * @param value 值
     */
    public void setGlobal(String key, Object value) {
        this.globalMap.put(key, value);
    }






    public List<Object> getFactObjectList() {
        return this.factObjectList;
    }

    public void setFactObjectList(List<Object> factObjectList) {
        this.factObjectList = factObjectList;
    }

    public Map<String, Object> getGlobalMap() {
        return globalMap;
    }

    public void setGlobalMap(Map<String, Object> globalMap) {
        this.globalMap = globalMap;
    }

    public boolean isExecuteAll() {
        return executeAll;
    }

    public void setExecuteAll(boolean executeAll) {
        this.executeAll = executeAll;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
}
