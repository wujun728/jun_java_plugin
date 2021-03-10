package com.monkeyk.sos.config;


import com.monkeyk.sos.web.WebUtils;
import com.monkeyk.sos.web.filter.CharacterEncodingIPFilter;
import com.monkeyk.sos.web.filter.SOSSiteMeshFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 2018/1/30
 * <p>
 * Spring MVC 扩展配置
 * <p>
 *
 * @author Wujun
 */
@Configuration
public class MVCConfiguration implements WebMvcConfigurer {


    /**
     * 扩展拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        WebMvcConfigurer.super.addInterceptors(registry);
    }


    /**
     * 解决乱码问题
     * For UTF-8
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        converters.add(new StringHttpMessageConverter(Charset.forName(WebUtils.UTF_8)));
    }


    /**
     * 字符编码配置 UTF-8
     */
    @Bean
    public FilterRegistrationBean encodingFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CharacterEncodingIPFilter());
        registrationBean.addUrlPatterns("/*");
        //值越小越靠前
        registrationBean.setOrder(1);
        return registrationBean;
    }


    /**
     * sitemesh filter
     */
    @Bean
    public FilterRegistrationBean sitemesh() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SOSSiteMeshFilter());
        registrationBean.addUrlPatterns("/*");
        //注意: 在 spring security filter之后
        registrationBean.setOrder(8899);
        return registrationBean;
    }


}
