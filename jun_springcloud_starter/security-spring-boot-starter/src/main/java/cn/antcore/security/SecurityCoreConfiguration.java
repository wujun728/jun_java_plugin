package cn.antcore.security;

import cn.antcore.security.filter.AuthorizeFilter;
import cn.antcore.security.filter.AuthorizeFilterImpl;
import cn.antcore.security.filter.LoginStatusFilter;
import cn.antcore.security.filter.RedisLoginStatusFilter;
import cn.antcore.security.handle.ApiAuthorizeInterceptor;
import cn.antcore.security.handle.LoginInterceptor;
import cn.antcore.security.helper.SessionStringRedisSerializer;
import cn.antcore.security.session.SessionIdStrategy;
import cn.antcore.security.session.SessionManager;
import cn.antcore.security.session.SessionStrategy;
import cn.antcore.security.session.max.MaxSession;
import cn.antcore.security.session.max.MaxSessionManager;
import cn.antcore.security.session.refresh.RedisSessionTimeoutRefresh;
import cn.antcore.security.session.refresh.SessionTimeoutRefresh;
import cn.antcore.security.session.strategy.HeaderSessionIdStrategy;
import cn.antcore.security.session.strategy.RedisSessionStrategy;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;

/**
 * Security服务端自动配置
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
public class SecurityCoreConfiguration implements WebMvcConfigurer {

    /**
     * SessionId 生成策略
     *
     * @return SessionId生成策略
     */
    @Bean
    @ConditionalOnMissingBean(value = SessionIdStrategy.class)
    public SessionIdStrategy sessionIdStrategy() {
        return new HeaderSessionIdStrategy();
    }

    /**
     * Session 生成策略
     *
     * @param sessionManager Session管理
     * @return Session生成策略
     */
    @Bean
    @ConditionalOnMissingBean(value = SessionStrategy.class)
    public SessionStrategy sessionStrategy(SessionManager sessionManager) {
        return new RedisSessionStrategy(sessionManager);
    }

    /**
     * Session包装器
     *
     * @param sessionStrategy Session策略
     * @return Servlet过滤器
     */
    @Bean
    public SecuritySessionFilter securitySessionFilter(
            @Autowired(required = false) SessionStrategy sessionStrategy) {
        return new SecuritySessionFilter(sessionStrategy);
    }

    /**
     * Session线程池 - 事件发布会使用
     *
     * @return 线程池
     */
    @Bean
    @ConditionalOnMissingBean(value = Executor.class)
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("session-exec-");
        executor.initialize();
        return executor;
    }

    /**
     * 为EventMulticaster添加线程池
     *
     * @param executor
     * @param beanFactory
     * @return
     */
    @Bean(name = "applicationEventMulticaster")
    @ConditionalOnMissingBean(value = SimpleApplicationEventMulticaster.class)
    public SimpleApplicationEventMulticaster applicationEventMulticaster(Executor executor, BeanFactory beanFactory) {
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        simpleApplicationEventMulticaster.setTaskExecutor(executor);
        return simpleApplicationEventMulticaster;
    }

    /**
     * RedisSession 操作Redis工具
     *
     * @param connectionFactory
     * @return
     */
    @Bean(name = "sessionRedisTemplate")
    @ConditionalOnMissingClass(value = "sessionRedisTemplate")
    public RedisTemplate<String, Object> sessionRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new SessionStringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * Redis Session 失效时间刷新实例
     *
     * @return 刷新实例方法
     */
    @Bean
    @ConditionalOnMissingBean(value = RedisSessionTimeoutRefresh.class)
    public SessionTimeoutRefresh sessionTimeoutRefresh() {
        return new RedisSessionTimeoutRefresh();
    }

    /**
     * Session 用户存活数管理
     *
     * @return 存活数管理实例
     */
    @Bean
    @ConditionalOnMissingBean(value = MaxSession.class)
    public MaxSession maxSession() {
        return new MaxSessionManager();
    }

    /**
     * 登录状态拦截器
     *
     * @return Redis Session 登录状态拦截器
     */
    @Bean
    @ConditionalOnMissingBean(value = LoginStatusFilter.class)
    public LoginStatusFilter loginStatusFilter() {
        return new RedisLoginStatusFilter();
    }

    /**
     * 权限拦截器
     *
     * @return 权限拦截器
     */
    @Bean
    @ConditionalOnMissingBean(value = AuthorizeFilter.class)
    public AuthorizeFilter authorizeFilter() {
        return new AuthorizeFilterImpl();
    }


    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private ApiAuthorizeInterceptor apiAuthorizeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor);
        registry.addInterceptor(apiAuthorizeInterceptor);
    }
}
