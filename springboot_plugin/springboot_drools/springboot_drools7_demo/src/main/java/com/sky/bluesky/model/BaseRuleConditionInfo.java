package com.sky.bluesky.model;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.model.BaseRuleConditionInfo
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public class BaseRuleConditionInfo extends BaseModel {
    private Long conditionId;//主键
    private Long ruleId;//规则id
    private String conditionName;//条件名称
    private String conditionExpression;//条件表达式
    private String conditionDesc;//条件描述

    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public void setConditionDesc(String conditionDesc) {
        this.conditionDesc = conditionDesc;
    }
}
