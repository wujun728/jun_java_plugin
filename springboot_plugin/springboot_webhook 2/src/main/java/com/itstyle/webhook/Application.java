package com.itstyle.webhook;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 启动类
 * 创建者  科帮网
 * 创建时间	2017年11月19日
 * linux 下 后台启动  nohup java -jar spring-boot-webhook.jar & 
 */
@SpringBootApplication
@RestController
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector
public class Application  {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	/**
	 * JSON数据格式
	 * body:{
	 *       "imageUrl":"http://grafana.org/assets/img/blog/mixed_styles.png",
	 *       "message":"Someone is testing the alert notification within grafana.",
	 *       "ruleId":0,
	 *       "ruleName":"Test notification",
	 *       "ruleUrl":"http://grafana.52itstyle.com/",
	 *       "state":"alerting",
	 *       "title":"[Alerting] Test notification",
	 *       "evalMatches":[
	 *             {"value":100,"metric":"High value","tags":null},
	 *             {"value":200,"metric":"Higher Value","tags":null}
	 *          ]
	 *  }
	 */
	@RequestMapping("/send")
    public String webhook(String body,HttpServletRequest request) {
		//处理预警信息(邮件、短信、钉钉)
		logger.info("webhook警报系统,body:{}",body);
		return "success";
    }
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
		logger.info("项目启动 ");
	}
}