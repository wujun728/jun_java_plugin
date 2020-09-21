/**
 * ApplicationMeta.java
 * Created at 2016-12-22
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.rms.meta;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述 : ApplicationMeta
 *
 * @author Administrator
 */
@Data
public class ApplicationMeta implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 服务ID
     */
    private String serviceId;

    /**
     * 描述 : 私钥
     */
    private String secret;

    /**
     * 描述 : 权限
     */
    private String purview;

    /**
     * 描述 : 所有服务的调用权限(优先判定)
     */
    private Boolean all = false;

    /**
     * 描述 : 禁止服务调用
     */
    private Boolean disabled = false;

    /**
     * 描述 : 描述
     */
    private String description;

}
