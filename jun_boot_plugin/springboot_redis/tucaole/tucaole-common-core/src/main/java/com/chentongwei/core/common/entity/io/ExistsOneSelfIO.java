package com.chentongwei.core.common.entity.io;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 是否是自己的数据IO
 */
@Data
public class ExistsOneSelfIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 创建人Id */
    private Long creatorId;
    /** 表名称 */
    private String tableName;

    public ExistsOneSelfIO id(Long id) {
        this.id = id;
        return this;
    }

    public ExistsOneSelfIO creatorId(Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public ExistsOneSelfIO tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }
}
