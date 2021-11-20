package org.camunda.bpm.demo.process;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.engine.task.Task;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/simpleProcessHandler")
public interface SimpleProcessHandler {

	@ApiOperation(value = "流程初始化", notes = "流程初始化")
	@RequestMapping(value = "/simpleInitProcess", method = RequestMethod.POST)
	public List<TaskDto> simpleInitProcess(@RequestBody PscCommonProcessRequest pscCommonProcessRequest) throws Exception;
	
	@ApiOperation(value = "流程提交", notes = "流程提交")
	@RequestMapping(value = "/simpleStartProcess", method = RequestMethod.POST)
	public List<TaskDto> simpleStartProcess(@RequestBody PscCommonProcessRequest pscCommonProcessRequest,HttpServletRequest request) throws Exception;
	
	@ApiOperation(value = "查找历史任务", notes = "根据流程定义Key查找历史任务记录")
	@RequestMapping(value = "/simpleGetHisTasks/{processDefKey}", method = RequestMethod.POST)
	public List<HistoricTaskInstance> simpleGetHisTasks(
			@ApiParam(name = "processDefKey", value = "流程定义Key", required = true) @PathVariable String processDefKey)
			throws Exception;
	
	@ApiOperation(value = "查找运行任务", notes = "根据流程定义Key查找运行任务")
	@RequestMapping(value = "/simpleGetTaskIds/{processDefKey}", method = RequestMethod.POST)
	public List<TaskDto> simpleGetTaskIds(
			@ApiParam(name = "processDefKey", value = "流程定义Key", required = true) @PathVariable String processDefKey)
			throws Exception;

	
	@ApiOperation(value = "流程审批", notes = "流程审批")
	@RequestMapping(value = "/simpleApproveProcess", method =RequestMethod.POST) 
	public List<TaskDto>  simpleApproveProcess(@RequestBody PscCommonTaskRequest pscCommonTaskRequest,HttpServletRequest request) throws Exception;
	
	@ApiOperation(value = "流程撤回", notes = "流程撤回")
	@RequestMapping(value = "/simpleUndoProcess", method =  RequestMethod.POST) 
	public List<TaskDto> simpleUndoProcess(@RequestBody PscCommonTaskRequest pscCommonTaskRequest,HttpServletRequest request) throws Exception;
	
	@ApiOperation(value = "流程驳回", notes = "流程驳回，驳回类型，1：起草节点，2：上一节点，3：目标节点")
	@RequestMapping(value = "/simpleRollbackProcess", method =  RequestMethod.POST) 
	public List<TaskDto> simpleRollbackProcess(@RequestBody PscCommonTaskRequest pscCommonTaskRequest,HttpServletRequest request) throws Exception;
	
	@ApiOperation(value = "流程终止、作废", notes = "流程终止、作废，审批人执行为终止、提交人执行草稿状态的为作废")
	@RequestMapping(value = "/simpleTerminateProcess", method =  RequestMethod.POST) 
	public List<TaskDto> simpleTerminateProcess(@RequestBody PscCommonTaskRequest pscCommonTaskRequest,HttpServletRequest request) throws Exception;
	
	@ApiOperation(value = "流程重启", notes = "流程重启，被审批通过、终止、作废的流程可重新启动到指定节点")
	@RequestMapping(value = "/simpleRestartProcess", method =  RequestMethod.POST) 
	public List<TaskDto> simpleRestartProcess(@RequestBody PscCommonTaskRequest pscCommonTaskRequest,HttpServletRequest request) throws Exception;
	
	@ApiOperation(value = "流程转办", notes = "流程转办，将当期任务转交给其他人处理")
	@RequestMapping(value = "/simpleTurnOverProcess", method =  RequestMethod.POST) 
	public List<TaskDto> simpleTurnOverProcess(@RequestBody PscCommonTaskRequest pscCommonTaskRequest,HttpServletRequest request) throws Exception;
	
	@ApiOperation(value = "流程跳转", notes = "流程跳转，跳转类型,1:往前跳转，2：往回跳转")
	@RequestMapping(value = "/simpleJumpProcess", method =  RequestMethod.POST) 
	public List<TaskDto> simpleJumpProcess(@RequestBody PscCommonTaskRequest pscCommonTaskRequest,HttpServletRequest request) throws Exception;
	
	
	
	/* * 
	 * 
	 * @ApiOperation(value = "流程跟踪", notes =
	 * "获取流程跟踪图Url，根据流程实例Id和当前用户Id获取流程跟踪图Url")
	 * 
	 * @RequestMapping(value =
	 * "/simpleGetProcessDiagramUrl/{processInstId}/{uid}", method =
	 * RequestMethod.GET) public String simpleGetProcessDiagramUrl(
	 * 
	 * @ApiParam(name = "processInstId", value = "流程实例Id", required =
	 * true) @PathVariable String processInstId,
	 * 
	 * @ApiParam(name = "uid", value = "用户Id", required = true) @PathVariable
	 * String uid,HttpServletRequest request) throws Exception;
	 * 
	 * @ApiOperation(value = "流程撤回", notes = "流程撤回,如果该任务允许收回，收回已办任务")
	 * 
	 * @RequestMapping(value = "/simpleUndoProcess", method =
	 * RequestMethod.POST) public TaskInstance simpleUndoProcess(@RequestBody
	 * PscCommonTaskRequest pscCommonTaskRequest ) throws Exception;
	 * 
	 * @ApiOperation(value = "流程驳回", notes = "流程驳回,回退任务到目标节点")
	 * 
	 * @RequestMapping(value = "/simpleRollbackProcess", method =
	 * RequestMethod.POST) public TaskInstance
	 * simpleRollbackProcess(@RequestBody PscCommonTaskRequest
	 * pscCommonTaskRequest ) throws Exception;
	 * 
	 * @ApiOperation(value = "流程转办", notes =
	 * "将待办任务委派给其他人处理，不会产生新任务，该操作更改任务执行人信息")
	 * 
	 * @RequestMapping(value = "/simpleTurnOverProcess", method =
	 * RequestMethod.POST) public boolean simpleTurnOverProcess(@RequestBody
	 * PscCommonTaskRequest pscCommonTaskRequest ) throws Exception;
	 * 
	 * @ApiOperation(value = "流程传阅", notes = "流程传阅，创建传阅任务实例")
	 * 
	 * @RequestMapping(value = "/simpleUserCCTask", method = RequestMethod.POST)
	 * public TaskInstance simpleUserCCTask(@RequestBody PscCommonTaskRequest
	 * pscCommonTaskRequest ) throws Exception;
	 * 
	 * @ApiOperation(value = "流程查阅", notes = "流程查阅，如果该任务尚未阅读，标记任务读取时间")
	 * 
	 * @RequestMapping(value = "/simpleReadProcess", method =
	 * RequestMethod.POST) public void simpleReadProcess(@RequestBody
	 * PscCommonTaskRequest pscCommonTaskRequest ) throws Exception;
	 * 
	 * @ApiOperation(value = "流程复活", notes = "流程复活，通过id流程复活，重新激活已结束的流程实例")
	 * 
	 * @RequestMapping(value = "/simpleReactivateProcess", method =
	 * RequestMethod.POST) public TaskInstance
	 * simpleReactivateProcess(@RequestBody PscCommonTaskRequest
	 * pscCommonTaskRequest ) throws Exception;
	 * 
	 * @ApiOperation(value = "流程重启", notes =
	 * "流程复活，通过id重置流程到第一个节点，将任务创建给流程启动者，等同于由启动者撤销重办业务（适用于开始事件后是UserTask的人工流程）。 撤销操作将删除令牌、 所有待办已办任务等流程数据， 如果给定了reStartReason值 ，将撤销原因初始化成审批记录。如果该流程启动了子流程一并将子流程删除"
	 * )
	 * 
	 * @RequestMapping(value = "/simpleRestartProcess", method =
	 * RequestMethod.POST) public TaskInstance simpleRestartProcess(@RequestBody
	 * PscCommonTaskRequest pscCommonTaskRequest ) throws Exception;
	 * 
	 * @ApiOperation(value = "流程终止", notes = "流程终止，通过id终止一个流程实例")
	 * 
	 * @RequestMapping(value = "/simpleTerminateProcess", method =
	 * RequestMethod.POST) public boolean simpleTerminateProcess(@RequestBody
	 * PscCommonTaskRequest pscCommonTaskRequest ) throws Exception;
	 * 
	 * @ApiOperation(value = "流程删除", notes =
	 * "流程删除，通过id删除流程实例，与流程实例(及子流程实例嵌套)相关的业务数据、控制数据、变量等全部删除")
	 * 
	 * @RequestMapping(value = "/simpleTerminateProcess", method =
	 * RequestMethod.POST) public boolean simpleDeleteProcess(@RequestBody
	 * PscCommonTaskRequest pscCommonTaskRequest ) throws Exception;
	 */
}
