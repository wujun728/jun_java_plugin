package com.kenfo.util;

import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.task.Task;

/**
 * 根据会签的一票否决制编写
 * @author kenfo
 *
 */
public class Sign implements Command<Boolean>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public static String VAR_SIGN="同意";  
    private String PASS;  
    private String NOPASS;  
    private String parentTaskId;  
    private Task parentTask;  
    private Task joinTask;
    String pid;  
    
    public Sign(String parentTaskId,Task joinTask,String PASS,String NOPASS){  
        this.parentTaskId=parentTaskId;  
        this.PASS=PASS;  
        this.NOPASS=NOPASS;  
        this.joinTask=joinTask;  
    }  
      
    public String getPid(){  
        return pid;  
    }  
      
    public Boolean execute(Environment environment) throws Exception {
        TaskService taskService = environment.get(TaskService.class);  
        
        parentTask = taskService.getTask(parentTaskId);  
        pid = parentTask.getExecutionId();  
        String sign=(String)taskService.getVariable(joinTask.getId(), VAR_SIGN); 
        
        if(sign != null && sign.equals("不同意")){
            taskService.completeTask(joinTask.getId());  
            taskService.completeTask(parentTask.getId(), NOPASS);  
            return true;  
        }  
        taskService.completeTask(joinTask.getId());  
        if(taskService.getSubTasks(parentTaskId).size()==0){
            taskService.completeTask(parentTaskId,PASS);  
            return true;  
        }  
        return false;  
    }  

}
