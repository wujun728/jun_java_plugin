package cn.springmvc.mybatis.service.workflow;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import cn.springmvc.mybatis.entity.activiti.LeaveBill;
import cn.springmvc.mybatis.entity.activiti.WorkflowBean;
import cn.springmvc.mybatis.entity.auth.User;

public interface ActivitiService {

    /**
     * 查询出所有的请假单
     * 
     * @return 请假单
     */
    public List<LeaveBill> findLeaveBillList();

    /**
     * 根据请假单ID获取请假单
     * 
     * @param leaveBillId
     *            请假单ID
     * @return 请假单
     */
    public LeaveBill findLeaveBillById(Long leaveBillId);

    /**
     * 新增请假单
     * 
     * @param leaveBill
     *            请假单
     */
    public void saveLeaveBill(LeaveBill leaveBill);

    /**
     * 删除，请假申请
     * 
     * @param leaveBillId
     */
    public void deleteLeaveBillById(Long leaveBillId);

    // ======= 以下方法与工作流程方法相关

    /**
     * 查询部署对象信息，对应表（act_re_deployment）
     * 
     * @return
     */
    public List<Deployment> findDeploymentList();

    /**
     * 查询流程定义的信息，对应表（act_re_procdef）
     * 
     * @return
     */
    public List<ProcessDefinition> findProcessDefinitionList();

    /**
     * 发布流程
     * 
     * @param bytes
     * @param filename
     */
    public void saveNewDeploye(byte[] bytes, String filename);

    /**
     * 使用部署对象ID，删除流程定义
     * 
     * @param deploymentId
     */
    public void deleteProcessDefinitionByDeploymentId(String deploymentId);

    /**
     * 获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
     * 
     * @param deploymentId
     * @param imageName
     * @return
     */
    public InputStream findImageInputStream(String deploymentId, String imageName);

    /**
     * 启动流程
     * 
     * @param workflowBean
     */
    public void saveStartProcess(WorkflowBean workflowBean, User user);

    /**
     * 使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>
     * 
     * @param name
     * @return
     */
    public List<Task> findTaskListByName(String name);

    /**
     * 获取任务表单中任务节点的url连接
     * 
     * @param taskId
     * @return
     */
    public String findTaskFormKeyByTaskId(String taskId);

    /**
     * 使用任务ID，查找请假单ID，从而获取请假单信息
     * 
     * @param taskId
     * @return
     */
    public LeaveBill findLeaveBillByTaskId(String taskId);

    /**
     * 已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中
     * 
     * @param taskId
     * @return
     */
    public List<String> findOutComeListByTaskId(String taskId);

    /**
     * 查询所有历史审核人的审核信息，帮助当前人完成审核，返回List<Comment>
     * 
     * @param taskId
     * @return
     */
    public List<Comment> findCommentByTaskId(String taskId);

    /**
     * 提交任务
     * 
     * @param workflowBean
     */
    public void saveSubmitTask(WorkflowBean workflowBean);

    /**
     * 获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
     * 
     * @param taskId
     * @return
     */
    public ProcessDefinition findProcessDefinitionByTaskId(String taskId);

    /**
     * 查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中
     * 
     * @param taskId
     * @return
     */
    public Map<String, Object> findCoordingByTask(String taskId);

    /**
     * 使用请假单ID，查询历史的批注信息
     * 
     * @param leaveBillId
     * @return
     */
    public List<Comment> findCommentByLeaveBillId(Long leaveBillId);

    /** 查询当前人的组任务 */
    public List<Task> findGroupTaskByUserId(String userId);

}
