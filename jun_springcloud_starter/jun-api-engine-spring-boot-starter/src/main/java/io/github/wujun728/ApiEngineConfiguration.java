package io.github.wujun728;

import io.github.wujun728.entity.DBConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DBConfig.class)
public class ApiEngineConfiguration {

    private final DBConfig dbConfig;

    public ApiEngineConfiguration(DBConfig config) {
        this.dbConfig = config;
    }

    @Bean
    @ConditionalOnMissingBean(ApiEngine.class)
    public ApiEngine Engine() {
        return new ApiEngine(dbConfig);
    }
}