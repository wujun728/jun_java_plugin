package com.sky.bluesky.model;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.model.BaseRulePropertyInfo
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/25
 */
public class BaseRulePropertyInfo extends BaseModel{
    private Long rulePropertyId;//主键
    private String rulePropertyIdentify;//标识
    private String rulePropertyName;//名称
    private String rulePropertyDesc;//描述
    private String defaultValue;//默认值

    public Long getRulePropertyId() {
        return rulePropertyId;
    }

    public void setRulePropertyId(Long rulePropertyId) {
        this.rulePropertyId = rulePropertyId;
    }

    public String getRulePropertyIdentify() {
        return rulePropertyIdentify;
    }

    public void setRulePropertyIdentify(String rulePropertyIdentify) {
        this.rulePropertyIdentify = rulePropertyIdentify;
    }

    public String getRulePropertyName() {
        return rulePropertyName;
    }

    public void setRulePropertyName(String rulePropertyName) {
        this.rulePropertyName = rulePropertyName;
    }

    public String getRulePropertyDesc() {
        return rulePropertyDesc;
    }

    public void setRulePropertyDesc(String rulePropertyDesc) {
        this.rulePropertyDesc = rulePropertyDesc;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
