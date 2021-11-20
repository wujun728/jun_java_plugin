package com.sky.bluesky.model;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.model.BaseRuleActionRuleRelInfo
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public class BaseRuleActionRuleRelInfo extends BaseModel {
    private Long ruleActionRelId;//主键
    private Long actionId;//动作
    private Long ruleId;//规则

    public Long getRuleActionRelId() {
        return ruleActionRelId;
    }

    public void setRuleActionRelId(Long ruleActionRelId) {
        this.ruleActionRelId = ruleActionRelId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }
}
