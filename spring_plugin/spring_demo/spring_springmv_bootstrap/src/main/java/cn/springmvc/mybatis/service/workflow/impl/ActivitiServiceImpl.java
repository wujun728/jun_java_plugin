package cn.springmvc.mybatis.service.workflow.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.mybatis.entity.activiti.LeaveBill;
import cn.springmvc.mybatis.entity.activiti.WorkflowBean;
import cn.springmvc.mybatis.entity.auth.Role;
import cn.springmvc.mybatis.entity.auth.User;
import cn.springmvc.mybatis.mapper.activiti.LeaveBillMapper;
import cn.springmvc.mybatis.service.auth.AuthService;
import cn.springmvc.mybatis.service.workflow.ActivitiService;

@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private LeaveBillMapper leaveBillMapper;

    @Resource
    private ProcessEngine processEngine;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private FormService formService;

    @Resource
    private HistoryService historyService;

    @Autowired
    private AuthService authService;

    // 查询所有请假单信息
    @Override
    public List<LeaveBill> findLeaveBillList() {
        return leaveBillMapper.findAll();
    }

    // 根据请假单ID查询请假单信息
    @Override
    public LeaveBill findLeaveBillById(Long leaveBillId) {
        return leaveBillMapper.findById(leaveBillId);
    }

    /** 部署流程定义 */
    @Override
    public void saveLeaveBill(LeaveBill leaveBill) {
        if (leaveBill != null && null != leaveBill.getId()) {
            leaveBill.setLeaveDate(Calendar.getInstance().getTime());
            leaveBillMapper.update(leaveBill);
        } else {
            // leaveBill.setId("1");
            leaveBillMapper.insert(leaveBill);
        }
    }

    /** 使用部署对象ID，删除流程定义 */
    @Override
    public void deleteLeaveBillById(Long leaveBillId) {
        leaveBillMapper.delete(leaveBillId);
    }

    /** 查询部署对象信息，对应表（act_re_deployment） */
    @Override
    public List<Deployment> findDeploymentList() {
        List<Deployment> list = repositoryService.createDeploymentQuery()// 创建部署对象查询
            .orderByDeploymenTime().asc()//
            .list();
        return list;
    }

    /** 查询流程定义的信息，对应表（act_re_procdef） */
    @Override
    public List<ProcessDefinition> findProcessDefinitionList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()// 创建流程定义查询
            .orderByProcessDefinitionVersion().asc()//
            .list();
        return list;
    }

    /** 部署流程定义 */
    @Override
    public void saveNewDeploye(byte[] bytes, String filename) {
        try {
            // 2：将byte类型的文件转化成ZipInputStream流
            InputStream stream = new ByteArrayInputStream(bytes);
            ZipInputStream zipInputStream = new ZipInputStream(stream);
            repositoryService.createDeployment()// 创建部署对象
                .name(filename)// 添加部署名称
                .addZipInputStream(zipInputStream)//
                .deploy();// 完成部署
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 使用部署对象ID，删除流程定义 */
    @Override
    public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }

    /** 使用部署对象ID和资源图片名称，获取图片的输入流 */
    @Override
    public InputStream findImageInputStream(String deploymentId, String imageName) {
        return repositoryService.getResourceAsStream(deploymentId, imageName);
    }

    /** 更新请假状态，启动流程实例，让启动的流程实例关联业务 */
    @Override
    public void saveStartProcess(WorkflowBean workflowBean, User user) {
        // 1：获取请假单ID，使用请假单ID，查询请假单的对象LeaveBill
        Long id = workflowBean.getId();
        LeaveBill leaveBill = leaveBillMapper.findById(id);
        // 2：更新请假单的请假状态从0变成1（初始录入-->审核中）
        leaveBill.setState(1);
        leaveBillMapper.update(leaveBill);

        // 3：使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
        String key = leaveBill.getClass().getSimpleName();
        /**
         * 4：从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人 inputUser是流程变量的名称， 获取的办理人是流程变量的值
         */
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("inputUser", user.getId());// 表示惟一用户
        /**
         * 5： (1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务 <br/>
         * (2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
         */
        // 格式：LeaveBill.id的形式（使用流程变量）
        String objId = key + "." + id;
        variables.put("objId", objId);
        // 6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
        runtimeService.startProcessInstanceByKey(key, objId, variables);

        IdentityService identityService = processEngine.getIdentityService();//

        // 这个地方的角色编码是写死了
        String manager_role = "manager_role";
        String boss_role = "boss_role";
        Role managerRole = authService.findRoleByRoleCode(manager_role);
        Role bossRole = authService.findRoleByRoleCode(boss_role);

        Group manager = identityService.createGroupQuery().groupId(managerRole.getCode()).singleResult();
        if (manager == null) {
            // 创建角色
            identityService.saveGroup(new GroupEntity(managerRole.getCode()));// 管理员，即经纪人==》往工作流的角色表里面写一条数据

            List<User> mgUsers = authService.findUserByRoleCode(manager_role);// 所有管理员人员
            for (User u : mgUsers) {
                org.activiti.engine.identity.User aUser = identityService.createUserQuery().userId(u.getId()).singleResult();
                if (aUser == null) {
                    identityService.saveUser(new UserEntity(u.getId()));// ==》往工作流用户表里面写一条数据
                    identityService.createMembership(u.getId(), managerRole.getCode());// ==》往工作流用户角色表里面写一条数据
                }
            }
        }

        Group boss = identityService.createGroupQuery().groupId(bossRole.getCode()).singleResult();
        if (boss == null) {
            identityService.saveGroup(new GroupEntity(bossRole.getCode()));// 总经理，即老总==》往工作流的角色表里面写一条数据
            List<User> bUsers = authService.findUserByRoleCode(boss_role);// 所有老总人员
            for (User u : bUsers) {
                org.activiti.engine.identity.User aUser = identityService.createUserQuery().userId(u.getId()).singleResult();
                if (aUser == null) {
                    identityService.saveUser(new UserEntity(u.getId()));// ==》往工作流用户表里面写一条数据
                    identityService.createMembership(u.getId(), bossRole.getCode());// ==》往工作流用户角色表里面写一条数据
                }
            }
        }

    }

    /** 2：使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task> */
    @Override
    public List<Task> findTaskListByName(String name) {
        List<Task> list = taskService.createTaskQuery()//
            .taskAssignee(name)// 指定个人任务查询
            .orderByTaskCreateTime().asc()//
            .list();
        return list;
    }

    /** 使用任务ID，获取当前任务节点中对应的Form key中的连接的值 */
    @Override
    public String findTaskFormKeyByTaskId(String taskId) {
        TaskFormData formData = formService.getTaskFormData(taskId);
        // 获取Form key的值
        String url = formData.getFormKey();
        return url;
    }

    /** 一：使用任务ID，查找请假单ID，从而获取请假单信息 */
    @Override
    public LeaveBill findLeaveBillByTaskId(String taskId) {
        // 1：使用任务ID，查询任务对象Task
        Task task = taskService.createTaskQuery()//
            .taskId(taskId)// 使用任务ID查询
            .singleResult();
        // 2：使用任务对象Task获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        // 3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
            .processInstanceId(processInstanceId)// 使用流程实例ID查询
            .singleResult();
        // 4：使用流程实例对象获取BUSINESS_KEY
        String buniness_key = pi.getBusinessKey();
        // 5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
        String id = "";
        if (StringUtils.isNotBlank(buniness_key)) {
            // 截取字符串，取buniness_key小数点的第2个值
            id = buniness_key.split("\\.")[1];
        }
        // 查询请假单对象
        // 使用hql语句：from LeaveBill o where o.id=1
        LeaveBill leaveBill = leaveBillMapper.findById(Long.valueOf(id));
        return leaveBill;
    }

    /** 二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中 */
    @Override
    public List<String> findOutComeListByTaskId(String taskId) {
        // 返回存放连线的名称集合
        List<String> list = new ArrayList<String>();
        // 1:使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
            .taskId(taskId)// 使用任务ID查询
            .singleResult();
        // 2：获取流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        // 3：查询ProcessDefinitionEntiy对象
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        // 使用任务对象Task获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        // 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
            .processInstanceId(processInstanceId)// 使用流程实例ID查询
            .singleResult();
        // 获取当前活动的id
        String activityId = pi.getActivityId();
        // 4：获取当前的活动
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
        // 5：获取当前活动完成之后连线的名称
        List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
        if (pvmList != null && pvmList.size() > 0) {
            for (PvmTransition pvm : pvmList) {
                String name = (String) pvm.getProperty("name");
                if (StringUtils.isNotBlank(name)) {
                    list.add(name);
                } else {
                    list.add("默认提交");
                }
            }
        }
        return list;
    }

    /** 获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注 */
    @Override
    public List<Comment> findCommentByTaskId(String taskId) {
        List<Comment> list = new ArrayList<Comment>();
        // 使用当前的任务ID，查询当前流程对应的历史任务ID
        // 使用当前任务ID，获取当前任务对象
        Task task = taskService.createTaskQuery()//
            .taskId(taskId)// 使用任务ID查询
            .singleResult();
        // 获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        // //使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
        // List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()//历史任务表查询
        // .processInstanceId(processInstanceId)//使用流程实例ID查询
        // .list();
        // //遍历集合，获取每个任务ID
        // if(htiList!=null && htiList.size()>0){
        // for(HistoricTaskInstance hti:htiList){
        // //任务ID
        // String htaskId = hti.getId();
        // //获取批注信息
        // List<Comment> taskList = taskService.getTaskComments(htaskId);//对用历史完成后的任务ID
        // list.addAll(taskList);
        // }
        // }
        list = taskService.getProcessInstanceComments(processInstanceId);
        return list;
    }

    /** 指定连线的名称完成任务 */
    @Override
    public void saveSubmitTask(WorkflowBean workflowBean) {
        // 获取任务ID
        String taskId = workflowBean.getTaskId();
        // 获取连线的名称
        String outcome = workflowBean.getOutcome();
        // 批注信息
        String message = workflowBean.getComment();
        // 获取请假单ID
        Long id = workflowBean.getId();

        /**
         * 1：在完成之前，添加一个批注信息，向act_hi_comment表中添加数据，用于记录对当前申请人的一些审核信息
         */
        // 使用任务ID，查询任务对象，获取流程流程实例ID
        Task task = taskService.createTaskQuery()//
            .taskId(taskId)// 使用任务ID查询
            .singleResult();
        // 获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        /**
         * 注意：添加批注的时候，由于Activiti底层代码是使用：</br>
         * String userId = Authentication.getAuthenticatedUserId();</br>
         * CommentEntity comment = new CommentEntity();</br>
         * comment.setUserId(userId);</br>
         * 所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，不过不添加审核人，该字段为null</br>
         * 所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人</br>
         */
        // Authentication.setAuthenticatedUserId(SessionContext.get().getName());
        taskService.addComment(taskId, processInstanceId, message);
        /**
         * 2：如果连线的名称是“默认提交”，那么就不需要设置，如果不是，就需要设置流程变量</br>
         * 在完成任务之前，设置流程变量，按照连线的名称，去完成任务</br>
         * 流程变量的名称：outcome</br>
         * 流程变量的值：连线的名称</br>
         */
        Map<String, Object> variables = new HashMap<String, Object>();
        if (outcome != null && !outcome.equals("默认提交")) {
            variables.put("outcome", outcome);
        }

        // 3：使用任务ID，完成当前人的个人任务，同时流程变量
        taskService.complete(taskId, variables);

        // 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成

        /**
         * 5：在完成任务之后，判断流程是否结束</br>
         * 如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
         */
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
            .processInstanceId(processInstanceId)// 使用流程实例ID查询
            .singleResult();
        // 流程结束了
        if (pi == null) {
            // 更新请假单表的状态从1变成2（审核中-->审核完成）
            LeaveBill bill = leaveBillMapper.findById(Long.valueOf(id));
            bill.setState(2);
            leaveBillMapper.update(bill);
        }
    }

    /** 1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象 */
    @Override
    public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
        // 使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
            .taskId(taskId)// 使用任务ID查询
            .singleResult();
        // 获取流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        // 查询流程定义的对象
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()// 创建流程定义查询对象，对应表act_re_procdef
            .processDefinitionId(processDefinitionId)// 使用流程定义ID查询
            .singleResult();
        return pd;
    }

    /**
     * 二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中</br>
     * map集合的key：表示坐标x,y,width,height</br>
     * map集合的value：表示坐标对应的值</br>
     */
    @Override
    public Map<String, Object> findCoordingByTask(String taskId) {
        // 存放坐标
        Map<String, Object> map = new HashMap<String, Object>();
        // 使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
            .taskId(taskId)// 使用任务ID查询
            .singleResult();
        // 获取流程定义的ID
        String processDefinitionId = task.getProcessDefinitionId();
        // 获取流程定义的实体对象（对应.bpmn文件中的数据）
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        // 流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        // 使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()// 创建流程实例查询
            .processInstanceId(processInstanceId)// 使用流程实例ID查询
            .singleResult();
        // 获取当前活动的ID
        String activityId = pi.getActivityId();
        // 获取当前活动对象
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);// 活动ID
        // 获取坐标
        map.put("x", activityImpl.getX());
        map.put("y", activityImpl.getY());
        map.put("width", activityImpl.getWidth());
        map.put("height", activityImpl.getHeight());
        return map;
    }

    /** 使用请假单ID，查询历史批注信息 */
    @Override
    public List<Comment> findCommentByLeaveBillId(Long leaveBillId) {
        // 使用请假单ID，查询请假单对象
        LeaveBill leaveBill = leaveBillMapper.findById(leaveBillId);
        // 获取对象的名称
        String objectName = leaveBill.getClass().getSimpleName();
        // 组织流程表中的字段中的值
        String objId = objectName + "." + leaveBillId;

        /** 1:使用历史的流程实例查询，返回历史的流程实例对象，获取流程实例ID */
        // HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()//对应历史的流程实例表
        // .processInstanceBusinessKey(objId)//使用BusinessKey字段查询
        // .singleResult();
        // //流程实例ID
        // String processInstanceId = hpi.getId();
        /** 2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID */
        HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()// 对应历史的流程变量表
            .variableValueEquals("objId", objId)// 使用流程变量的名称和流程变量的值查询
            .singleResult();
        // 流程实例ID
        String processInstanceId = hvi.getProcessInstanceId();
        List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
        return list;
    }

    /** 查询当前人的组任务 */
    @Override
    public List<Task> findGroupTaskByUserId(String userId) {
        List<Task> list = processEngine.getTaskService()// 与正在执行的任务管理相关的Service
            .createTaskQuery()// 创建任务查询对象
            /** 查询条件（where部分） */
            .taskCandidateUser(userId)// 组任务的办理人查询
            /** 排序 */
            .orderByTaskCreateTime().asc()// 使用创建时间的升序排列
            /** 返回结果集 */
            .list();// 返回列表
        return list;
    }
}
