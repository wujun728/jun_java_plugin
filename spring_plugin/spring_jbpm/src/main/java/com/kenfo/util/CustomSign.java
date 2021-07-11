package com.kenfo.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jbpm.api.Configuration;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskService;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.history.events.TaskActivityStart;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.stereotype.Service;

/**
 * 会签 自定义活动
 * @author iibm
 *
 */
public class CustomSign implements ExternalActivityBehaviour {
	private String form;

	private static final long serialVersionUID = 1L;
	private static int count = 0;// 同意人数
	private String mainTaskName = "领导会签";
	
	/**
	 * 节点的功能代码
	 */
	public void execute(ActivityExecution activityExecution) throws Exception {
		ExecutionImpl executionimpl = (ExecutionImpl) activityExecution;
		DbSession dbsession = EnvironmentImpl.getFromCurrent(DbSession.class);
		TaskImpl mainTask = dbsession.createTask();
		mainTask.setName(mainTaskName);
		mainTask.setExecution(executionimpl);
		mainTask.setProcessInstance(executionimpl.getProcessInstance());
		mainTask.setSignalling(false);
		mainTask.setExecutionDbid(executionimpl.getDbid());
		dbsession.save(mainTask);
		HistoryEvent.fire(new TaskActivityStart(mainTask), executionimpl);
		
		// 从原任务中分出两个子任务
		//子任务1分配给manager
		TaskImpl subtask1 = mainTask.createSubTask();
		subtask1.setAssignee("manager");
		subtask1.setFormResourceName("sign/task");
		subtask1.setName("领导会签,操作角色:" + "manager");
		subtask1.setSignalling(false);
		subtask1.setExecution(executionimpl);
		subtask1.setExecutionDbid(executionimpl.getDbid());
		subtask1.setSuperTask(mainTask);
		dbsession.save(subtask1);
		HistoryEvent.fire(new TaskActivityStart(subtask1), executionimpl);
		
		//子任务2分配给boss
		TaskImpl subtask2 = mainTask.createSubTask();
		subtask2.setAssignee("boss");
		subtask2.setFormResourceName("sign/task");
		subtask2.setName("领导会签,操作角色:" + "boss");
		subtask2.setSignalling(false);
		subtask2.setExecution(executionimpl);
		subtask2.setExecutionDbid(executionimpl.getDbid());
		subtask2.setSuperTask(mainTask);
		dbsession.save(subtask2);
		HistoryEvent.fire(new TaskActivityStart(subtask2), executionimpl);
		
		executionimpl.setVariable("historyAi", executionimpl.getHistoryActivityInstanceDbid()); 
		// 流程等待
		executionimpl.waitForSignal();
	}
	
	/**
	 * 在当前节点等待时，外部发信号时的行为
	 */
	public void signal(ActivityExecution activityExecution, String signalName,
			Map<String, ?> parms) throws Exception {
		ExecutionImpl executionImpl = (ExecutionImpl) activityExecution;
		TaskService taskService = Configuration.getProcessEngine().getTaskService();
		// 获得活动节点
		Activity activity = executionImpl.getActivity();
		//主task:领导会签 
		TaskImpl mainTask = (TaskImpl)taskService.createTaskQuery().processInstanceId(executionImpl.getProcessInstance()   
				.getId()).activityName(mainTaskName).uniqueResult();
		// 获得子任务
		//List<Task> subTask_list = taskService.getSubTasks(tasks.get(0).getId());
		List<Task> subTaskList = taskService.getSubTasks(mainTask.getId()); 
		Iterator<Task> iter = subTaskList.iterator();
		// 循环所有子任务
		while (iter.hasNext()) {
			Task subTask = iter.next();
			if (parms.get("name").equals(subTask.getAssignee())) {
				if (parms.get("RESULT").equals("同意")) {
					count++;
				}
				
//				DbSession dbsession = EnvironmentImpl.getFromCurrent(DbSession.class); 
//				executionImpl.setHistoryActivityInstanceDbid(executionImpl.getHistoryActivityInstanceDbid()-1);
//				dbsession.update(executionImpl) ; 
//				dbsession.flush(); 
				//移除主任务和子任务的关联关系  
				//mainTask.removeSubTask(subTask);
				taskService.completeTask(subTask.getId()); // 完成当前会签人子任务
				
			}
		}

		// 剩余子任务数量为1个时，则直接跳转下一个流程节点
		if (subTaskList == null || subTaskList.size() == 1) {
			Transition transition = null;
			if ((signalName == null)
					|| ((Task.STATE_COMPLETED.equals(signalName))
							&& (activity.getOutgoingTransitions() != null) && (activity
							.getOutgoingTransitions().size() == 1))) {
				transition = activity.getOutgoingTransitions().get(0); // 获得将跳转到的transition
			} else {
				transition = activity.findOutgoingTransition(signalName);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if (count == 2) {
				count = 0;
				map.put("RESULT", "PASS");
			} else {
				count = 0;
				map.put("RESULT", "NOPASS");
				 System.out.print("会签不通过");
			}
			//taskService.completeTask(tasks.get(0).getId(), map); // 结束父任务
			overTasks(executionImpl);
			executionImpl.take(transition); // 流程继续

		} else { 
			executionImpl.waitForSignal();
		}

	}


	private void overTasks(ExecutionImpl executionimpl){   
		ProcessEngine engine = Configuration.getProcessEngine();   
		TaskService ts = engine.getTaskService();   
		Task mainTask = ts.createTaskQuery().processInstanceId(   
				executionimpl.getProcessInstance().getId())   
		.activityName(mainTaskName).uniqueResult(); 
		//2.2找到所有的子task，然后终止掉
		List<Task> subTaskList = ts.getSubTasks(mainTask.getId());   
		for(Task t : subTaskList){   
			ts.completeTask(t.getId());   
		}  
		//2.3终止主task 
		//2.1先恢复HistoryActivityInstanceDbid 
		executionimpl.setHistoryActivityInstanceDbid( (Long) executionimpl.getVariable("historyAi")); 
		ts.completeTask(mainTask.getId());  
	}
	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

}
