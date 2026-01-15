package io.github.wujun728.snakerflow;

import static org.snaker.engine.access.QueryFilter.DESC;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

//import io.github.wujun728.bizservice.service.BizCommonService;
import io.github.wujun728.snakerflow.ext.mapper.ExtLogMapper;
import io.github.wujun728.snakerflow.module.PageResponse;
import io.github.wujun728.snakerflow.module.Response;
import io.github.wujun728.snakerflow.process.SnakerEngineFacets;
import io.github.wujun728.snakerflow.process.SnakerHelper;
import org.apache.commons.lang.StringUtils;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.snaker.engine.entity.WorkItem;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import io.github.wujun728.system.entity.SysUser;
//import io.github.wujun728.system.mapper.SysUserMapper;
//import io.github.wujun728.system.service.HttpSessionService;
//import com.laker.admin.framework.aop.Metrics;
//import com.laker.admin.module.flow.SnakerflowFacetsController;
//import com.laker.admin.module.sys.entity.SysUser;
//import com.laker.admin.module.sys.service.ISysUserService;
//import io.github.wujun728.system.service.UserService;

//import cn.dev33.satoken.annotation.SaCheckPermission;
//import cn.dev33.satoken.stp.StpUtil;
//import cn.dev33.satoken.annotation.SaCheckPermission;
//import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
//import com.laker.admin.framework.aop.Metrics;
//import com.laker.admin.framework.model.PageResponse;
//import com.laker.admin.framework.model.Response;
//import com.laker.admin.module.flow.process.SnakerEngineFacets;
//import com.laker.admin.module.flow.process.SnakerHelper;
//import com.laker.admin.module.sys.entity.SysUser;
//import com.laker.admin.module.sys.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/flow")
@Slf4j
public class SnakerflowFacetsController {

//	@Resource
//	HttpSessionService sessionService;

	@Autowired
	private SnakerEngineFacets snakerEngineFacets;

//	@Resource
//	private UserService sysUserService;

//	@Autowired
//	private SysUserMapper sysuer;

	@Autowired
	private ExtLogMapper bizCommonMapper;
//	@Autowired
//	private BizCommonService bizCommonService;

	/**
	 * ---------------------------------------------流程定义--------------------------------------------
	 */
	/**
	 * 根据流程文件名称，在线部署流程 http://qixing.vip321.vip/flow/process/deploy/leave.snaker
	 * http://localhost:8081/flow/process/deploy/leave.snaker
	 * 
	 * @return
	 */
	@ApiOperation(value = "根据流程文件名称，在线部署流程", tags = "流程引擎-流程部署")
	@RequestMapping(value = "/process/deploy/{filename}", method = RequestMethod.GET)
	// @Metrics
	public Response processdeploy(@PathVariable("filename") String filename) {
		snakerEngineFacets.initFlowsByName(filename);
		return Response.ok();
	}

	/**
	 * 获取流程定义，获取流程定义的XML，根据流程ID
	 */
	@GetMapping("/getXml")
	public Response processEdit(String id) {
		Process process = snakerEngineFacets.getEngine().process().getProcessById(id);
		if (process.getDBContent() != null) {
			try {
				return Response.ok(new String(process.getDBContent(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return Response.error("500", "xml异常");
	}

	/**
	 * 获取流程定义JOSN，根据流程定义的名称
	 * 
	 * @param processId
	 * @return
	 */
	@GetMapping(value = "/process/modelJson"/*, produces = "application/json;charset=UTF-8"*/)
//	@GetMapping(value = "/process/modelJson", produces = "text/plain;charset=UTF-8")
	@ApiOperation(value = "根据流程定义名称获取流程定义json", tags = "流程引擎-流程")
	// @Metrics
	public void getProcess(@RequestParam(required = false) String processId) throws IOException {
		HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
		assert response != null;
		String json = "";
		if (StrUtil.isBlank(processId)) {
			json =  "";
		}
		Process process = snakerEngineFacets.getEngine().process().getProcessById(processId);
		AssertHelper.notNull(process);
		ProcessModel processModel = process.getModel();
		if (processModel != null) {
			json = SnakerHelper.getModelJson(processModel);

//			return json;
		}
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(json);
//		return null;
	}

	/**
	 * 流程定义清单，查询列表
	 */
	@ApiOperation(value = "根据给定的参数列表args分页查询process", tags = "流程引擎-流程")
	@RequestMapping(value = "/process/list", method = RequestMethod.GET)
	public Response processList(Page<Process> page, String displayName, String limit) {
		QueryFilter filter = new QueryFilter();
		if (StringHelper.isNotEmpty(displayName)) {
			filter.setDisplayName(displayName);
		}
		if (StringHelper.isNotEmpty(limit)) {
			page.setPageSize(Integer.valueOf(limit));
		}
		filter.orderBy("create_Time").order(DESC);
		snakerEngineFacets.getEngine().process().getProcesss(page, filter);
		return PageResponse.ok(JSONUtil.parse(page.getResult()), page.getTotalCount());
	}

	/**
	 * 根据流程定义ID，删除流程定义
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据流程定义ID，删除流程定义", tags = "流程引擎-流程")
	@RequestMapping(value = "/process/delete/{id}", method = RequestMethod.GET)
	// @Metrics
	// @SaCheckPermission("flow.delete")
	public Response processDelete(@PathVariable("id") String id) {
		snakerEngineFacets.getEngine().process().undeploy(id);
		return Response.ok();
	}

	/**
	 * 保存流程定义[web流程设计器]
	 * 
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "保存流程定义[web流程设计器]", tags = "流程引擎-流程")
	@RequestMapping(value = "/process/deployXml", method = RequestMethod.POST)
	// @SaCheckPermission("flow.update")
	public boolean processDeploy(String model, String id,
			@RequestParam(required = false, defaultValue = "false") boolean xmlHearder) {
		InputStream input = null;
		try {
			String xml = "";
			if (!xmlHearder) {
				xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";
			}
			xml = xml + SnakerHelper.convertXml(model);
			System.out.println("model xml=\n" + xml);
			input = StreamHelper.getStreamFromString(xml);
			if (StringUtils.isNotEmpty(id)) {
				snakerEngineFacets.getEngine().process().redeploy(id, input);
			} else {
				snakerEngineFacets.getEngine().process().deploy(input);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * @param processId
	 * @param orderId
	 * @return
	 */
	@ApiOperation(value = "流程定义+流程状态", tags = "流程引擎-流程")
	@RequestMapping(value = "/process/json", method = RequestMethod.GET)
	// @Metrics
	public Object json(String processId, String orderId) {
		if (StrUtil.isBlank(processId)) {
			processId = snakerEngineFacets.getEngine().query().getHistOrder(orderId).getProcessId();
		}
		Process process = snakerEngineFacets.getEngine().process().getProcessById(processId);
		AssertHelper.notNull(process);
		ProcessModel model = process.getModel();
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (model != null) {
			jsonMap.put("process", SnakerHelper.getModelJson(model));
		}

		if (StringUtils.isNotEmpty(orderId)) {
			List<Task> tasks = snakerEngineFacets.getEngine().query()
					.getActiveTasks(new QueryFilter().setOrderId(orderId));
			List<HistoryTask> historyTasks = snakerEngineFacets.getEngine().query()
					.getHistoryTasks(new QueryFilter().setOrderId(orderId));
			jsonMap.put("state", SnakerHelper.getStateJson(model, tasks, historyTasks));
		}
		log.info(jsonMap.get("state"));
		// {"historyRects":{"rects":[{"paths":["TO 任务1"],"name":"开始"},{"paths":["TO
		// 分支"],"name":"任务1"},{"paths":["TO 任务3","TO 任务4","TO 任务2"],"name":"分支"}]}}
		return jsonMap;
	}

	/**
	 * --------------------------------------------- 任务相关
	 * ---------------------------------------------
	 */
	/**
	 * 根据当前用户查询待办任务列表
	 */
	@GetMapping("/task/todoList")
	@ApiOperation(value = "根据当前用户查询待办任务列表", tags = "流程引擎-任务")
	public PageResponse userTaskTodoList(String username) {
		if (org.springframework.util.StringUtils.isEmpty(username)) {
			username = "sessionService.getCurrentUsername()";
		}
		String usercode = "sessionService.getCurrentUsername()";
		Page<WorkItem> page = new Page<>(30);
		snakerEngineFacets.getEngine().query().getWorkItems(page, new QueryFilter().setOperator(username));
		return PageResponse.ok(page.getResult(), page.getTotalCount());
	}

	/**
	 * 根据当前用户查询待办任务列表
	 */
	@GetMapping("/task/doneList")
	@ApiOperation(value = "根据当前用户查询已办任务列表", tags = "流程引擎-任务")
	public PageResponse userTaskdoneList() {
		Page<WorkItem> page = new Page<>(30);
		snakerEngineFacets.getEngine().query().getHistoryWorkItems(page,
				new QueryFilter().setOperator("sessionService.getCurrentUsername()"));
		List<WorkItem> items = page.getResult();
		return PageResponse.ok(page.getResult(), page.getTotalCount());
	}

	@GetMapping("/task/actor/add")
	@ApiOperation(value = "根据流程实例id和任务名称，增加任务参与者", tags = "流程引擎-任务")
	public Response addTaskActor(@RequestParam("orderId") String orderId, @RequestParam("taskName") String taskName,
			@RequestParam("operator") String operator) {
		List<Task> tasks = snakerEngineFacets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
		for (Task task : tasks) {
			if (task.getTaskName().equalsIgnoreCase(taskName) && StringUtils.isNotEmpty(operator)) {
				snakerEngineFacets.getEngine().task().addTaskActor(task.getId(), operator);
			}
		}
		return Response.ok();
	}

	@GetMapping("/task/tip")
	@ApiOperation(value = "根据流程实例id和任务名称,查找当前任务的到达时间和待执行人", tags = "流程引擎-任务")
	public Response taskTip(String orderId, String taskName) {
		List<Task> tasks = snakerEngineFacets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
		StringBuilder builder = new StringBuilder();
		String createTime = "";
		String finishTime = "";
		String taskOperatorFlag = "";
		String taskOperatorMsg = "";
		boolean find = false;
		for (Task task : tasks) {
			if (task.getTaskName().equalsIgnoreCase(taskName)) {
				String[] actors = snakerEngineFacets.getEngine().query().getTaskActorsByTaskId(task.getId());
				String.join(",",actors);
				for (String actor : actors) {
//					SysUser sysUser = sysuer.getUserByName(actor);
//					if (sysUser!=null) {
//						String name =  sysUser.getRealName();
//						if (!builder.toString().contains(name)) {
//							builder.append(name).append(",");
//						}
//					}else{
//						//builder.append("用户【"+actor+"】不存在 ").append(",");
//
//					}
					find = true;
				}
				createTime = task.getCreateTime();
				taskOperatorFlag = String.valueOf(task.getVariableMap().get("taskOperatorFlag"));
				taskOperatorMsg = String.valueOf(task.getVariableMap().get("taskOperatorMsg"));
			}
		}
		if (!find) {
			List<HistoryTask> historyTasks = snakerEngineFacets.getEngine().query()
					.getHistoryTasks(new QueryFilter().setOrderId(orderId));
			for (HistoryTask task : historyTasks) {
				if (task.getTaskName().equalsIgnoreCase(taskName)) {
					String[] actors = snakerEngineFacets.getEngine().query().getHistoryTaskActorsByTaskId(task.getId());
					for (String actor : actors) {
//						// SysUser sysUser = sysUserService.getById(String.valueOf(actor));
//						SysUser sysUser = sysuer.getUserByName(actor);
//						if (sysUser != null) {
//							String nickName = sysUser.getRealName();
//							if (!builder.toString().contains(nickName)) {
//								builder.append(nickName).append(",");
//							}
//						}
					}
					createTime = task.getCreateTime();
					finishTime = task.getFinishTime();
					taskOperatorFlag = String.valueOf(task.getVariableMap().get("taskOperatorFlag"));
					taskOperatorMsg = String.valueOf(task.getVariableMap().get("taskOperatorMsg"));
				}
			}
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		Map<String, String> data = new HashMap<String, String>();
		data.put("actors", builder.toString());
		data.put("createTime", createTime);
		data.put("finishTime", finishTime);
		data.put("taskOperatorFlag", taskOperatorFlag);
		data.put("taskOperatorMsg", taskOperatorMsg);
		return Response.ok(data);
	}

	/**
	 * 活动任务的驳回
	 */
	@GetMapping("/task/reject")
	@ApiOperation(value = "\t 【审批任务】驳回，根据任务主键ID，操作人ID，参数列表执行任务，并且根据nodeName跳转到任意节点\n"
			+ "\t 1、nodeName为null时，则跳转至上一步处理\n" + "\t 2、nodeName不为null时，则任意跳转，即动态创建转移", tags = "流程引擎-任务")
	public Response activeTaskReject(String taskId, String nodeName, String reason) {
		Dict rejectReason = Dict.create()
				// 拒绝原因，建议单独搞个 审核表 审核的comment file单独存储
				.set("rejectReason", reason);
		Map args = new HashMap(8);
		args.put("taskOperator", "sessionService.getCurrentUsername()");
		args.put("taskOperatorMsg", "驳回原因，填写有问题，不能XXX，需要XXX！");
		args.put("taskOperatorFlag", "处理结果：驳回");
		snakerEngineFacets.executeAndJump(taskId, "sessionService.getCurrentUsername()", args, nodeName);
		return Response.ok();
	}

	/**
	 * 活动任务的驳回-驳回到发起人
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/task/rejectToCreate")
	@ApiOperation(value = "【任务驳回】驳回到发起人", tags = "流程引擎-任务")
	public Response activeTaskReject(String taskId, String taskOperatorFlag, String taskOperatorMsg,
			String operatorNextid, String operatorNext) {
		List<WorkItem> workItems = snakerEngineFacets.getEngine().query().getWorkItems(null,
				new QueryFilter().setTaskId(taskId));
		if (CollUtil.isEmpty(workItems)) {
			Response.error("500", "不存在任务喽");
		}
		WorkItem workItem = workItems.get(0);
		Process process = snakerEngineFacets.getEngine().process().getProcessById(workItem.getProcessId());
		ProcessModel model = process.getModel();
		// 获取开始节点下面的第一个节点
		String name = model.getStart().getOutputs().get(0).getTarget().getName();
		Map args = new HashMap(8);
		args.put("taskOperator", "sessionService.getCurrentUsername()");
		args.put("taskOperatorName", operatorNext);
		args.put("taskOperatorMsg", taskOperatorMsg);
		args.put("taskOperatorFlag", taskOperatorFlag);
		snakerEngineFacets.executeAndJump(taskId, "sessionService.getCurrentUsername()", args, name);
		return Response.ok();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/task/approval", method = RequestMethod.GET)
	@ApiOperation(value = "【审批任务】同意", tags = "流程引擎-任务")
	public Response doApproval(String taskId, String flag, String taskOperatorFlag, String taskOperatorMsg,
			String operatorNextid, String operatorNext, String processName, String processId) {
		Task task1 = snakerEngineFacets.getEngine().query().getTask(taskId);// 查询当前任务节点信息
		String orderId = task1.getOrderId();
		String taskName = task1.getTaskName();
		String processNameEn = String.valueOf(task1.getVariableMap().get("processName"));// 获取流程名称
		String businessId = String.valueOf(task1.getVariableMap().get("businessId"));// 获取流程名称
		Process process = snakerEngineFacets.getEngine().process().getProcessByName(processNameEn);// 获取流程实例信息
		List<TaskModel> tm = process.getModel().getNode(task1.getTaskName()).getNextModels(TaskModel.class);// 获取流程下一环节节点信息
		if (!"0".equals(flag) && tm.size() > 0 && operatorNextid != null && operatorNextid.length() < 1) {// flag:0|1，判断节点的分支属性；下一环节处理人
			return Response.error("1010", "流程审批人不能为空(驳回及非最终环节)！");
		}
		if (tm == null || tm.size() == 0) {
			log.info("流程结束！" + processNameEn, "orderId=" + orderId);
			//@TODO wujun  改定时任务处理
			String errMsg = "";
			//bizCommonService.doAfterFlowFinish(processNameEn, orderId, businessId, taskName);
			if (errMsg.length() > 0) {
				Response.error("2022", errMsg);
			}
		}
		Map args = new HashMap(8);
		args.put("taskOperator", "sessionService.getCurrentUsername()");
		args.put("taskOperatorName", operatorNext);
		args.put("taskOperatorMsg", taskOperatorMsg);
		args.put("taskOperatorFlag", taskOperatorFlag);
		try {
			args.put("flag", Integer.valueOf(flag));
		} catch (NumberFormatException e) {
			log.error("flag Integer.valueOf(flag) NumberFormatException 暂不处理 ");
			e.printStackTrace();
		}
		List<Task> tasks = snakerEngineFacets.execute(taskId, "sessionService.getCurrentUsername()", args);
		for (Task task : tasks) {
			snakerEngineFacets.getEngine().task().addTaskActor(task.getId(), operatorNextid.split(","));
		}
		return Response.ok();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/task/submit", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "【提交任务】", tags = "流程引擎-任务")
	public Response doSubmitTask(@RequestBody Map param) {
		String processName = MapUtil.getStr(param, "processName");
		String taskNewName = MapUtil.getStr(param, "taskNewName");
		String taskOperatorMsg = MapUtil.getStr(param, "taskOperatorMsg");
		String operatorNextid = MapUtil.getStr(param, "operatorNextid");
		String operatorNext = MapUtil.getStr(param, "operatorNext");
		String taskOperatorFlag = MapUtil.getStr(param, "taskOperatorFlag");
		String businessId = MapUtil.getStr(param, "businessId");
//		SysUser user = sysUserService.getById(sessionService.getCurrentUserId());
		// <!-- 流程flow-mark001,不同流程，环节节点、处理人、表结构，业务ID等，都不一样 -->
		String orderName = taskNewName;
		List<Map<String, String>> list = WorkflowsConfig.getList();
		for (Map<String, String> item : list) {
			if (processName.equalsIgnoreCase(item.get("processName"))) {
//    			String step1 = item.get("step1");
//    			String step2 = item.get("step2");
				String tname = item.get("tname");
//    			String process = item.get("process");
				String taskName = item.get("taskName");
				if (orderName == null || orderName.length() < 1) {
					//@TODO wujun
//					orderName = user.getRealName() + taskName /* + PrimaryKeyService.getOrderIdPrefix(new Date())  */;
				}
				Map args = new HashMap(8);
				args.put("taskOperator", "sessionService.getCurrentUsername()");
				args.put("step1", "sessionService.getCurrentUsername()");
				args.put("step2", operatorNextid.split(","));
				args.put("processName", processName);
				args.put("tname", tname);
				args.put("businessId", businessId);
				args.put("taskOperatorMsg", taskOperatorMsg);
				args.put("taskOperatorFlag", taskOperatorFlag);
				args.put(SnakerEngine.ID, orderName);
				Order order = snakerEngineFacets.startAndExecute(processName, null, "sessionService.getCurrentUsername()",
						args);
				if (businessId.contains(",")) {
					for (String id : businessId.split(",")) {
						bizCommonMapper.updateRecordByColumnValue(tname, "order_id", order.getId(), id);
					}
				} else {
					bizCommonMapper.updateRecordByColumnValue(tname, "order_id", order.getId(), businessId);
				}
				return Response.ok();
			}
		}
		return Response.error("2010", "流程配置有问题！");
	}

	/**
	 * 历史任务撤回，这玩意只能撤回刚发出的且没有被处理的
	 *
	 * @param taskId
	 * @return
	 */
	@GetMapping("/task/undo")
	@ApiOperation(value = "根据任务主键id、操作人撤回任务", tags = "流程引擎-任务")
	public Response historyTaskUndo(String taskId) {
		snakerEngineFacets.getEngine().task().withdrawTask(taskId, "sessionService.getCurrentUsername()");
		return Response.ok();
	}

	@GetMapping("/task/transferMajor")
	@ApiOperation(value = "转办", tags = "流程引擎-任务")
	public Response transferMajor(String taskId, String nextOperator) {
		snakerEngineFacets.transferMajor(taskId, "sessionService.getCurrentUsername()", nextOperator.split(","));
		return Response.ok();
	}

	/**
	 * ------------------ 流程
	 */
	/**
	 * 流程实例管理
	 */
	@ApiOperation(value = "流程分页查询", tags = "流程引擎-流程实例")
	@RequestMapping(value = "/order/list", method = RequestMethod.GET)
	public Response orderList(Page<HistoryOrder> page, String displayName) {
		QueryFilter filter = new QueryFilter();
		if (StringHelper.isNotEmpty(displayName)) {
			filter.setDisplayName(displayName);
		}
		filter.orderBy("create_Time").order(DESC);
		snakerEngineFacets.getEngine().query().getHistoryOrders(page, filter);
		return PageResponse.ok(JSONUtil.parse(page.getResult()), page.getTotalCount());
	}
}