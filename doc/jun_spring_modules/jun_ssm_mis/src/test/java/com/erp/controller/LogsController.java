package com.erp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//import org.apache.struts2.convention.annotation.Action;
//import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.model.Log;
import com.erp.service.ILogsService;
import com.erp.viewModel.GridModel;
//import com.opensymphony.xwork2.ModelDriven;
//@Namespace("/logs")
//@Action(value = "logsAction")
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

@Controller
@RequestMapping("/logsController.do")
public class LogsController extends BaseController {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 3658084064057123814L;
	private Log log;
	private ILogsService logsService;

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	@Autowired
	public void setLogsService(ILogsService logsService) {
		this.logsService = logsService;
	}

	/**
	 * 函数功能说明 TODO:查询所有日志 Administrator修改者名字 2013-6-18修改日期 修改内容
	 * 
	 * @Title: findLogsAllList
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(params = "method=findLogsAllList")
	public String findLogsAllList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}

		GridModel gridModel = new GridModel();
		gridModel.setRows(logsService.findLogsAllList(map, pageUtil));
		gridModel.setTotal(logsService.getCount(map, pageUtil));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:持久化日志弹窗 Administrator修改者名字 2013-6-18修改日期 修改内容
	 * 
	 * @Title: persistenceLogs
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(params = "method=persistenceLogs")
	public String persistenceLogs(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log log = new Log();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.MapToBean(param, log);
		OutputJson(getMessage(logsService.persistenceLogs(log)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:删除日志 Administrator修改者名字 2013-6-18修改日期 修改内容
	 * 
	 * @Title: delLogs
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=delLogs")
	public String delLogs(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log log = new Log();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.MapToBean(param, log);
		OutputJson(getMessage(logsService.delLogs(log.getLogId())), response);
		return null;
	}

	// public Log getModel()
	// {
	// if (null==log)
	// {
	// log=new Log();
	// }
	// return log;
	// }
}
