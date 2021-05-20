package com.jun.plugin.dfs.base;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseEntity {

    /**
     * 创建日期
     */
    protected Date createDate;

    /**
     * 修改日期
     */
    protected Date updateDate;
}
