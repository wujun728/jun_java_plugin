package org.itkk.udf.dal.mybatis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MyBatisConfig
 */
@Component
@ConfigurationProperties(prefix = "org.itkk.dal.mybatis.properties")
@Data
public class MyBatisProperties {
    /**
     * 方言
     */
    private String dialect = "mysql";

    /**
     * 描述 : 需要分表的表名
     */
    private String tableNames;

}
