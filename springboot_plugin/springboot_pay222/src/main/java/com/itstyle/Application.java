package com.itstyle;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * 支付主控 
 * 创建者 科帮网
 * 创建时间 2017年7月27日
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.itstyle.modules" })
@ImportResource({"classpath:spring-context-dubbo.xml"})
@Controller
public class Application extends WebMvcConfigurerAdapter {
	private static final Logger logger = Logger.getLogger(Application.class);

	@RequestMapping("/")
	public String greeting() {
		return "index";
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/cert/**").addResourceLocations(
				"classpath:/cert/");
		super.addResourceHandlers(registry);
		logger.info("自定义静态资源目录,这只是个Demo,生产肯定不会暴露");
	}

	public static void main(String[] args) throws InterruptedException,
			IOException {
		SpringApplication.run(Application.class, args);
		// 初始化 支付宝 微信 银联 参数 涉及机密 此文件不提交 请自行配置加载
		//Configs.init("zfbinfo.properties");
		//ConfigUtil.init("wxinfo.properties");
		//SDKConfig.getConfig().loadPropertiesFromSrc();
		logger.info("支付项目启动 ");
	}

}