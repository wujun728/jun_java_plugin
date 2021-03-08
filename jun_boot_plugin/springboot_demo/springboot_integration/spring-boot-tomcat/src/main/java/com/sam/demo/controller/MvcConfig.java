/**
 * <pre>
 * <b>项目名:</b>系统项目名称
 * <b>包名:</b>com.msxf.wechat.cofigs
 * <b>文件名:</b>MvcConfig.java
 * <b>版本信息:</b>
 * <b>日期:</b>2016年6月3日-上午11:04:27
 * <b> 作者 : </b> bin.li01 
 * <b>Copyright (c)</b> 2016马上消费金融有限公司-版权所有
 * </pre>
 */
package com.sam.demo.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName(InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/jsp/login.jsp");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE );
        super.addViewControllers(registry);
    } 
}
