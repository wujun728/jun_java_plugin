package com.sky.bluesky.model;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.model.BaseRuleActionInfo
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public class BaseRuleActionInfo extends BaseModel {

    private Long actionId;//主键
    private Integer actionType;//动作类型
    private String actionName;//动作名称
    private String actionDesc;//动作描述
    private String actionClass;//动作实现类

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public String getActionClass() {
        return actionClass;
    }

    public void setActionClass(String actionClass) {
        this.actionClass = actionClass;
    }

    /**
     * 获取实体标识(例如：com.sky.bluesky.model.TestRule  最后得到 testRule)
     */
    public String getActionClazzIdentify() {
        int index = actionClass.lastIndexOf(".");
        return actionClass.substring(index + 1).substring(0, 1).toLowerCase() +
                actionClass.substring(index + 1).substring(1);
    }
}
