package com.sky.bluesky.model;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.model.BaseRuleActionParamInfo
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public class BaseRuleActionParamInfo extends BaseModel {
    private Long actionParamId;//主键
    private Long actionId;//动作id
    private String actionParamName;//参数名称
    private String actionParamDesc;//参数描述
    private String paramIdentify;//标识

    public Long getActionParamId() {
        return actionParamId;
    }

    public void setActionParamId(Long actionParamId) {
        this.actionParamId = actionParamId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getActionParamName() {
        return actionParamName;
    }

    public void setActionParamName(String actionParamName) {
        this.actionParamName = actionParamName;
    }

    public String getActionParamDesc() {
        return actionParamDesc;
    }

    public void setActionParamDesc(String actionParamDesc) {
        this.actionParamDesc = actionParamDesc;
    }

    public String getParamIdentify() {
        return paramIdentify;
    }

    public void setParamIdentify(String paramIdentify) {
        this.paramIdentify = paramIdentify;
    }
}
