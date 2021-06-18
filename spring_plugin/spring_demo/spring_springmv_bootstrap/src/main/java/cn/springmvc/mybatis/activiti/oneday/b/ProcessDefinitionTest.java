package cn.springmvc.mybatis.activiti.oneday.b;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.springmvc.mybatis.activiti.oneday.a.HelloWorld;

public class ProcessDefinitionTest {

    private static final Logger log = LoggerFactory.getLogger(HelloWorld.class);

    // 工作流引擎对象
    // ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();// 这行代码默认加载activiti.cfg.xml
    @Resource
    private ProcessEngine processEngine;

    // 部署流程定义
    @Test
    public void deploymentProcessDefinition_classpath() {
        // 主要是ACT_RE打头的表
        RepositoryService repositoryService = processEngine.getRepositoryService();// 与流程定义部署对象相关的service
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("sequenceFlow.bpmn");
        InputStream inputStreamPng = this.getClass().getResourceAsStream("sequenceFlow.png");

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();// 创建一个部署对象
        deploymentBuilder.name("流程定义");// 添加部署的名称
        deploymentBuilder.addInputStream("helloworld.bpmn", inputStreamBpmn);//
        deploymentBuilder.addInputStream("helloworld.png", inputStreamPng);//
        // deploymentBuilder.addClasspathResource("diagrams/helloworld.bpmn");// 从classpath的资源中加载，一次一个文件
        // deploymentBuilder.addClasspathResource("diagrams/helloworld.png");// 从classpath的资源中加载，一次一个文件

        Deployment deployment = deploymentBuilder.deploy();// 部署完成

        log.debug("部署ID:{}", deployment.getId());// 7501
        log.debug("部署名称:{}", deployment.getName());// 流程定义
    }

    // 部署流程定义
    @Test
    public void deploymentProcessDefinition_zip() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);

        // 主要是ACT_RE打头的表
        RepositoryService repositoryService = processEngine.getRepositoryService();// 与流程定义部署对象相关的service

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();// 创建一个部署对象
        deploymentBuilder.name("流程定义");// 添加部署的名称
        deploymentBuilder.addZipInputStream(zipInputStream);// 指定zip格式的文件完成部署

        Deployment deployment = deploymentBuilder.deploy();// 部署完成

        log.debug("部署ID:{}", deployment.getId());// 7501
        log.debug("部署名称:{}", deployment.getName());// 流程定义
    }

    // 查询流程定义
    @Test
    public void findProcessDefinition() {
        // String deploymentId = "helloword:3:10004";//
        // String processDefinitionKey = "helloword";
        // String processDefinitionNameLike = "Process";
        // int firstResult = 0, maxResults = 10;

        RepositoryService repositoryService = processEngine.getRepositoryService();// 与流程定义部署对象相关的service

        // 创建一个流程定义查询
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        // 指定查询条件，where条件
        // processDefinitionQuery.deploymentId("deploymentId");//使用部署对象ID查询
        // processDefinitionQuery.processDefinitionKey(processDefinitionKey);//使用流程定义的KEY查询
        // processDefinitionQuery.processDefinitionNameLike(processDefinitionNameLike);//使用流程定义的名称模糊查询

        processDefinitionQuery.orderByProcessDefinitionVersion().asc();// 按照版本的升序排列
        // processDefinitionQuery.orderByProcessDefinitionName().desc();// 按照流程定义的名称降序排序

        // 返回结果集
        List<ProcessDefinition> processDefinitions = processDefinitionQuery.list();// 返回集合列表
        // processDefinitionQuery.singleResult();//返回唯一结果集
        // processDefinitionQuery.count();//返回结果集数量
        // processDefinitionQuery.listPage(firstResult, maxResults);//分页查询，firstResult=第几个开始,maxResults每页多少条
        if (CollectionUtils.isNotEmpty(processDefinitions)) {
            for (ProcessDefinition pd : processDefinitions) {
                log.debug("流程定义ID:{}", pd.getId());// 流程定义的key+版本+随机生成数
                log.debug("流程定义的名称:{}", pd.getName());// 对应helloworld.bpmn文件中的name属性值
                log.debug("流程定义的key:{}", pd.getKey());// 对应helloworld.bpmn文件中的id属性值
                log.debug("流程定义的版本:{}", pd.getVersion());// 当流程定义的key值相同的相同下，版本升级，默认1
                log.debug("资源名称bpmn文件:{}", pd.getResourceName());
                log.debug("资源名称png文件:{}", pd.getDiagramResourceName());
                log.debug("部署对象ID：{}", pd.getDeploymentId());
                log.debug("#########################################################");
            }
        }
    }

    /** 删除流程定义，一般只给超级管理员用 */
    @Test
    public void deleteProcessDefinition() {
        // 使用部署ID，完成删除
        String deploymentId = "1";

        RepositoryService repositoryService = processEngine.getRepositoryService();// 与流程定义部署对象相关的service

        /**
         * 不带级联的删除 ，只能删除没有启动的流程，如果流程启动，就会抛出异常
         */
        // repositoryService.deleteDeployment(deploymentId);

        /**
         * 级联删除， 不管流程是否启动，都能可以删除
         */
        repositoryService.deleteDeployment(deploymentId, true);

        log.debug("删除成功！");
    }

    /**
     * 查看流程图
     * 
     * @throws IOException
     */
    @Test
    public void viewPic() throws IOException {
        /** 将生成图片放到文件夹下 */
        String deploymentId = "12501";

        RepositoryService repositoryService = processEngine.getRepositoryService();// 与流程定义部署对象相关的service

        // 获取图片资源名称
        List<String> list = repositoryService.getDeploymentResourceNames(deploymentId);

        // 定义图片资源的名称
        String resourceName = "";
        if (CollectionUtils.isNotEmpty(list)) {
            for (String name : list) {
                if (name.indexOf(".png") >= 0) {
                    resourceName = name;
                }
            }
        }

        // 获取图片的输入流
        InputStream in = repositoryService.getResourceAsStream(deploymentId, resourceName);

        // 将图片生成到D盘的目录下
        File file = new File("D:/" + resourceName);

        // 将输入流的图片写到D盘下
        FileUtils.copyInputStreamToFile(in, file);
    }

    /*** 附加功能：查询最新版本的流程定义 */
    @Test
    public void findLastVersionProcessDefinition() {
        RepositoryService repositoryService = processEngine.getRepositoryService();// 与流程定义部署对象相关的service

        // 创建一个流程定义查询
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.orderByProcessDefinitionVersion().asc().list();// 使用流程定义的版本升序排列, 返回集合列表

        /**
         * Map<String,ProcessDefinition></br>
         * map集合的key： 流程定义的key </br>
         * map集合的value：流程定义的对象</br>
         * map集合的特点：当map集合key值相同的情况下，后一次的值将替换前一次的值
         */
        Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (ProcessDefinition pd : list) {
                map.put(pd.getKey(), pd);
            }
        }
        List<ProcessDefinition> pdList = new ArrayList<ProcessDefinition>(map.values());
        if (CollectionUtils.isNotEmpty(pdList)) {
            for (ProcessDefinition pd : pdList) {
                log.debug("流程定义ID:{}", pd.getId());// 流程定义的key+版本+随机生成数
                log.debug("流程定义的名称:{}", pd.getName());// 对应helloworld.bpmn文件中的name属性值
                log.debug("流程定义的key:{}", pd.getKey());// 对应helloworld.bpmn文件中的id属性值
                log.debug("流程定义的版本:{}", pd.getVersion());// 当流程定义的key值相同的相同下，版本升级，默认1
                log.debug("资源名称bpmn文件:{}", pd.getResourceName());
                log.debug("资源名称png文件:{}", pd.getDiagramResourceName());
                log.debug("部署对象ID：{}", pd.getDeploymentId());
                log.debug("#########################################################");
            }
        }
    }

    /** 附加功能：删除流程定义（删除key相同的所有不同版本的流程定义） */
    @Test
    public void deleteProcessDefinitionByKey() {

        // 流程定义的key
        String processDefinitionKey = "task";

        RepositoryService repositoryService = processEngine.getRepositoryService();// 与流程定义部署对象相关的service

        // 创建一个流程定义查询
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        // 先使用流程定义的key查询流程定义，查询出所有的版本
        List<ProcessDefinition> list = processDefinitionQuery.processDefinitionKey(processDefinitionKey).list();

        // 遍历，获取每个流程定义的部署ID
        if (CollectionUtils.isNotEmpty(list)) {
            for (ProcessDefinition pd : list) {
                // 获取部署ID
                String deploymentId = pd.getDeploymentId();
                repositoryService.deleteDeployment(deploymentId, true);
            }
        }
    }

}
