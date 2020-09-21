package com.opensource.nredis.proxy.monitor.quartz;

/**
 * 方法执行
 * @author liubing
 *
 */
public interface IFrameworkQuartz {
	
	/**
	 * 执行
	 * @param 失败次数
	 * @return
	 */
	public String invoke(int times);
}
