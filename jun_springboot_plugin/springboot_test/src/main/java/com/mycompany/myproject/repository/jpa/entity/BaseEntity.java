package com.mycompany.myproject.repository.jpa.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract  class BaseEntity implements Serializable {

    @CreatedBy
    @Column(name = "create_by", nullable = false, updatable = false)
    private String createBy;

    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    private Date createDate;

    @LastModifiedBy
    private String updateBy;

    @LastModifiedDate
    private Date updateDate;

//    @PrePersist
//    public void prePersist(){
//        if(StringUtils.isEmpty(this.createBy)) this.createBy =  "barry";
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//       if(StringUtils.isEmpty(this.updateBy)) this.updateBy =  "barry";
//    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
