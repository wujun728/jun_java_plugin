package org.itkk.udf.datasource.dynamic;

import lombok.Data;
import org.itkk.udf.core.domain.datasource.DataSourceMeta;
import org.itkk.udf.core.domain.datasource.IBuildDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 描述 : DataSourceConfig
 *
 * @author wangkang
 */
@Configuration
@ConfigurationProperties(prefix = "org.itkk.datasource")
@Data
public class DataSourceConfig {

    /**
     * 描述 : properties
     */
    private DataSourceMeta properties;

    /**
     * 描述 : 创建数据源
     *
     * @param buildDataSource 数据源构建
     * @return 数据源
     */
    @Bean
    @Primary
    public DataSource dataSource(IBuildDataSource buildDataSource) {
        return buildDataSource.build(this.properties);
    }

}
