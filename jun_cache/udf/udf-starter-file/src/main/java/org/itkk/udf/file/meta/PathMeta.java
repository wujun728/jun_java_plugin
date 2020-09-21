/**
 * PathMeta.java
 * Created at 2017-05-26
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.file.meta;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述 : PathMeta
 *
 * @author Administrator
 */
@Data
public class PathMeta implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 应用名称
     */
    private String owner;

    /**
     * 描述 : 文件路径
     */
    private String path;

    /**
     * 描述 : 描述
     */
    private String description;

}
