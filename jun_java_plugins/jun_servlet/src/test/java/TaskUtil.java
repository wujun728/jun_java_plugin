

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jun.web.utils.task.DoSomething;
import com.jun.web.utils.task.TaskEntity;
import com.jun.web.utils.task.TaskPoolManager;

public class TaskUtil {

	public static void addTaskList(List<TaskEntity> taskList) {
		TaskPoolManager.newInstance().addTasks(taskList);
	}

	public static void addTask(TaskEntity task) {
		TaskPoolManager.newInstance().addTask(task);
	}

	public static void methodRun(Class clazz, String method, Map param) {
		List<TaskEntity> taskList = new ArrayList<TaskEntity>();
		TaskEntity task = null;
		Map<String, String> taskParam = null;
		for (int i = 0; i < 1; i++) {
			task = new TaskEntity();
			task.setTaskClass(clazz);
			task.setTaskMethod(method);
			taskParam = param;
			task.setTaskParam(taskParam);
			taskList.add(task);
		}
		TaskUtil.addTaskList(taskList);
	}

	@Test
	public void dosomething() {
		Map map = new HashMap();
		map.put("key1", " value1 ");

		Map taskParam = new HashMap();
		taskParam.put("name", "abc-->");
		taskParam.put("content", " cccc  ");
		taskParam.put("map", map);
		methodRun(DoSomething.class, "testMethod", taskParam);
	}

	@Test
	public void BizService() {
		Map map = new HashMap();
//		methodRun(com.jun.plugin.dbutils.BizService.class, "queryAll", null);
	}

}
