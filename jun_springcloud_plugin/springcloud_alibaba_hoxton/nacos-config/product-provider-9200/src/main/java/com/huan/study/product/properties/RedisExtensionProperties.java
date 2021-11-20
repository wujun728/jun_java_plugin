package com.huan.study.product.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author huan.fu 2020/11/22 - 13:03
 */
@ConfigurationProperties(prefix = "redis")
@Configuration
@Data
@Slf4j
public class RedisExtensionProperties implements InitializingBean {

    private String host;
    private Integer port;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("redis 配置:[{}]", this);
    }
}
