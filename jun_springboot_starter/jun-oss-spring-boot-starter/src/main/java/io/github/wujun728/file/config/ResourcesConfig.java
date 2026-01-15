//package io.github.wujun728.file.config;
//
//import io.github.wujun728.common.constant.Constants;
//import io.github.wujun728.file.common.comfig.FileUploadProperties;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.*;
//
//import javax.annotation.Resource;
//
///**
// * 通用配置
// *
// */
//@Slf4j
//@Configuration
////@EnableConfigurationProperties(FileUploadProperties.class)
//public class ResourcesConfig implements WebMvcConfigurer {
//
//  @Resource
//  private FileUploadProperties fileUploadProperties;
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        WebMvcConfigurer.super.addCorsMappings(registry);
//        registry.addMapping("/**").allowedHeaders("*").allowedMethods("POST", "GET", "PUT", "DELETE")
//                .allowedOrigins("*");
//    }
//
//    /**
//     * 默认首页的设置，当输入域名是可以自动跳转到默认指定的网页
//     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
////		registry.addViewController("/").setViewName("forward:" + indexUrl);
//        registry.addViewController("/").setViewName("forward:" + "login.html");
//    }
//
//    /**
//     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。 需要重新指定静态资源
//     */
//
//
//    /**
//     * @description: 访问静态文件
//     * @date: 2021/4/15
//     */
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        /** 本地文件上传路径 */
//        registry.addResourceHandler("/files/**").addResourceLocations("file:" + fileUploadProperties.getPath() + "/");
//
//        /** 文件下载映射配置,同下 */
//        registry.addResourceHandler(fileUploadProperties.getAccessUrl()).addResourceLocations("file:" + fileUploadProperties.getPath());
//
//		log.debug("System.getProperty(\"user.dir\")");
//		// 访问路径
//		registry.addResourceHandler("/api/upload/**")
//				// 映射真实路径
//				.addResourceLocations("file:" + System.getProperty("user.dir") + "/");// 必须加"/"，不然映射不到
//	}
//
//}
