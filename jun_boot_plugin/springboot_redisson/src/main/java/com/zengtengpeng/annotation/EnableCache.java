package com.zengtengpeng.annotation;

import com.zengtengpeng.configuration.CacheConfiguration;
import com.zengtengpeng.configuration.MQConfiguration;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.TYPE })
@Documented
@Import(CacheConfiguration.class)
@Configuration
public @interface EnableCache {

    /**
     * 缓存的名称 @Cacheable,@CachePut,@CacheEvict的value必须包含在这里面
     * @return
     */
    String[] value();

    /**
     * 缓存时间 默认30分钟
     * @return
     */
    long ttl() default 1000*60* 30L;

    /**
     * 最长空闲时间 默认30分钟
     * @return
     */
    long maxIdleTime() default 1000*60* 30L;
}
