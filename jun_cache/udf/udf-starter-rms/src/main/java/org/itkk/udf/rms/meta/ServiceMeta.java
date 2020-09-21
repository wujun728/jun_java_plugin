/**
 * ServiceMeta.java
 * Created at 2016-12-22
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.rms.meta;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述 : ServiceMeta
 *
 * @author Administrator
 */
@Data
public class ServiceMeta implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 应用名称
     */
    private String owner;

    /**
     * 描述 : 地址
     */
    private String uri;

    /**
     * 描述 : 服务方法
     */
    private String method;

    /**
     * 描述 : 是否HTTPS
     */
    private Boolean isHttps = false;

    /**
     * 描述 : 描述
     */
    private String description;

}
