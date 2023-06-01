package cn.springmvc.mybatis.activiti.oneday.a;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.springmvc.mybatis.common.utils.DateUtil;

public class HelloWorld {

    private static final Logger log = LoggerFactory.getLogger(HelloWorld.class);

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();// 这行代码默认加载activiti.cfg.xml

    // 部署流程定义
    @Test
    public void deploymentProcessDefinition() {
        RepositoryService repositoryService = processEngine.getRepositoryService();// 与流程定义部署对象相关的service
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("sequenceFlow.bpmn");
        InputStream inputStreamPng = this.getClass().getResourceAsStream("sequenceFlow.png");

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();// 创建一个部署对象
        deploymentBuilder.name("helloworld入门程序");// 添加部署的名称
        deploymentBuilder.addInputStream("helloworld.bpmn", inputStreamBpmn);//
        deploymentBuilder.addInputStream("helloworld.png", inputStreamPng);//
        // deploymentBuilder.addClasspathResource("diagrams/helloworld.bpmn");// 从classpath的资源中加载，一次一个文件
        // deploymentBuilder.addClasspathResource("diagrams/helloworld.png");// 从classpath的资源中加载，一次一个文件

        Deployment deployment = deploymentBuilder.deploy();// 部署完成

        log.debug("部署ID:{}", deployment.getId());// 1
        log.debug("部署名称:{}", deployment.getName());// helloworld入门程序
    }

    // 启动流程实例
    @Test
    public void startProcessInstance() {
        String processDefinitionKey = "helloword";

        // 与正在执行的流程实例和执行对象相关的service
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // ACT_RE_PROCDEF.key
        // 使用流程定义的key启动 流程实例，key对应helloworld.bpmn文件中id的属性值,使用key值启动，默认是按照最新版本的流程定义启动
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey);

        log.debug("流程实例ID：{}", pi.getId());// 2501
        log.debug("流程定义ID：{}", pi.getProcessDefinitionId());// helloword:1:4
    }

    // 查询当前人的个人任务
    @Test
    public void findProcessTask() {
        String assignee = "张三";

        // 与正在执行的任务管理相关的service
        TaskService taskService = processEngine.getTaskService();

        // 创建任务查询对象
        TaskQuery taskQuery = taskService.createTaskQuery();

        List<Task> tasks = taskQuery.taskAssignee(assignee).list();// 指定个人任务查询，指定办理人
        for (Task task : tasks) {
            log.debug("##任务ID:{}", task.getId());// 2504
            log.debug("##任务名称:{}", task.getName());
            log.debug("##任务的创建时间:{}", DateUtil.dateToString(task.getCreateTime(), DateUtil.fm_yyyy_MM_dd_HHmmssSSS));
            log.debug("##任务的办理人:{}", task.getAssignee());
            log.debug("##流程实例ID：{}", task.getProcessInstanceId());
            log.debug("##执行对象ID:{}", task.getExecutionId());
            log.debug("##流程定义ID:{}", task.getProcessDefinitionId());
            log.debug("##########################################################");
        }
    }

    /** 完成我的任务 */
    @Test
    public void completeMyPersonalTask() {
        // 任务ID
        String taskId = "2504";

        TaskService taskService = processEngine.getTaskService();// 与正在执行的任务管理相关的Service
        taskService.complete(taskId);

        log.debug("##完成任务,任务ID：{}", taskId);
        // 这里执行完，流程则跑到李四那了，调 用findProcessTask()方法 ， 查李四能查到。
    }

}
