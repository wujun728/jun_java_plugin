package com.jun.mvc.config;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by yejf on 2017/1/11.
 * Spring MVC Configuration
 */
@Configuration  //这是一个配置类
@Import(value = AppConfig.class)  //读入另一个配置类
@ComponentScan({"com.jun"})  //指定扫描的包及其子包
@EnableWebMvc //启动WEB MVC 功能 (和 AOP, DT 一样，默认都是禁用的)
public class WebMvcConfig implements WebMvcConfigurer { //spring5.x后直接实现接口

    //日志信息
    private static final Logger LOGGER = Logger.getLogger(WebMvcConfig.class);

    public WebMvcConfig() {
        LOGGER.debug("--- 初始化WebMvcConfig实例...");
    }

    //添加其它的配置...
    //

    /************
     * 由于spring的前端控制器DispatcherServlet的匹配模式是/
     * 这样所有的资源都将被这个控制器给处理，并去匹配mapping关系，
     * 而像js,html,css, 图片等静态资源无需通过控制器，所以，需要通过重新这些资源的处理方法
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        LOGGER.debug("--- 进入addResourceHandlers方法...");
        //映射存放在 webapps下面的静态资源 [不带 classpath ]
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/");
        //映射存放在 resources下面的静态资源 [要带 classpath ]
        registry.addResourceHandler("/public/**")
                .addResourceLocations("classpath:/public/");
    }

    /*****************
     * 解决文件上传的处理Bean
     * @return
     */
    @Bean
    public MultipartResolver multipartResolver() {
        LOGGER.debug("-- 进入multipartResolver方法中");
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        //设置相关的属性
        multipartResolver.setDefaultEncoding("UTF-8");
        multipartResolver.setMaxUploadSize(20*1024*1024); //20M
        LOGGER.debug("-- 实例化CommonsMultipartResolver对象，并设置了编码和最大位");
        //返回
        return multipartResolver;
    }

    /***********
     * 用于i18n 国际化处理，非必须
     */
    @Bean
    public MessageSource validationMessageSource() {
        LOGGER.debug("--- 进入创建MessageSource实例的代码中");
        //创建一个MessageSource,此处以 ResourceBundleMessageSource 为Bean
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //配置相关的属性值
        messageSource.setDefaultEncoding("UTF-8");
        //设置要读取的资源文件的基础名，可以是多个
        messageSource.setBasenames("validationMessages");
        //是否使用消息代码做为默认的消息
        messageSource.setUseCodeAsDefaultMessage(true);
        //设置缓存时长
        messageSource.setCacheSeconds(60);
        //返回
        LOGGER.debug("-- 创建ResourceBundleMessageSource对象成功，并设置了相关的属性...");
        return messageSource;
    }

}
