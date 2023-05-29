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
@RequestMapping("/simpleRestProcessHandler")
public interface SimpleRestProcessHandler {


	@ApiOperation(value = "流程提交", notes = "流程提交")
	@RequestMapping(value = "/simpleStartProcess", method = RequestMethod.POST)
	public List<TaskDto> simpleStartProcess(@RequestBody CommonProcessRequest commonProcessRequest,HttpServletRequest request) throws Exception;

}
