package com.xbd.quartz.listener;

import org.quartz.JobListener;
import org.quartz.Matcher;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.EverythingMatcher;

/**
 * <p>
 * 自定义JobListener，可匹配任务。定义并配置之后，系统可自动注册
 * </p>
 *
 * @author 小不点
 */
public abstract class AbstractJobListener implements JobListener {

	/**
	 * <p>
	 * 返回匹配某一、某些Job的匹配策略
	 * </p>
	 */
	public Matcher<TriggerKey> matcher() {
		return EverythingMatcher.allTriggers();
	}
	
}
