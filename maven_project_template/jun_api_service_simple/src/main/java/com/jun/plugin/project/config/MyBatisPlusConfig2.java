package com.jun.plugin.project.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus config
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
@Configuration
public class MyBatisPlusConfig2 {
    /**
     * 配置mybatis-plus 分页查件
     */
    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        return new PaginationInnerInterceptor();
    }
}