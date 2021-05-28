package com.itstyle.dubbo;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itstyle.dubbo.domain.User;
import com.itstyle.dubbo.service.IUserService;
/**
 * @author Wujun
 */
@EnableAutoConfiguration
@ImportResource({"classpath:dubbo.xml"})
@Controller
public class Application  {
	private static final Logger logger = Logger.getLogger(Application.class);
	@Reference
	private IUserService userService;
	@RequestMapping("/")
	@ResponseBody
    public String   greeting() {
		User user = new User("张三", 19);
		userService.saveUser(user);
        return "执行成功";
    }
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
		logger.info("项目启动 ");
	}
}