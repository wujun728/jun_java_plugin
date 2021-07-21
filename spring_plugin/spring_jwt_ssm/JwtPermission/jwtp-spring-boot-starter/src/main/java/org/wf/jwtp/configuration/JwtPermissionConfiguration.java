package org.wf.jwtp.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.*;
import org.wf.jwtp.TokenInterceptor;
import org.wf.jwtp.perm.RestUrlPerm;
import org.wf.jwtp.perm.SimpleUrlPerm;
import org.wf.jwtp.perm.UrlPerm;
import org.wf.jwtp.provider.TokenStore;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * JwtPermission自动配置
 * Created by wangfan on 2018-12-29 下午 2:11.
 */
@ComponentScan("org.wf.jwtp.controller")
@EnableConfigurationProperties(JwtPermissionProperties.class)
public class JwtPermissionConfiguration implements WebMvcConfigurer, ApplicationContextAware {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private ApplicationContext applicationContext;
    @Autowired
    private JwtPermissionProperties properties;

    /**
     * 注入redisTokenStore
     */
    @ConditionalOnMissingBean(TokenStore.class)
    @ConditionalOnProperty(name = "jwtp.store-type", havingValue = "0")
    @Bean
    public TokenStore redisTokenStore() {
        DataSource dataSource = getBean(DataSource.class);
        org.springframework.data.redis.core.StringRedisTemplate stringRedisTemplate = getBean(org.springframework.data.redis.core.StringRedisTemplate.class);
        if (stringRedisTemplate == null) logger.error("JwtPermission: StringRedisTemplate is null");
        return new org.wf.jwtp.provider.RedisTokenStore(stringRedisTemplate, dataSource);
    }

    /**
     * 注入jdbcTokenStore
     */
    @ConditionalOnMissingBean(TokenStore.class)
    @ConditionalOnProperty(name = "jwtp.store-type", havingValue = "1")
    @Bean
    public TokenStore jdbcTokenStore() {
        DataSource dataSource = getBean(DataSource.class);
        if (dataSource == null) logger.error("JwtPermission: DataSource is null");
        return new org.wf.jwtp.provider.JdbcTokenStore(dataSource);
    }

    /**
     * 注入simpleUrlPerm
     */
    @ConditionalOnMissingBean(UrlPerm.class)
    @ConditionalOnProperty(name = "jwtp.url-perm-type", havingValue = "0")
    @Bean
    public UrlPerm simpleUrlPerm() {
        return new SimpleUrlPerm();
    }

    /**
     * 注入restUrlPerm
     */
    @ConditionalOnMissingBean(UrlPerm.class)
    @ConditionalOnProperty(name = "jwtp.url-perm-type", havingValue = "1")
    @Bean
    public UrlPerm restUrlPerm() {
        return new RestUrlPerm();
    }

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TokenStore tokenStore = getBean(TokenStore.class);  // 获取TokenStore
        // 给TokenStore添加配置参数
        if (tokenStore != null) {
            tokenStore.setMaxToken(properties.getMaxToken());
            tokenStore.setFindRolesSql(properties.getFindRolesSql());
            tokenStore.setFindPermissionsSql(properties.getFindPermissionsSql());
        } else {
            logger.error("JwtPermission: Unknown TokenStore");
        }
        UrlPerm urlPerm = getBean(UrlPerm.class);  // 获取UrlPerm
        String[] path = properties.getPath();  // 获取拦截路径
        String[] excludePath = properties.getExcludePath();  // 获取排除路径
        TokenInterceptor interceptor = new TokenInterceptor(tokenStore, urlPerm);
        registry.addInterceptor(interceptor).addPathPatterns(path).excludePathPatterns(excludePath);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取Bean
     */
    private <T> T getBean(Class<T> clazz) {
        T bean = null;
        Collection<T> beans = applicationContext.getBeansOfType(clazz).values();
        while (beans.iterator().hasNext()) {
            bean = beans.iterator().next();
            if (bean != null) break;
        }
        return bean;
    }

}
