package com.roncoo.spring.boot.autoconfigure.xxl.job;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.handler.annotation.XxlJob;

/**
 * 
 * @author Wujun
 */
@Configuration
@ConditionalOnClass(XxlJob.class)
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobAutoConfiguration {

	private final XxlJobProperties properties;

	public XxlJobAutoConfiguration(XxlJobProperties properties) {
		this.properties = properties;
	}

	@Bean(initMethod = "start", destroyMethod = "destroy")
	public XxlJobSpringExecutor xxlJobExecutor() {
		XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
		xxlJobSpringExecutor.setAdminAddresses(properties.getAdmin().getAddresses());
		xxlJobSpringExecutor.setAppName(properties.getExecutor().getAppName());
		xxlJobSpringExecutor.setIp(properties.getExecutor().getIp());
		xxlJobSpringExecutor.setPort(properties.getExecutor().getPort());
		xxlJobSpringExecutor.setAccessToken(properties.getAccessToken());
		xxlJobSpringExecutor.setLogPath(properties.getExecutor().getLogPath());
		xxlJobSpringExecutor.setLogRetentionDays(properties.getExecutor().getLogRetentionDays());
		return xxlJobSpringExecutor;
	}

}
