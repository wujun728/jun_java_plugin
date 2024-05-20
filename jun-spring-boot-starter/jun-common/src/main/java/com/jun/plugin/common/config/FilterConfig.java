package com.jun.plugin.common.config;

import com.jun.plugin.common.filter.EncodingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Locale;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBeanEncodingFilter(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new EncodingFilter());
        filterRegistrationBean.setOrder(0);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("testFilter");
        return filterRegistrationBean;
    }

//    @Bean
//    public FilterRegistrationBean<EncodingFilter> userFilterConfigFilterRegistrationBean(){
//        FilterRegistrationBean<EncodingFilter> userFilter = new FilterRegistrationBean<>();
//        Builder<EncodingFilter> userBuilder = new Builder<>(userFilter);
//        userBuilder.filterConfiguration(EncodingFilter.class,1,false,"/*");
//        return userFilter;
//    }


    private class Builder<T extends Filter>{

        private FilterRegistrationBean<T> filterRegistrationBean = null;

        public Builder(FilterRegistrationBean<T> filterRegistrationBean){
            this.filterRegistrationBean = filterRegistrationBean;
        }

        public Builder filterConfiguration(Class<? extends Filter> clazz, int order, boolean async, String ...patterns){
            T filter = null;
            try {
                filter = (T)clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                System.out.println("[ " + clazz.toString() + " ] 过滤器对象不存在");
            }
            this.filterRegistrationBean.setFilter(filter); // 设置过滤器
            this.filterRegistrationBean.setOrder(order); // 设置启动顺序
            String clazzPath = clazz.toString().toLowerCase(Locale.ROOT);
            // 配置过滤器的名称，首字母一定要小写，不然拦截了请求后会报错
            this.filterRegistrationBean.setName(clazzPath.substring(clazzPath.lastIndexOf(".")));
            this.filterRegistrationBean.addUrlPatterns(patterns); // 配置拦截的请求地址
            return this;
        }

    }
}

