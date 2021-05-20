package com.sky.bluesky.model;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.model.BaseRuleEntityInfo
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
public class BaseRuleEntityInfo extends BaseModel {
    private Long entityId;//主键
    private String entityName;//名称
    private String entityDesc;//描述
    private String entityIdentify;//标识
    private String pkgName;//实体包路径

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityDesc() {
        return entityDesc;
    }

    public void setEntityDesc(String entityDesc) {
        this.entityDesc = entityDesc;
    }

    public String getEntityIdentify() {
        return entityIdentify;
    }

    public void setEntityIdentify(String entityIdentify) {
        this.entityIdentify = entityIdentify;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    /**
     * 方法说明:获取实体类名称
     */
    public String getEntityClazz(){
        int index =  pkgName.lastIndexOf(".");
        return pkgName.substring(index+1);
    }
}
