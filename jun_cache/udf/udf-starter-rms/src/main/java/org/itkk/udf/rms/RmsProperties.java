/**
 * Config.java
 * Created at 2016-11-19
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.rms;

import lombok.Data;
import org.itkk.udf.rms.meta.ApplicationMeta;
import org.itkk.udf.rms.meta.ServiceMeta;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述 : Config
 *
 * @author Administrator
 */
@Component
@ConfigurationProperties(prefix = "org.itkk.rms.properties")
@Data
public class RmsProperties implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 应用清单(应用名称 : 应用地址)
     */
    private Map<String, ApplicationMeta> application;

    /**
     * 描述 : 服务路径(服务编号 : 服务元数据)
     */
    private Map<String, ServiceMeta> service;

}
