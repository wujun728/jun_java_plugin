package org.wf.jwtp.configuration.client;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.wf.jwtp.client.ClientInterceptor;
import org.wf.jwtp.configuration.JwtPermissionProperties;
import org.wf.jwtp.perm.RestUrlPerm;
import org.wf.jwtp.perm.SimpleUrlPerm;
import org.wf.jwtp.perm.UrlPerm;

import java.util.Collection;

/**
 * JwtPermission Client端自动配置
 * Created by wangfan on 2018-12-29 下午 2:11.
 */
@EnableConfigurationProperties(JwtPermissionProperties.class)
public class JwtPermissionClientConfiguration implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Autowired
    private JwtPermissionProperties properties;

    /**
     * 注入simpleUrlPerm
     */
    @ConditionalOnProperty(name = "jwtp.url-perm-type", havingValue = "0")
    @Bean
    public UrlPerm simpleUrlPerm() {
        return new SimpleUrlPerm();
    }

    /**
     * 注入restUrlPerm
     */
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
        UrlPerm urlPerm = getBean(UrlPerm.class);  // 获取UrlPerm
        String[] path = properties.getPath();  // 获取拦截路径
        String[] excludePath = properties.getExcludePath();  // 获取排除路径
        ClientInterceptor interceptor = new ClientInterceptor(properties.getAuthCenterUrl(), urlPerm);
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
            if (bean != null) {
                break;
            }
        }
        return bean;
    }

}
