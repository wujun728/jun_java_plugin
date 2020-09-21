package org.coody.framework.task.loader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.apache.log4j.Logger;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.annotation.Order;
import org.coody.framework.core.constant.FrameworkConstant;
import org.coody.framework.core.entity.AspectEntity;
import org.coody.framework.core.loader.iface.IcopLoader;
import org.coody.framework.core.util.MethodSignUtil;
import org.coody.framework.core.util.PrintException;
import org.coody.framework.core.util.PropertUtil;
import org.coody.framework.core.util.StringUtil;
import org.coody.framework.task.annotation.CronTask;
import org.coody.framework.task.container.TaskContainer;
import org.coody.framework.task.exception.ErrorCronException;
import org.coody.framework.task.trigger.TaskTrigger;

/**
 * 定时任务加载器
 * 
 * @author Coody
 *
 */
@Order(4)
public class TaskLoader implements IcopLoader {
	
	

	private static final Logger logger = Logger.getLogger(TaskLoader.class);


	@Override
	public void doLoader(Set<Class<?>> clazzs) throws Exception {
		if (StringUtil.isNullOrEmpty(clazzs)) {
			return;
		}
		for (Class<?> clazz:clazzs) {
			if (clazz.isAnnotation()) {
				continue;
			}
			if (clazz.isInterface()) {
				continue;
			}
			if(Modifier.isAbstract(clazz.getModifiers())){
				continue;
			}
			if(clazz.isEnum()){
				continue;
			}
			Annotation initBean = PropertUtil.getAnnotation(clazz, AutoBuild.class);
			if (StringUtil.isNullOrEmpty(initBean)) {
				continue;
			}
			Method[] methods = clazz.getDeclaredMethods();
			if (StringUtil.isNullOrEmpty(methods)) {
				continue;
			}
			for (Method method : methods) {
				Annotation cronTask = PropertUtil.getAnnotation(method, CronTask.class);
				if(cronTask==null){
					continue;
				}
				String cron=PropertUtil.getAnnotationValue(cronTask, "value");
				if (StringUtil.isNullOrEmpty(cron)) {
					PrintException.printException(logger, new ErrorCronException(cron, method));
					continue;
				}
				logger.debug("初始化定时任务 >>"+cron+":"+MethodSignUtil.getMethodKey(clazz, method));
				AspectEntity aspectEntity = new AspectEntity();
				// 装载切面控制方法
				aspectEntity.setAnnotationClass(new Class<?>[] { CronTask.class });
				aspectEntity.setAspectInvokeMethod(TaskTrigger.getTriggerMethod());
				FrameworkConstant.writeToAspectMap(MethodSignUtil.getMethodUnionKey(TaskTrigger.getTriggerMethod()), aspectEntity);
				TaskContainer.setTaskEntity(clazz, method, cron);
			}
		}
	}

}
