package com.jun.plugin.configuration;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 定时任务配置
 * 
 * @ClassName: DCSQuartzConfigration
 * @Description:
 * @author: renkai721
 * @date: 2018年6月29日 上午11:08:46
 */
@Configuration
// 开启异步事件的支持
@EnableAsync
public class DCSQuartzConfigration {
	/**
     * 核心线程数，默认为1
     */
	@Value("${boiler.quartz.corePoolSize}")
	private int corePoolSize;
	/**
     * 最大线程数，默认为Integer.MAX_VALUE
     */
	@Value("${boiler.quartz.maxPoolSize}")
	private int maxPoolSize;
	/**
     * 队列最大长度 >=mainExecutor.maxSize
     */
	@Value("${boiler.quartz.queueCapacity}")
	private int queueCapacity;

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.initialize();
		return executor;
	}
}
