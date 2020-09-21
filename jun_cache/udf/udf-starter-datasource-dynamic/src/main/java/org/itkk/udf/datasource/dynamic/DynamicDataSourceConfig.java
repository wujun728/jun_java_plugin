/**
 * DruidDataSourceConfig.java
 * Created at 2016-11-02
 * Created by wangkang
 * Copyright (C) 2016 egridcloud.com, All rights reserved.
 */
package org.itkk.udf.datasource.dynamic;

import lombok.Data;
import org.itkk.udf.core.domain.datasource.DataSourceMeta;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述 : DynamicDataSourceConfig
 *
 * @author wangkang
 */
@Configuration
@ConfigurationProperties(prefix = "org.itkk.datasource.dynamic")
@Data
public class DynamicDataSourceConfig {

    /**
     * 描述 : 默认数据代码
     */
    private String defaultCode;

    /**
     * 描述 : map
     */
    private Map<String, DataSourceMeta> map;

    /**
     * 描述 : 创建数据源
     *
     * @return 数据源
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(new HashMap<>());
        return dynamicDataSource;
    }

}
