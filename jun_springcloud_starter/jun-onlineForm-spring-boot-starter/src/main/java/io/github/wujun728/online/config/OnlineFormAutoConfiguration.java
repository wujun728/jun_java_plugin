package io.github.wujun728.online.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(name = "org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration")
@MapperScan("io.github.wujun728.online.dao")
public class OnlineFormAutoConfiguration {

    /**
     * 配置MyBatis Mapper扫描
     */
    @Bean
    @ConditionalOnMissingBean
    public MapperScannerConfig mapperScannerConfig() {
        return new MapperScannerConfig();
    }

    public static class MapperScannerConfig {
        // 这个类用于支持自动配置
    }
}
