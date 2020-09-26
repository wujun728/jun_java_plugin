/**
 * 
 */
package com.jun.plugin.quartz.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 配置JobFactory使得QuartzJob可以@Autowired注入spring托管的实例
 * 
 * @author ZhaoXinGuo
 * 
 * @createdate 2016-08-15 18:46
 * 
 * @url http://www.micaifengqing.com
 *
 */
public class AutoWiringSpringBeanJobFactory extends SpringBeanJobFactory
		implements
			ApplicationContextAware {

	private transient AutowireCapableBeanFactory beanFactory;
	
	public void setApplicationContext(final ApplicationContext context) {
		beanFactory = context.getAutowireCapableBeanFactory();
	}
	/**
	 * 这里覆盖了super的createJobInstance方法，对其创建出来的类再进行autowire。
	 */
	@Override
	protected Object createJobInstance(final TriggerFiredBundle bundle)
			throws Exception {
		final Object job = super.createJobInstance(bundle);
		beanFactory.autowireBean(job);
		return job;
	}

}
