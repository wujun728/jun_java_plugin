package com.snakerflow.demo.utils;
/* Copyright 2013-2015 www.snakerflow.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.apache.commons.lang.StringUtils;
import org.snaker.engine.IProcessService;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.*;
import org.snaker.engine.entity.Process;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.model.TaskModel.TaskType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Component（把普通pojo实例化到spring容器中，相当于配置文件中的 <bean id="" class=""/>）
 *
 */
@Component("SnakerEngineFacade")
public class SnakerEngineFacadeImpl implements SnakerEngineFacade, InitializingBean {

    @Autowired
    private SnakerEngine engine;

    private IProcessService process;

    public SnakerEngine getEngine() {
        return engine;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        process = engine.process();
    }

    public IProcessService getProcess() {
        return process;
    }

    @Override
    public String deploy(String resourcesPath) {
        String processId = process.deploy(StreamHelper
                .getStreamFromClasspath(resourcesPath));
        return processId;
    }

    /**
     * 得到所有流程定义的名称
     *
     * @return
     */
    public List<String> getAllProcessNames() {
        List<Process> list = engine.process().getProcesss(new QueryFilter());
        List<String> names = new ArrayList<String>();
        for (Process entity : list) {
            if (names.contains(entity.getName())) {
                continue;
            } else {
                names.add(entity.getName());
            }
        }
        return names;
    }

    public Order startInstanceById(String processId, String operator, Map<String, Object> args) {
        return engine.startInstanceById(processId, operator, args);
    }

    public Order startInstanceByName(String name, Integer version, String operator, Map<String, Object> args) {
        return engine.startInstanceByName(name, version, operator, args);
    }

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

    public List<Task> execute(String taskId, String operator, Map<String, Object> args) {
        return engine.executeTask(taskId, operator, args);
    }

    public List<Task> executeAndJump(String taskId, String operator, Map<String, Object> args, String nodeName) {
        return engine.executeAndJumpTask(taskId, operator, args, nodeName);
    }

    public List<Task> transferMajor(String taskId, String operator, String... actors) {
        List<Task> tasks = engine.task().createNewTask(taskId, TaskType.Major.ordinal(), actors);
        engine.task().complete(taskId, operator);
        return tasks;
    }

    public List<Task> transferAidant(String taskId, String operator, String... actors) {
        List<Task> tasks = engine.task().createNewTask(taskId, TaskType.Aidant.ordinal(), actors);
        engine.task().complete(taskId, operator);
        return tasks;
    }

    public Map<String, Object> flowData(String orderId, String taskName) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(taskName)) {
            List<HistoryTask> histTasks = engine.query()
                    .getHistoryTasks(
                            new QueryFilter().setOrderId(orderId).setName(
                                    taskName));
            List<Map<String, Object>> vars = new ArrayList<Map<String, Object>>();
            for (HistoryTask hist : histTasks) {
                vars.add(hist.getVariableMap());
            }
            data.put("vars", vars);
            data.put("histTasks", histTasks);
        }
        return data;
    }

    public void addSurrogate(Surrogate entity) {
        if (entity.getState() == null) {
            entity.setState(1);
        }
        engine.manager().saveOrUpdate(entity);
    }

    public void deleteSurrogate(String id) {
        engine.manager().deleteSurrogate(id);
    }

    public Surrogate getSurrogate(String id) {
        return engine.manager().getSurrogate(id);
    }

    public List<Surrogate> searchSurrogate(Page<Surrogate> page, QueryFilter filter) {
        return engine.manager().getSurrogate(page, filter);
    }
}
