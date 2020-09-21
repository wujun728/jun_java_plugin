/**
 * JobDetailMeta.java
 * Created at 2017-06-02
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.meta;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述 : JobDetailMeta
 *
 * @author Administrator
 */
@Data
public class JobDetailMeta implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 名称
     */
    private String name;

    /**
     * 描述 : 组别
     */
    private String group = "default";

    /**
     * 描述 : 类型
     */
    private String className;

    /**
     * 描述 : 描述
     */
    private String description = "暂无描述";

    /**
     * 描述 : 是否可恢复
     */
    private Boolean recovery = false;

    /**
     * 描述 : 是否持久化
     */
    private Boolean durability = true;

    /**
     * 是否自动实例化
     */
    private Boolean autoInit = false;

    /**
     * 描述 : 数据
     */
    private Map<String, String> dataMap;

}
