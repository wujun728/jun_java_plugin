package com.ibeetl.admin.core.file;

import java.io.OutputStream;

public abstract class PersistFileItem extends FileItem {
    protected Long id;
    protected Long userId;
    protected Long orgId;
    protected String bizType;
    protected String bizId;
    FileTag[] tags;
    public PersistFileItem() {
        this.isTemp = false;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getOrgId() {
        return orgId;
    }
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
    public FileTag[] getTags() {
        return tags;
    }
    public void setTags(FileTag[] tags) {
        this.tags = tags;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBizType() {
        return bizType;
    }
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
    public String getBizId() {
        return bizId;
    }
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }
    
   

}
