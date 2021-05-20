package cn.springmvc.mybatis.activiti.twoday.i;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class StartTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /** 部署流程定义（从inputStream） */
    @Test
    public void deploymentProcessDefinition_inputStream() {
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("start.bpmn");
        InputStream inputStreamPng = this.getClass().getResourceAsStream("start.png");
        Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
            .createDeployment()// 创建一个部署对象
            .name("开始活动")// 添加部署的名称
            .addInputStream("start.bpmn", inputStreamBpmn)//
            .addInputStream("start.png", inputStreamPng)//
            .deploy();// 完成部署
        System.out.println("部署ID：" + deployment.getId());//
        System.out.println("部署名称：" + deployment.getName());//
    }

    /** 启动流程实例+判断流程是否结束+查询历史 */
    @Test
    public void startProcessInstance() {
        // 流程定义的key
        String processDefinitionKey = "start";
        ProcessInstance pi = processEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的Service
            .startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID:" + pi.getId());// 流程实例ID 101
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());// 流程定义ID helloworld:1:4

        /** 判断流程是否结束，查询正在执行的执行对象表 */
        ProcessInstance rpi = processEngine.getRuntimeService()//
            .createProcessInstanceQuery()// 创建流程实例查询对象
            .processInstanceId(pi.getId()).singleResult();
        // 说明流程实例结束了
        if (rpi == null) {
            /** 查询历史，获取流程的相关信息 */
            HistoricProcessInstance hpi = processEngine.getHistoryService()//
                .createHistoricProcessInstanceQuery()//
                .processInstanceId(pi.getId())// 使用流程实例ID查询
                .singleResult();
            System.out.println(hpi.getId() + "    " + hpi.getStartTime() + "   " + hpi.getEndTime() + "   " + hpi.getDurationInMillis());
        }
    }

}
