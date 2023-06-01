package cn.springmvc.mybatis.activiti.oneday.d;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessVariablesTest {

    private static final Logger log = LoggerFactory.getLogger(ProcessVariablesTest.class);

    // 工作流引擎对象
    // ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();// 这行代码默认加载activiti.cfg.xml
    @Resource
    private ProcessEngine processEngine;

    // 部署流程定义
    @Test
    public void deploymentProcessDefinition_InputStream() {
        // InputStream
        InputStream inputStreambpmn = this.getClass().getResourceAsStream("processVariables.bpmn");
        InputStream inputStreampng = this.getClass().getResourceAsStream("processVariables.png");

        // 主要是ACT_RE打头的表
        RepositoryService repositoryService = processEngine.getRepositoryService();// 与流程定义部署对象相关的service

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();// 创建一个部署对象
        deploymentBuilder.name("流程定义");// 添加部署的名称
        // deploymentBuilder.addZipInputStream(zipInputStream);// 指定zip格式的文件完成部署
        deploymentBuilder.addInputStream("processVariables.bpmn", inputStreambpmn);// 使用资源文件的名称（要求：与资源文件的名称要一致），和输入流完成部署
        deploymentBuilder.addInputStream("processVariables.png", inputStreampng);// 使用资源文件的名称（要求：与资源文件的名称要一致），和输入流完成部署
        Deployment deployment = deploymentBuilder.deploy();// 部署完成

        log.debug("#部署ID:{}", deployment.getId());// 7501
        log.debug("#部署名称:{}", deployment.getName());// 流程定义
    }

    // 启动流程实例
    @Test
    public void startProcessInstance() {
        String processDefinitionKey = "processVariables";

        // 与正在执行的流程实例和执行对象相关的service
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // ACT_RE_PROCDEF.key
        // 使用流程定义的key启动 流程实例，key对应helloworld.bpmn文件中id的属性值,使用key值启动，默认是按照最新版本的流程定义启动
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey);

        log.debug("流程实例ID：{}", pi.getId());// 2501
        log.debug("流程定义ID：{}", pi.getProcessDefinitionId());// helloword:1:4
    }

    /** 设置流程变量 */
    @Test
    public void setVariables() {
        /** 与任务（正在执行） */
        TaskService taskService = processEngine.getTaskService();
        // 任务ID
        String taskId = "42504";
        /** 一：设置流程变量，使用基本数据类型 */
        // taskService.setVariableLocal(taskId, "请假天数", 5);//与任务ID绑定
        // taskService.setVariable(taskId, "请假日期", new Date());
        // taskService.setVariable(taskId, "请假原因", "回家探亲，一起吃个饭");
        /** 二：设置流程变量，使用javabean类型 */
        /**
         * 当一个javabean（实现序列号）放置到流程变量中，要求javabean的属性不能再发生变化 * 如果发生变化，再获取的时候，抛出异常
         * 
         * 解决方案：在Person对象中添加： private static final long serialVersionUID = 6757393795687480331L; 同时实现Serializable
         */
        Person p = new Person();
        p.setId(10);
        p.setName("翠花");
        // taskService.setVariable(taskId, "人员信息(添加固定版本)", p);
        taskService.setVariable(taskId, "人员信息", p);

        System.out.println("设置流程变量成功！");
    }

    /** 获取流程变量 */
    @Test
    public void getVariables() {
        /** 与任务（正在执行） */
        TaskService taskService = processEngine.getTaskService();
        // 任务ID
        String taskId = "42504";
        /** 一：获取流程变量，使用基本数据类型 */
        // Integer days = (Integer) taskService.getVariable(taskId, "请假天数");
        // Date date = (Date) taskService.getVariable(taskId, "请假日期");
        // String resean = (String) taskService.getVariable(taskId, "请假原因");
        // System.out.println("请假天数：" + days);
        // System.out.println("请假日期：" + date);
        // System.out.println("请假原因：" + resean);
        /** 二：获取流程变量，使用javabean类型 */
        // Person p = (Person) taskService.getVariable(taskId, "人员信息(添加固定版本)");
        Person p = (Person) taskService.getVariable(taskId, "人员信息");
        System.out.println(p.getId() + " " + p.getName());
    }

    /** 模拟设置和获取流程变量的场景 */
    public void setAndGetVariables() {
        /** 与流程实例，执行对象（正在执行） */
        // RuntimeService runtimeService = processEngine.getRuntimeService();
        /** 与任务（正在执行） */
        // TaskService taskService = processEngine.getTaskService();

        /** 设置流程变量 */
        // runtimeService.setVariable(executionId, variableName, value)//表示使用执行对象ID，和流程变量的名称，设置流程变量的值（一次只能设置一个值）
        // runtimeService.setVariables(executionId, variables)//表示使用执行对象ID，和Map集合设置流程变量，map集合的key就是流程变量的名称，map集合的value就是流程变量的值（一次设置多个值）

        // taskService.setVariable(taskId, variableName, value)//表示使用任务ID，和流程变量的名称，设置流程变量的值（一次只能设置一个值）
        // taskService.setVariables(taskId, variables)//表示使用任务ID，和Map集合设置流程变量，map集合的key就是流程变量的名称，map集合的value就是流程变量的值（一次设置多个值）

        // runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);//启动流程实例的同时，可以设置流程变量，用Map集合
        // taskService.complete(taskId, variables)//完成任务的同时，设置流程变量，用Map集合

        /** 获取流程变量 */
        // runtimeService.getVariable(executionId, variableName);//使用执行对象ID和流程变量的名称，获取流程变量的值
        // runtimeService.getVariables(executionId);//使用执行对象ID，获取所有的流程变量，将流程变量放置到Map集合中，map集合的key就是流程变量的名称，map集合的value就是流程变量的值
        // runtimeService.getVariables(executionId, variableNames);//使用执行对象ID，获取流程变量的值，通过设置流程变量的名称存放到集合中，获取指定流程变量名称的流程变量的值，值存放到Map集合中

        // taskService.getVariable(taskId, variableName);//使用任务ID和流程变量的名称，获取流程变量的值
        // taskService.getVariables(taskId);//使用任务ID，获取所有的流程变量，将流程变量放置到Map集合中，map集合的key就是流程变量的名称，map集合的value就是流程变量的值
        // taskService.getVariables(taskId, variableNames);//使用任务ID，获取流程变量的值，通过设置流程变量的名称存放到集合中，获取指定流程变量名称的流程变量的值，值存放到Map集合中

    }

    /** 完成我的任务 */
    @Test
    public void completeMyPersonalTask() {
        // 任务ID
        String taskId = "47502";
        processEngine.getTaskService()// 与正在执行的任务管理相关的Service
            .complete(taskId);
        System.out.println("完成任务：任务ID：" + taskId);
    }

    /** 查询流程变量的历史表 */
    @Test
    public void findHistoryProcessVariables() {
        List<HistoricVariableInstance> list = processEngine.getHistoryService()//
            .createHistoricVariableInstanceQuery()// 创建一个历史的流程变量查询对象
            .variableName("请假天数").list();
        if (list != null && list.size() > 0) {
            for (HistoricVariableInstance hvi : list) {
                System.out.println(hvi.getId() + "   " + hvi.getProcessInstanceId() + "   " + hvi.getVariableName() + "   " + hvi.getVariableTypeName() + "    " + hvi.getValue());
                System.out.println("###############################################");
            }
        }
    }
}
