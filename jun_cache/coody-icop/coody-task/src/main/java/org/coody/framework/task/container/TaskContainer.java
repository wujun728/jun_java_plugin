package org.coody.framework.task.container;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TaskContainer {
	
	
	private static List<TaskEntity> taskContainer=new ArrayList<TaskContainer.TaskEntity>();
	
	
	public static void setTaskEntity(Class<?> clazz,Method method,String cron){
		TaskEntity task=new TaskEntity();
		task.setClazz(clazz);
		task.setCron(cron);
		task.setMethod(method);
		taskContainer.add(task);
	}
	
	public static List<TaskEntity> getTaskEntitys(){
		return taskContainer;
	}
	
	public static class TaskEntity{
		
		private String cron;
		
		private Method method;
		
		private Class<?> clazz;

		public String getCron() {
			return cron;
		}

		public void setCron(String cron) {
			this.cron = cron;
		}

		public Method getMethod() {
			return method;
		}

		public void setMethod(Method method) {
			this.method = method;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}
		
		
		
	}
}
