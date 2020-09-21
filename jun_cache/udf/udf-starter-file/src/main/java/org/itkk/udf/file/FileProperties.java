/**
 * Config.java
 * Created at 2016-11-19
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.file;

import lombok.Data;
import org.itkk.udf.file.meta.PathMeta;
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
@ConfigurationProperties(prefix = "org.itkk.file.properties")
@Data
public class FileProperties implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 根路径
     */
    private String rootPath;

    /**
     * 描述 : 文件路径 ( key : pathCode )
     */
    private Map<String, PathMeta> path;

}
