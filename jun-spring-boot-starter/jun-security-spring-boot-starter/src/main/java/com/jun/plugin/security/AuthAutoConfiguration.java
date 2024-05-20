package com.jun.plugin.security;

import com.jun.plugin.security.interceptor.AuthenticeInterceptor;
import com.jun.plugin.security.interceptor.PermissionInterceptor;
import com.jun.plugin.security.interfaces.PermissionInfoInterface;
import com.jun.plugin.security.provider.AuthProvider;
import com.jun.plugin.security.provider.JdbcAuthProvider;
import com.jun.plugin.security.provider.RedisAuthProvider;
import com.jun.plugin.security.provider.SingleAuthProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * <p>
 *  自动配置类
 * </p>
 *
 * @since 2022-12-13 11:45
 **/
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthAutoConfiguration {
    final static Logger logger = LoggerFactory.getLogger(AuthAutoConfiguration.class);

    @Autowired
    private AuthProperties authProperties;


    /**
     * 注入redisAuthProvider
     */
    @ConditionalOnMissingBean(AuthProvider.class)
    @ConditionalOnProperty(name = "jun-security.store-type", havingValue = "redis")
    @Bean
    public AuthProvider redisAuthProvider(StringRedisTemplate stringRedisTemplate) {
        if (stringRedisTemplate == null) {
            logger.error("AuthAutoConfiguration: Bean StringRedisTemplate is null!");
            return null;
        }
        logger.info("RedisAuthProvider is running!");
        return new RedisAuthProvider(stringRedisTemplate, authProperties);
    }

    /**
     * 注入jdbcAuthProvider
     */
    @ConditionalOnMissingBean(AuthProvider.class)
    @ConditionalOnProperty(name = "jun-security.store-type", havingValue = "jdbc")
    @Bean
    public AuthProvider jdbcAuthProvider(JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            logger.error("AuthAutoConfiguration: Bean JdbcTemplate is null!");
            return null;
        }
        logger.info("JdbcAuthProvider is running!");
        return new JdbcAuthProvider(jdbcTemplate, authProperties);
    }

    /**
     * 注入singleAuthProvider
     */
    @ConditionalOnMissingBean(AuthProvider.class)
    @ConditionalOnProperty(name = "jun-security.store-type", havingValue = "single", matchIfMissing = true)
    @Bean
    public AuthProvider singleAuthProvider() {
        logger.info("SingleAuthProvider is running!");
        return new SingleAuthProvider(authProperties);
    }


    /**
     * 添加会话拦截器( 注入AuthStore（可能是redis的，也可能是jdbc的，根据配置来的）)
     */
    @Bean
    public AuthenticeInterceptor authenticeInterceptor(AuthProvider authProvider) {
        if (authProvider != null) {
            return new AuthenticeInterceptor(authProvider);
        } else {
            logger.error("AuthAutoConfiguration: Bean AuthProvider Not Defined!");
            return null;
        }
    }


    /**
     * 添加权限拦截器（当存在bean PermissionInfoInterface时，这个配置才生效）
     * 注入PermissionInfoInterface
     */
    @Bean
    @ConditionalOnBean(PermissionInfoInterface.class)
    public PermissionInterceptor permissionInterceptor(PermissionInfoInterface permissionInfoInterface) {
        if (permissionInfoInterface != null) {
            return new PermissionInterceptor(permissionInfoInterface);
        } else {
            logger.error("AuthAutoConfiguration: Bean PermissionInfoInterface Not Defined!");
            return null;
        }
    }

}
