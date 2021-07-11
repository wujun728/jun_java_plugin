package com.kenfo.util;

import java.util.List;

import org.jbpm.api.Configuration;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.OpenTask;

/**
 * 根据会签的一票否决制编写
 * @author kenfo
 *
 */
public class AssignTask implements AssignmentHandler{

	private static final long serialVersionUID = 1L;  
	
    List<String> participants;  
    ProcessEngine processEngine=Configuration.getProcessEngine();  
    TaskService taskService=processEngine.getTaskService();
    
	@SuppressWarnings("unchecked")
	@Override
	public void assign(Assignable assignable, OpenExecution execution)
			throws Exception {
		System.out.println("分配");  
        String pid = execution.getProcessInstance().getId();  
        System.out.println("pid :"+pid);  
        Task task = taskService.createTaskQuery().processInstanceId(pid)
        		.activityName(execution.getName()).uniqueResult();  
        System.out.println(task.getName());  
        participants = (List<String>)taskService.getVariable(task.getId(), "participants");  
        if(participants != null){  
            for(String participant:participants){  
                System.out.println(participant);  
                Task subTask = ((OpenTask)task).createSubTask();  
                subTask.setAssignee(participant);  
                taskService.addTaskParticipatingUser(task.getId(), participant, Participation.CLIENT);  
            }  
        }  
		
	}

}
