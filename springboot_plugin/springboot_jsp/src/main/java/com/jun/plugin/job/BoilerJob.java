package com.jun.plugin.job;
//package cn.hege.job;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import cn.hege.common.cache.IGatRedisKey;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * DCS定时任务
// * 
// * @ClassName: DCSTask
// * @Description:
// * @author: renkai721
// * @date: 2018年6月28日 下午5:30:44
// */
//@Component
//@EnableScheduling
//@Slf4j
//@Async
//@PropertySource("classpath:/application.properties")
//public class DCSJob implements IGatRedisKey {
//	@Autowired
//	public StringRedisTemplate stringRedisTemplate;
//	@Autowired
//	public RestTemplate restTemplate;
//
//	/**
//	 * 
//	 * 
//	 * @author: renkai721
//	 * @date: 2018年7月5日 上午11:15:31
//	 * @Title: keepaliveTask
//	 * @Description:
//	 */
//	@Scheduled(cron = "${boiler.job.dcsTask}")
//	public void keepaliveTask() {
//		log.info("定时任务");	
//	}
//
//}
