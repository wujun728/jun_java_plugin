package com.chentongwei.entity.doutu.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 图片分类
 *
 * @author TongWei.Chen 2017-05-17 13:23:14
 */
public class Catalog implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Integer id;
    /** 分类名称 */
    private String name;
    /** 创建时间 */
    private Date createTime;
    /** 删除状态 0:已删除；1：正常*/
    private Boolean isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
