/**
 * 
 */
package com.jun.plugin.quartz.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 被Spring的Quartz JobDetailBean定时执行的Job类, 支持持久化到数据库实现Quartz集群.<br/>
 * 
 * 因为需要被持久化,只能在每次调度时从QuartzJobBean注入的applicationContext中动态 取出.<br/>
 * 
 * @author Wujun
 * 
 * @createdate 2016-08-15 18:46
 * 
 * @url http://www.micaifengqing.com
 *
 */
@Component
public class TestTask extends QuartzJobBean {
	
	private final Logger logger = LoggerFactory.getLogger(TestTask.class);
	
	//这里也可以直接注入你的业务对象进来，例如下面的UserService，里面就可以写你自己的业务了
	/*@Autowired
	private UserService userService;*/

	@Override
	protected void executeInternal(JobExecutionContext ctx)
			throws JobExecutionException {
		task1();
		task2();
	}

	/**
	 * 任务调度1
	 */
	private void task1() {
		logger.info("----------" + "任务调度器1执行开始时间为：" + System.currentTimeMillis() + "----------");
		logger.info("1111111111111");
		logger.info("----------" + "任务调度器1执行结束时间为：" + System.currentTimeMillis() + "----------");
	}
	
	/**
	 * 任务调度2
	 */
	protected void task2() {
		logger.info("----------" + "任务调度器2执行开始时间为：" + System.currentTimeMillis() + "----------");
		logger.info("222222222222");
		logger.info("----------" + "任务调度器2执行结束时间为：" + System.currentTimeMillis() + "----------");
	}

}
