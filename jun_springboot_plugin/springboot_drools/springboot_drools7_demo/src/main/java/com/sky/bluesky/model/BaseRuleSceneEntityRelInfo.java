package com.sky.bluesky.model;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.model.BaseRuleSceneEntityRelInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public class BaseRuleSceneEntityRelInfo extends BaseModel {

    private Long sceneEntityRelId;//主键
    private Long sceneId;//场景id
    private Long entityId;//实体

    public Long getSceneEntityRelId() {
        return sceneEntityRelId;
    }

    public void setSceneEntityRelId(Long sceneEntityRelId) {
        this.sceneEntityRelId = sceneEntityRelId;
    }

    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
