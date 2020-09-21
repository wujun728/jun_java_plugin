package org.itkk.udf.connection.pool.druid;

import org.itkk.udf.core.domain.datasource.IBuildDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DruidConfig
 */
@Configuration
public class DruidConfig {

    /**
     * buildDataSource
     *
     * @return IBuildDataSource
     */
    @Bean
    public IBuildDataSource buildDataSource() {
        return new DruidBuild();
    }

}
