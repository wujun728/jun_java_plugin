package com.xbd.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * <p>
 * 自动装载Bean到Spring，可在JobBean中直接注入定义的Bean
 * </p>
 *
 * @author 小不点
 */
public class AutowiredSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		Object job = super.createJobInstance(bundle);
		this.applicationContext.getAutowireCapableBeanFactory().autowireBean(job);
		return job;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
