package com.sky.bluesky.model;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.model.BaseRulePropertyRelInfo
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/25
 */
public class BaseRulePropertyRelInfo extends BaseRulePropertyInfo {
    private Long ruleProRelId;//主键
    private Long ruleId;//规则
    private Long rulePropertyId;//规则属性
    private String rulePropertyValue;//属性值

    public Long getRuleProRelId() {
        return ruleProRelId;
    }

    public void setRuleProRelId(Long ruleProRelId) {
        this.ruleProRelId = ruleProRelId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public Long getRulePropertyId() {
        return rulePropertyId;
    }

    @Override
    public void setRulePropertyId(Long rulePropertyId) {
        this.rulePropertyId = rulePropertyId;
    }

    public String getRulePropertyValue() {
        return rulePropertyValue;
    }

    public void setRulePropertyValue(String rulePropertyValue) {
        this.rulePropertyValue = rulePropertyValue;
    }
}
