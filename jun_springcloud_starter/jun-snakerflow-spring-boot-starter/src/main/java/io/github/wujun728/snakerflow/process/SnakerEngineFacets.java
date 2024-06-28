package io.github.wujun728.snakerflow.process;

import org.snaker.engine.SnakerEngine;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SnakerEngineFacets封装snakerflow基本操作，可以直接使用
 *
 * @author zhaoguoqing
 * @since 0.1
 */
@Component
public class SnakerEngineFacets {
    @Autowired
    private SnakerEngine engine;

    /**
     * 初始化状态机流程
     *
     * @return 流程主键
     */
    public String initFlows() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("flows/leave.snaker");
        String deploy = engine.process().deploy(stream);
        return deploy;
    }
    
    /**
     * 初始化状态机流程
     *
     * @return 流程主键
     */
    public String initFlowsByName(String filename) {
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	InputStream stream = classLoader.getResourceAsStream("flows/"+filename);
    	String deploy = engine.process().deploy(stream);
    	return deploy;
    }

    /**
     * 获得所有有效流程
     *
     * @return List<Process>
     */
    public List<Process> getAllProcess() {
        QueryFilter filter = new QueryFilter();
        return engine.process().getProcesss(filter);
    }

    /**
     * 通过orderId 获得流程
     *
     * @param orderId 流程实例Id
     * @return List<Process>
     */
    public List<Process> getProcessByOrderId(String orderId) {
        QueryFilter filter = new QueryFilter();
        filter.setOrderId(orderId);
        return engine.process().getProcesss(filter);
    }

    /**
     * 获得执行引擎
     *
     * @return SnakerEngine
     */
    public SnakerEngine getEngine() {
        return engine;
    }

    /**
     * 获得所有流程的名字
     *
     * @return List<String>
     */
    public List<String> getAllProcessNames() {
        List<Process> list = engine.process().getProcesss(new QueryFilter());
        List<String> names = new ArrayList<>();
        for (Process entity : list) {
            if (names.contains(entity.getName())) {
                continue;
            } else {
                names.add(entity.getName());
            }
        }
        return names;
    }

    /**
     * 通过processId发起一个流程实例
     *
     * @param processId 流程ID
     * @param operator  操作人
     * @param args      自定义参数
     * @return Order流程实例
     */
    public Order startInstanceById(String processId, String operator, Map<String, Object> args) {
        return engine.startInstanceById(processId, operator, args);
    }

    /**
     * 通过process name发起一个流程实例
     *
     * @param name     流程 name
     * @param operator 操作人
     * @param args     自定义参数
     * @return Order流程实例
     */
    public Order startInstanceByName(String name, Integer version, String operator, Map<String, Object> args) {
        return engine.startInstanceByName(name, version, operator, args);
    }

    /**
     * 执行流程实例
     *
     * @param name     流程 name
     * @param version  版本
     * @param operator 操作人
     * @param args     自定义参数
     * @return Order流程实例
     */
    public Order startAndExecute(String name, Integer version, String operator, Map<String, Object> args) {
        Order order = engine.startInstanceByName(name, version, operator, args);
        List<Task> tasks = engine.query().getActiveTasks(new QueryFilter().setOrderId(order.getId()));
        List<Task> newTasks = new ArrayList<Task>();
        if (tasks != null && tasks.size() > 0) {
            Task task = tasks.get(0);
            newTasks.addAll(engine.executeTask(task.getId(), operator, args));
        }
        return order;
    }

    /**
     * 执行流程实例
     *
     * @param processId 流程Id
     * @param operator  操作人
     * @param args      自定义参数
     * @return Order流程实例
     */
    public Order startAndExecute(String processId, String operator, Map<String, Object> args) {
        Order order = engine.startInstanceById(processId, operator, args);
        List<Task> tasks = engine.query().getActiveTasks(new QueryFilter().setOrderId(order.getId()));
        List<Task> newTasks = new ArrayList<Task>();
        if (tasks != null && tasks.size() > 0) {
            Task task = tasks.get(0);
            newTasks.addAll(engine.executeTask(task.getId(), operator, args));
        }
        return order;
    }

    /**
     * 通过taskId执行
     *
     * @param taskId   任务Id
     * @param operator 操作人
     * @param args     自定义参数
     * @return List<Task>
     */
    public List<Task> execute(String taskId, String operator, Map<String, Object> args) {
        return engine.executeTask(taskId, operator, args);
    }

    /**
     * 流程跳转
     *
     * @param taskId   任务Id
     * @param operator 操作人
     * @param args     自定义参数
     * @param nodeName 跳转到的节点名称
     * @return List<Task>
     */
    public List<Task> executeAndJump(String taskId, String operator, Map<String, Object> args, String nodeName) {
        return engine.executeAndJumpTask(taskId, operator, args, nodeName);
    }

    /**
     * 通过orderId获取对应的流程task
     *
     * @param orderId 流程实例Id
     * @return List<Task>
     */
    public List<Task> getTasks(String orderId) {
        return engine.query().getActiveTasks(new QueryFilter().setOrderId(orderId));
    }

    /**
     * 转办 主办
     * @param taskId
     * @param operator
     * @param actors
     * @return
     */
    public List<Task> transferMajor(String taskId, String operator, String... actors) {
        List<Task> tasks = engine.task().createNewTask(taskId, TaskModel.TaskType.Major.ordinal(), actors);
        engine.task().complete(taskId, operator);
        return tasks;
    }

    /**
     * 转办 协办
     * @param taskId
     * @param operator
     * @param actors
     * @return
     */
    public List<Task> transferAidant(String taskId, String operator, String... actors) {
        List<Task> tasks = engine.task().createNewTask(taskId, TaskModel.TaskType.Aidant.ordinal(), actors);
        engine.task().complete(taskId, operator);
        return tasks;
    }
}