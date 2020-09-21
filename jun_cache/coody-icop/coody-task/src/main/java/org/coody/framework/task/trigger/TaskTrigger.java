package org.coody.framework.task.trigger;

import java.lang.reflect.Method;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.bean.InitBeanFace;
import org.coody.framework.core.container.BeanContainer;
import org.coody.framework.core.point.AspectPoint;
import org.coody.framework.core.util.DateUtils;
import org.coody.framework.core.util.StringUtil;
import org.coody.framework.task.annotation.CronTask;
import org.coody.framework.task.container.TaskContainer;
import org.coody.framework.task.container.TaskContainer.TaskEntity;
import org.coody.framework.task.cron.CronExpression;
import org.coody.framework.task.threadpool.TaskThreadPool;


@AutoBuild
public class TaskTrigger implements InitBeanFace{
	
	private static Map<Method, ZonedDateTime> cronExpressionMap=new ConcurrentHashMap<Method, ZonedDateTime>();
	static Logger logger=Logger.getLogger(TaskTrigger.class);
	
	public static Method getTriggerMethod(){
		Method[] methods=TaskTrigger.class.getDeclaredMethods();
		if(StringUtil.isNullOrEmpty(methods)){
			return null;
		}
		for(Method method:methods){
			if("taskTrigger".equals(method.getName())){
				return method;
			}
		}
		return null;
	}
	
	public static void trigger(Object bean,Method method,String cron,ZonedDateTime zonedDateTime){
		CronExpression express = new CronExpression(cron);
		if(zonedDateTime==null){
			zonedDateTime=ZonedDateTime.now(ZoneId.systemDefault());
		}
		zonedDateTime=express.nextTimeAfter(zonedDateTime);
		cronExpressionMap.put(method, zonedDateTime);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.DATETIME_PATTERN, Locale.CHINA);
		Date nextRunDate=DateUtils.toDate(zonedDateTime.toLocalDateTime().format(formatter));
		long timeRage=nextRunDate.getTime()-System.currentTimeMillis();
		TaskThreadPool.TASK_POOL.schedule(new Runnable() {
			@Override
			public void run() {
				Object[] params={};
				try {
					method.invoke(bean, params);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		},timeRage , TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 定时任务管理
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	public static Object taskTrigger(AspectPoint aspect) throws Throwable {
		Method method=aspect.getMethod();
		CronTask cronTask=method.getAnnotation(CronTask.class);
		Object bean=aspect.getBean();
		String cron=cronTask.value();
		try{
			return aspect.invoke();
		}finally {
			ZonedDateTime zonedDateTime=cronExpressionMap.get(method);
			trigger(bean, method, cron,zonedDateTime);
		}
	}
	
	public void init(){
		for (TaskEntity task : TaskContainer.getTaskEntitys()) {
			Object bean = BeanContainer.getBean(task.getClazz());
			TaskTrigger.trigger(bean, task.getMethod(), task.getCron(), null);
		}
	}
}
