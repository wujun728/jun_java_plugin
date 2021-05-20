package net.oschina.quartzutils.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.oschina.quartzutils.jobean.ScheduleJob;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;




/**
 * 
 * @author 张大川
 *
 */
public class TaskUtils {
	public final static Logger log = Logger.getLogger(TaskUtils.class);

	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 * @param scheduleJob
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void invokMethod(ScheduleJob scheduleJob) throws Exception {
		Object object = null;
		Class<?> clazz = null;
		// springId不为空先按springId查找bean
		if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
			object = SpringUtils.getBean(scheduleJob.getSpringId());
		} else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
			try {
				clazz = Class.forName(scheduleJob.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (object == null) {
			log.debug("任务名称 = [" + scheduleJob.getJobName()
					+ "]---------------未启动成功，请检查是否配置正确！！！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(scheduleJob.getMethodName(), null);
			log.debug("调用方法" + method.toString());
		} catch (NoSuchMethodException e) {
			log.debug("任务名称 = [" + scheduleJob.getMethodName()
					+ "]---------------未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	if (method != null) {
//			//try {
			method.invoke(object);
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}

	}

}
