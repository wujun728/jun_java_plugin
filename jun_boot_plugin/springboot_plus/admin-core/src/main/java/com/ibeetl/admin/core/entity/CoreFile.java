package com.ibeetl.admin.core.entity;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import com.ibeetl.admin.core.util.ValidateConfig;
import org.beetl.sql.core.TailBean;
import java.math.*;
import com.ibeetl.admin.core.entity.BaseEntity;

/* 
* 
* gen by Spring Boot2 Admin 2018-03-08
*/
public class CoreFile extends BaseEntity {
    @NotNull(message = "ID不能为空", groups = ValidateConfig.UPDATE.class)
    @SeqID(name = ORACLE_FILE_SEQ_NAME)
    @AutoID
    private Long id;
    // 文件名称
    private String name;
    // 路径
    private String path;
    // 业务ID
    private String bizId;
    // 上传人id
    private Long userId;
    // 创建时间
    private Date createTime;
    private Long orgId;
    private String bizType;
    
    private String fileBatchId;

    public CoreFile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 文件名称
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 文件名称
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 路径
     * 
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * 路径
     * 
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 业务ID
     * 
     * @return
     */
    public String getBizId() {
        return bizId;
    }

    /**
     * 业务ID
     * 
     * @param bizId
     */
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    /**
     * 上传人id
     * 
     * @return
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 上传人id
     * 
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 创建时间
     * 
     * @return
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * 
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getFileBatchId() {
        return fileBatchId;
    }

    public void setFileBatchId(String fileBatchId) {
        this.fileBatchId = fileBatchId;
    }
    
    
}
