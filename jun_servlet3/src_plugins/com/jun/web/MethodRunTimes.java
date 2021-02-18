package com.jun.web;


import org.apache.log4j.Logger;

/**
 *
 * @ClassName: MethodPerformance
 * @Description: 用于记录性能监视信息，简单记录方法耗时
 * @author kaka
 * @date 2014年1月27日 上午10:22:18
 *
 */
public class MethodRunTimes {
	private Logger logger = Logger.getLogger(MethodRunTimes.class);
	private Long beginTime;
	private Long endTime;
	private String serviceMethod;

	public MethodRunTimes(String serviceMethod) {
		this.serviceMethod = serviceMethod;
		beginTime = System.currentTimeMillis();
	}

	public void showPerformance() {
		endTime = System.currentTimeMillis();
		logger.info(serviceMethod + " 花费 " + (endTime - beginTime) + " 毫秒");
	}

}