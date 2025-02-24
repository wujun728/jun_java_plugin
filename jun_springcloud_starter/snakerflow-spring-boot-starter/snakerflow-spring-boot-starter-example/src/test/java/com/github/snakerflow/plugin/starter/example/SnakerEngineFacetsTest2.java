package com.github.snakerflow.plugin.starter.example;


//import cn.hutool.core.lang.//Console;
//import cn.hutool.core.lang.Dict;
//import cn.hutool.json.JSONUtil;
//import com.laker.admin.module.flow.process.SnakerEngineFacets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.github.snakerflow.plugin.starter.example.service.SnakerEngineFacets;

import net.minidev.json.JSONUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SnakerEngineFacetsTest2 {
    @Autowired
    SnakerEngineFacets snakerEngineFacets;

    //@Test
    public void initFlows() {
        String flows = snakerEngineFacets.initFlows();
        Assert.assertNotNull(flows);
    }

    //@Test
    public void getAllProcess() {
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        Assert.assertNotNull(allProcess);
    }

    //@Test
    public void getEngine() {
        SnakerEngine engine = snakerEngineFacets.getEngine();
        Assert.assertNotNull(engine);
    }

    //@Test
    public void getAllProcessNames() {
        List<String> allProcessNames = snakerEngineFacets.getAllProcessNames();
        //Console.log(allProcessNames);
        Assert.assertNotNull(allProcessNames);
    }

    //@Test
    public void getProcessByOrderId() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startInstanceById(process.getId(), "apply.operator", null);
            Assert.assertNotNull(dm);
            List<Process> processByOrderId = snakerEngineFacets.getProcessByOrderId(dm.getId());
            //Console.log(processByOrderId);
            Assert.assertNotNull(processByOrderId);
        }
    }

    //@Test
    public void startInstanceById() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startInstanceById(process.getId(), "apply.operator", null);
            Assert.assertNotNull(dm);
        }
    }

    //@Test
    public void startInstanceByName() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startInstanceByName(process.getName(), 0, "apply.operator", null);
            Assert.assertNotNull(dm);
        }
    }


    //@Test
    public void testStartAndExecute() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startAndExecute(process.getId(), "apply.operator", null);
            Assert.assertNotNull(dm);
        }
    }

    //@Test
    public void startAndExecute() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startAndExecute(process.getName(), 0, "apply.operator", null);
            Assert.assertNotNull(dm);
        }
    }

    //@Test
    public void execute() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startAndExecute(process.getId(), "张三", null);
            List<Task> tasks = snakerEngineFacets.getTasks(dm.getId());
            if (!CollectionUtils.isEmpty(tasks)) {
                List<Task> taskList = snakerEngineFacets.execute(tasks.get(0).getId(), "李四", null);
                Assert.assertNotNull(taskList);
            }
        }
    }


    //@Test
    public void getTasks() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startAndExecute(process.getId(), "apply.operator", null);
            List<Task> tasks = snakerEngineFacets.getTasks(dm.getId());
            Assert.assertNotNull(tasks);
        }
    }

    //@Test
    public void executeAndJump() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startAndExecute(process.getId(), "apply.operator", null);
            List<Task> tasks = snakerEngineFacets.getTasks(dm.getId());
            List<Task> taskList = snakerEngineFacets.executeAndJump(tasks.get(0).getId(), "approveDept.operator", null, "end1");
//            System.out.println("tasks" + JSONUtil.toJsonStr(tasks));
            Assert.assertNotNull(tasks);
        }
    }


    @Test
    public void test() {
        snakerEngineFacets.initFlows();
        Process process = snakerEngineFacets.getEngine().process().getProcessByName("leave");
        // 张三创建一个请假单流程
        Map map1 = new HashMap();
        map1.put("user1", "张三排");
        map1.put("user2", "组长");
        map1.put("approveBoss.operator", "老板");
        Order dm = snakerEngineFacets.startInstanceById(process.getId(), "张三", map1/*Dict.create()
                .set("user1", "张三排")
                .set("user2", "组长")
                .set("approveBoss.operator", "老板")*/);
        //Console.log("张三开启请假单流程：{}", dm);
        //
        List<Task> activeTasks = snakerEngineFacets.getEngine().query().getActiveTasks(new QueryFilter().setOperator("张三排"));
        //Console.log("张三排第一次看待办请假单：{}", activeTasks);
        //Console.log("张三排处理请假单");
        snakerEngineFacets.execute(activeTasks.get(0).getId(), "张三排", null);
        activeTasks = snakerEngineFacets.getEngine().query().getActiveTasks(new QueryFilter().setOperator("张三排"));
        //Console.log("张三排第二次看待办请假单：{}", activeTasks);
        activeTasks = snakerEngineFacets.getEngine().query().getActiveTasks(new QueryFilter().setOperator("组长"));
        //Console.log("组长第一次看待办请假单：{}", activeTasks);
        //Console.log("组长处理请假单，并设置参数 day = 3, 修改 approveBoss.operator = 老板娘");
        Map map = new HashMap();
        map.put("day", 3);
        map.put("approveBoss.operator", "老板娘");
        snakerEngineFacets.execute(activeTasks.get(0).getId(), "组长", map/*Dict.create()
                .set("day", 3)// #day &gt; 2 ? 'transition5' : 'transition4'
                .set("approveBoss.operator", "老板娘")*/);

        activeTasks = snakerEngineFacets.getEngine().query().getActiveTasks(new QueryFilter().setOperator("老板娘"));
        //Console.log("老板娘第一次看待办请假单：{}", activeTasks);
        //Console.log("老板娘处理请假单");
        snakerEngineFacets.execute(activeTasks.get(0).getId(), "老板娘", null);
        activeTasks = snakerEngineFacets.getEngine().query().getActiveTasks(new QueryFilter().setOperator("老板娘"));
//        //Console.log("老板娘第二次看待办请假单：{}", activeTasks);

    }




    //@Test
    public void startInstanceByname() {
        Process diy = snakerEngineFacets.getEngine().process().getProcessByName("自定义");
        Map map = new HashMap();
        map.put("name", "laker");
        Order order = snakerEngineFacets.startInstanceById(diy.getId(), "", map);
    }

}