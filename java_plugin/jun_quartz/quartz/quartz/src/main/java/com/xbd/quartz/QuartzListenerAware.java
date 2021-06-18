package com.xbd.quartz;

import java.util.Map;

import com.xbd.quartz.listener.AbstractJobListener;
import com.xbd.quartz.listener.AbstractSchedulerListener;
import com.xbd.quartz.listener.AbstractTriggerListener;
import org.apache.commons.collections.MapUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>
 * JobListener、SchedulerListener、TriggerListener自动注册监听
 * </p>
 *
 * @author 小不点
 */
public class QuartzListenerAware implements ApplicationContextAware, InitializingBean {

	private Logger log = LoggerFactory.getLogger(getClass());

	private ApplicationContext applicationContext;

	private Scheduler scheduler;

	@Override
	public void afterPropertiesSet() throws Exception {
		awareSchedulerListeners();
		awareJobListeners();
		awareTriggerListeners();
	}

	/**
	 * <p>
	 * 自动注册SchedulerListener
	 * </p>
	 *
	 * @throws Exception
	 */
	private void awareSchedulerListeners() throws Exception {
		log.info("--------加载SchedulerListener开始--------");
		Map<String, AbstractSchedulerListener> schedulerListenerMap = applicationContext.getBeansOfType(AbstractSchedulerListener.class);
		
		if (MapUtils.isEmpty(schedulerListenerMap)) {
			log.info("系统未注册SchedulerListener！");
			return;
		}
		
		for (AbstractSchedulerListener schedulerListener : schedulerListenerMap.values()) {
			scheduler.getListenerManager().addSchedulerListener(schedulerListener);
		}
		
		log.info("--------加载SchedulerListener结束--------");
	}

	/**
	 * <p>
	 * 自动注册JobListener
	 * </p>
	 *
	 * @throws Exception
	 */
	private void awareJobListeners() throws Exception {
		log.info("--------加载JobListener开始--------");
		Map<String, AbstractJobListener> jobListenerMap = applicationContext.getBeansOfType(AbstractJobListener.class);
		
		if (MapUtils.isEmpty(jobListenerMap)) {
			log.info("系统未注册JobListener！");
			return;
		}
		
		for (AbstractJobListener jobListener : jobListenerMap.values()) {
			scheduler.getListenerManager().addJobListener(jobListener);
		}
		
		log.info("--------加载JobListener结束--------");
	}

	/**
	 * <p>
	 * 自动注册TriggerListener
	 * </p>
	 *
	 * @throws Exception
	 */
	private void awareTriggerListeners() throws Exception {
		log.info("--------加载TriggerListener开始--------");
		Map<String, AbstractTriggerListener> triggerListenerMap = applicationContext.getBeansOfType(AbstractTriggerListener.class);
		
		if (MapUtils.isEmpty(triggerListenerMap)) {
			log.info("系统未注册TriggerListener！");
			return;
		}
		
		for (AbstractTriggerListener abstractTriggerListener : triggerListenerMap.values()) {
			log.info("--------" + abstractTriggerListener.getClass().getName());
			scheduler.getListenerManager().addTriggerListener(abstractTriggerListener);
		}
		
		log.info("--------加载TriggerListener结束--------");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

}
