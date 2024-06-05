package com.jun.plugin.codegenerator.admin.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 通用配置
 * 
 */
@Configuration
//@EnableConfigurationProperties(FileUploadProperties.class)
public class WebMvcConfig extends WebMvcConfigurationSupport/* implements WebMvcConfigurer*/ {
	
//  @Resource
//  private FileUploadProperties fileUploadProperties;
	/**
	 * 首页地址
	 */
//    @Value("${shiro.user.indexUrl}")
//    private String indexUrl;


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		super.addCorsMappings(registry);
		registry.addMapping("/**").allowedHeaders("*").allowedMethods("POST", "GET", "PUT", "DELETE")
				.allowedOrigins("*");
	}

	/**
	 * 默认首页的设置，当输入域名是可以自动跳转到默认指定的网页
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("forward:" + indexUrl);
		registry.addViewController("/").setViewName("forward:" + "login.html");
	}


	/**
	 * @description: 访问静态文件,发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。 需要重新指定静态资源
	 * @date: 2021/4/15
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/** 本地文件上传路径 */
//        registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**").addResourceLocations("file:" + Global.getProfile() + "/");

		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/","classpath:/static2/","classpath:/static3/",
				"classpath:/templates/","classpath:/templates2/","classpath:/views/");
//		registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/","classpath:/static2/","classpath:/static3/");
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

		/** swagger配置 */
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		/** 文件下载映射配置,同下 */
//        registry.addResourceHandler(fileUploadProperties.getAccessUrl()).addResourceLocations("file:" + fileUploadProperties.getPath());
	}


	/**
	 * 自定义拦截规则
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RepeatSubmitInterceptor() {
					@Override
					public boolean isRepeatSubmit(HttpServletRequest request) {
						return false;
					}
				}).addPathPatterns("/**")
				.excludePathPatterns("/emp/toLogin","/emp/login","/js/**","/css/**","/images/**");
	}

	/**
	 * 配置请求视图映射
	 * @return
	 */
	@Bean
	public InternalResourceViewResolver resourceViewResolver()
	{
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		//请求视图文件的前缀地址
		internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
		//请求视图文件的后缀
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}

	/**
	 * 视图配置
	 * @param registry
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		super.configureViewResolvers(registry);
		registry.viewResolver(resourceViewResolver());
		/*registry.jsp("/WEB-INF/jsp/",".jsp");*/
	}


}