package com.opensource.nredis.proxy.monitor.api;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opensource.nredis.proxy.monitor.date.utils.DateBase;
import com.opensource.nredis.proxy.monitor.enums.FinalStatusEnums;
import com.opensource.nredis.proxy.monitor.enums.MonitorMoudleEnums;
import com.opensource.nredis.proxy.monitor.model.SystemApplicationMonitor;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.platform.DataGridObject;
import com.opensource.nredis.proxy.monitor.platform.ResponseObject;
import com.opensource.nredis.proxy.monitor.platform.RestStatus;
import com.opensource.nredis.proxy.monitor.service.ISystemApplicationMonitorService;
import com.opensource.nredis.proxy.monitor.string.utils.StringUtil;
/**
* controller
*
* @author liubing
* @date 2016/12/31 08:09
* @version v1.0.0
*/
@Controller
public class SystemApplicationMonitorController {
	
	@Autowired
	private ISystemApplicationMonitorService systemApplicationMonitorService;
	
	/**
	 * 新增
	 * @param systemApplicationMonitor
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/systemApplicationMonitor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject systemApplicationMonitor(SystemApplicationMonitor systemApplicationMonitor) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		systemApplicationMonitorService.create(systemApplicationMonitor);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 修改
	 * @param pageElement
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editSystemApplicationMonitor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editSystemApplicationMonitor(SystemApplicationMonitor systemApplicationMonitor) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		systemApplicationMonitorService.modifyEntityById(systemApplicationMonitor);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/searchSystemApplicationMonitor", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchPageElement(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		SystemApplicationMonitor systemApplicationMonitor= systemApplicationMonitorService.getEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		responseObject.setData(systemApplicationMonitor);
		return responseObject;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeSystemApplicationMonitor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject removeSystemApplicationMonitor(@Validated Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		systemApplicationMonitorService.deleteEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 查询列表
	 * @param menuId
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchSystemApplicationMonitors", method = RequestMethod.GET)
	@ResponseBody
	public DataGridObject searchSystemApplicationMonitors(SystemApplicationMonitor systemApplicationMonitor,Integer page , Integer rows) throws Exception{
		DataGridObject responseObject=new DataGridObject();
		PageList<SystemApplicationMonitor> pageList=null;
		if(page==null||page==0){
			page=1;
		}
		if(StringUtil.isNotEmpty(systemApplicationMonitor.getMonitorApplicationName())){
			String applicationName=new String(systemApplicationMonitor.getMonitorApplicationName().getBytes("ISO-8859-1"), "UTF-8");
			systemApplicationMonitor.setMonitorApplicationName(applicationName);
		}
		
		PageAttribute pageAttribute=new PageAttribute(page, rows);
		pageList=systemApplicationMonitorService.queryEntityPageList(pageAttribute, systemApplicationMonitor, null);
		List<SystemApplicationMonitor> systemApplicationMonitors=pageList.getDatas();
		for(SystemApplicationMonitor applicationMonitor:systemApplicationMonitors){
			applicationMonitor.setCreateTimeString(DateBase.formatDate(applicationMonitor.getCreateTime(), DateBase.DATE_PATTERN_DATETIME));
			applicationMonitor.setMonitorMoudleString(MonitorMoudleEnums.getMessage(applicationMonitor.getMonitorMoudle()));
			applicationMonitor.setMonitorStatusString(FinalStatusEnums.getMessage(applicationMonitor.getMonitorStatus()));
		}
		responseObject.setTotal(pageList.getPage().getRowCount());
		responseObject.setRows(pageList.getDatas());
		return responseObject;
	}
	
}
