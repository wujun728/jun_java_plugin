package io.github.wujun728.uidgenerator.config;

import io.github.wujun728.uidgenerator.worker.repository.DbWorkerNodeResposity;
import io.github.wujun728.uidgenerator.worker.repository.WorkerNodeResposity;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
@Component
public class UidDbConfiguration {
    @Value("${spring.datasource.driver-class-name:}")
    private String driverClassName;
    @Value("${spring.datasource.url:}")
    private String url;
    @Value("${spring.datasource.username:}")
    private String username;
    @Value("${spring.datasource.password:}")
    private String password;

    @Bean
    @Primary
    public WorkerNodeResposity workerNodeResposity(ObjectProvider<DataSource> dataSourcePvd) {
        DataSource dataSource = null;
        if (StringUtils.isNotBlank(this.driverClassName) &&
            StringUtils.isNotBlank(this.url) &&
            StringUtils.isNotBlank(this.username) &&
            StringUtils.isNotBlank(this.password)) {

            HikariDataSource hds = new HikariDataSource();
            hds.setDriverClassName(this.driverClassName);
            hds.setJdbcUrl(this.url);
            hds.setUsername(this.username);
            hds.setPassword(this.password);
            dataSource = hds;
        } else {
            dataSource = dataSourcePvd.getIfAvailable();
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new DbWorkerNodeResposity(jdbcTemplate);
    }
}
