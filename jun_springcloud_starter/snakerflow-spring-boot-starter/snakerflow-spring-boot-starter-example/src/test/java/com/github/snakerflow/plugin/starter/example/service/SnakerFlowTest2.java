package com.github.snakerflow.plugin.starter.example.service;

import java.io.InputStream;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SnakerFlowTest2 {

	@Autowired
	SnakerEngineFacets snakerEngineFacets;

	@Autowired
	private SnakerEngine engine;

//	@Test
	public void testname() throws Exception {
    	initFlowsByPath();
//    	getAllProcess();
//    	getEngine();
////    	getProcessByOrderId2();
//		startInstanceById();  //
//		testStartAndExecute();
//		execute("apply.operator");
		execute("approveDept.operator");
//		executeAndJumpBackStep();
//		getTasks("apply.operator");
	}

	public void initFlowsByPath() {
		String path = "flows/leave.snaker";
//        String flows = snakerEngineFacets.initFlowsByPath(path);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream(path);
		String deploy = engine.process().deploy(stream);
		log.info("流程初始化成功！流程ID：" + deploy); // e6fbbed640184b1c8682dcab3379ec43
		Assert.assertNotNull(deploy);
	}

	public void getAllProcess() {
		QueryFilter filter = new QueryFilter();
		filter.setName("leave");
		List<Process> allProcess = engine.process().getProcesss(filter);
		log.info("查询定义的流程信息：" + JSON.toJSON(allProcess));
		Assert.assertNotNull(allProcess);
	}

	public void getEngine() {
		SnakerEngine engine = snakerEngineFacets.getEngine();
		log.info("engine信息：" + JSON.toJSON(engine));
		Assert.assertNotNull(engine);
	}

	public void getAllProcessNames() {
		List<String> allProcessNames = snakerEngineFacets.getAllProcessNames();
		Assert.assertNotNull(allProcessNames);
	}

	public void getProcessByOrderId2() {
		Order dm = snakerEngineFacets.startInstanceById("d1913b954e0c42e69c08a15175522853", "wujun", null);
//    	Order dm = snakerEngineFacets.startInstanceById("0389fe5362904ff69a059dac2443ed32", "apply.operator", null);
		Assert.assertNotNull(dm);
		List<Process> processByOrderId = snakerEngineFacets.getProcessByOrderId(dm.getId());
		Assert.assertNotNull(processByOrderId);
	}

	public void getProcessByOrderId() {
//		snakerEngineFacets.initFlows();
//		List<Process> allProcess = snakerEngineFacets.getAllProcess();
//		if (!CollectionUtils.isEmpty(allProcess)) {
//			Process process = allProcess.get(0);
//			Order dm = snakerEngineFacets.startInstanceById(process.getId(), "apply.operator", null);
//			Assert.assertNotNull(dm);
			List<Process> processByOrderId = snakerEngineFacets.getProcessByOrderId("3b10df17e75e4f1bb3b43763aa0771ef");
			Assert.assertNotNull(processByOrderId);
//		}
	}

	public void startInstanceById() {
		Map m = new HashMap<>();
		m.put("day", 123);
		Order dm = snakerEngineFacets.startInstanceById("d1913b954e0c42e69c08a15175522853", "wujun", m);
		Assert.assertNotNull(dm);
//        }
	}


	public void testStartAndExecute() {
//		snakerEngineFacets.initFlows();
//		List<Process> allProcess = snakerEngineFacets.getAllProcess();
//		if (!CollectionUtils.isEmpty(allProcess)) {
//			Process process = allProcess.get(0);
//			Order dm = snakerEngineFacets.startAndExecute("d1913b954e0c42e69c08a15175522853", "junjun", null);
		Map m = new HashMap<>();
		m.put("day", 123);

//		Order order = engine.startInstanceById("d1913b954e0c42e69c08a15175522853", "junjun", null);
		List<Task> tasks = engine.query().getActiveTasks(new QueryFilter().setOrderId("3b10df17e75e4f1bb3b43763aa0771ef"));
//		List<Task> tasks2 = engine.query().getActiveTasks(new QueryFilter().setOperator("junjun"));
		if (tasks != null && tasks.size() > 0) {
			Task task = tasks.get(0);
			engine.executeTask(task.getId(),"approveDept.operator", m);
		}
		
//		}
	}

	public void startAndExecute() {
		snakerEngineFacets.initFlows();
		List<Process> allProcess = snakerEngineFacets.getAllProcess();
		if (!CollectionUtils.isEmpty(allProcess)) {
			Process process = allProcess.get(0);
			Order dm = snakerEngineFacets.startAndExecute(process.getName(), 0, "apply.operator", null);
			Assert.assertNotNull(dm);
		}
	}

	public void execute(String actor) {
//		snakerEngineFacets.initFlows();
//		List<Process> allProcess = snakerEngineFacets.getAllProcess();
//		if (!CollectionUtils.isEmpty(allProcess)) {
//			Process process = allProcess.get(0);
//			Order dm = snakerEngineFacets.startAndExecute("d1913b954e0c42e69c08a15175522853", "apply.operator", null);
//			List<Task> tasks = snakerEngineFacets.getTasks("3b10df17e75e4f1bb3b43763aa0771ef");
		Map m = new HashMap<>();
		m.put("day", 123);
			List<Task> tasks = engine.query().getActiveTasks(new QueryFilter().setOrderId("3b10df17e75e4f1bb3b43763aa0771ef"));
			if (!CollectionUtils.isEmpty(tasks)) {
				List<Task> taskList = snakerEngineFacets.execute(tasks.get(0).getId(), actor, null);
				Assert.assertNotNull(taskList);
			}
//		}
	}

	public void getTasks(String actor) {
//		snakerEngineFacets.initFlows();
		List<Process> allProcess = snakerEngineFacets.getAllProcess();
//		if (!CollectionUtils.isEmpty(allProcess)) {
//			Process process = allProcess.get(0);
		Map m = new HashMap<>();
		m.put("day", 123);
//			Order dm = snakerEngineFacets.startAndExecute("d1913b954e0c42e69c08a15175522853", actor, m);
			List<Task> tasks = snakerEngineFacets.getTasks("3b10df17e75e4f1bb3b43763aa0771ef");
			System.out.println("tasks" + JSON.toJSON(tasks));
			Assert.assertNotNull(tasks);
//		}
	}

	@Test
	public void executeAndJump() {
//		snakerEngineFacets.initFlows();
		List<Process> allProcess = snakerEngineFacets.getAllProcess();
		if (!CollectionUtils.isEmpty(allProcess)) {
			Process process = allProcess.get(0);
			Order dm = snakerEngineFacets.startAndExecute(process.getId(), "apply.operator", null);
			List<Task> tasks = snakerEngineFacets.getTasks(dm.getId());
			List<Task> taskList = snakerEngineFacets.executeAndJump(tasks.get(0).getId(), "approveDept.operator", null,
					"end1");
			System.out.println("tasks" + JSON.toJSON(tasks));
			Assert.assertNotNull(tasks);
		}
	}
	public void executeAndJumpBackStep() {
//		snakerEngineFacets.initFlows();
//		List<Process> allProcess = snakerEngineFacets.getAllProcess();
//		if (!CollectionUtils.isEmpty(allProcess)) {
//			Process process = allProcess.get(0);
//			Order dm = snakerEngineFacets.startAndExecute(process.getId(), "apply.operator", null);
			List<Task> tasks = snakerEngineFacets.getTasks("orderID");
			List<Task> taskList = snakerEngineFacets.executeAndJump(tasks.get(0).getId(), null, null,
					null);
			System.out.println("tasks" + JSON.toJSON(tasks));
//			Assert.assertNotNull(tasks);
//		}
	}
	

}
