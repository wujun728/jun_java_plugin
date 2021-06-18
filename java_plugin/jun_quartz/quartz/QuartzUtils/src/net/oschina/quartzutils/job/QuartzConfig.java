package net.oschina.quartzutils.job;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 
 * 
 * @author 张大川
 *使用java 做配置文件
 */
@Configuration
public class QuartzConfig {
	@Bean
	public Scheduler getScheduler() throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		return scheduler;
	}

}
