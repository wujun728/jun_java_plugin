package cn.springmvc.mybatis.activiti.twoday.j;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ReceiveTaskTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**部署流程定义（从inputStream）*/
	@Test
	public void deploymentProcessDefinition_inputStream(){
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("receiveTask.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("receiveTask.png");
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
						.createDeployment()//创建一个部署对象
						.name("接收活动任务")//添加部署的名称
						.addInputStream("receiveTask.bpmn", inputStreamBpmn)//
						.addInputStream("receiveTask.png", inputStreamPng)//
						.deploy();//完成部署
		System.out.println("部署ID："+deployment.getId());//
		System.out.println("部署名称："+deployment.getName());//
	}
	
	/**启动流程实例+设置流程变量+获取流程变量+向后执行一步*/
	@Test
	public void startProcessInstance(){
		//流程定义的key
		String processDefinitionKey = "receiveTask";
		ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
						.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		System.out.println("流程实例ID:"+pi.getId());//流程实例ID    101
		System.out.println("流程定义ID:"+pi.getProcessDefinitionId());//流程定义ID   helloworld:1:4
		
		/**查询执行对象ID*/
		Execution execution1 = processEngine.getRuntimeService()//
						.createExecutionQuery()//创建执行对象查询
						.processInstanceId(pi.getId())//使用流程实例ID查询
						.activityId("receivetask1")//当前活动的id，对应receiveTask.bpmn文件中的活动节点id的属性值
						.singleResult();
		
		/**使用流程变量设置当日销售额，用来传递业务参数*/
		processEngine.getRuntimeService()//
						.setVariable(execution1.getId(), "汇总当日销售额", 21000);
		
		/**向后执行一步，如果流程处于等待状态，使得流程继续执行*/
		processEngine.getRuntimeService()
						.signal(execution1.getId());
		
		/**查询执行对象ID*/
		Execution execution2 = processEngine.getRuntimeService()//
						.createExecutionQuery()//创建执行对象查询
						.processInstanceId(pi.getId())//使用流程实例ID查询
						.activityId("receivetask2")//当前活动的id，对应receiveTask.bpmn文件中的活动节点id的属性值
						.singleResult();
		
		/**从流程变量中获取汇总当日销售额的值*/
		Integer value = (Integer)processEngine.getRuntimeService()//
						.getVariable(execution2.getId(), "汇总当日销售额");
		System.out.println("给老板发送短信：金额是："+value);
		/**向后执行一步，如果流程处于等待状态，使得流程继续执行*/
		processEngine.getRuntimeService()
						.signal(execution2.getId());
		
	}
	
	
}
