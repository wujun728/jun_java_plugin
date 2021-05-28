package com.sky.bluesky.model;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.model.BaseRuleActionParamValueInfo
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public class BaseRuleActionParamValueInfo extends BaseModel {
    private Long actionParamValueId;//主键
    private Long ruleActionRelId;//动作规则关系主键
    private Long actionParamId;//动作参数
    private String paramValue;//参数值

    public Long getActionParamValueId() {
        return actionParamValueId;
    }

    public void setActionParamValueId(Long actionParamValueId) {
        this.actionParamValueId = actionParamValueId;
    }

    public Long getRuleActionRelId() {
        return ruleActionRelId;
    }

    public void setRuleActionRelId(Long ruleActionRelId) {
        this.ruleActionRelId = ruleActionRelId;
    }

    public Long getActionParamId() {
        return actionParamId;
    }

    public void setActionParamId(Long actionParamId) {
        this.actionParamId = actionParamId;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}
