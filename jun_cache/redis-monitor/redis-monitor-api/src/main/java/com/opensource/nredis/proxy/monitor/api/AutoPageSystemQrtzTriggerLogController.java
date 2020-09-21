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
import com.opensource.nredis.proxy.monitor.enums.JobClassEnums;
import com.opensource.nredis.proxy.monitor.model.AutoPageSystemQrtzTriggerLog;
import com.opensource.nredis.proxy.monitor.model.PsDict;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.platform.DataGridObject;
import com.opensource.nredis.proxy.monitor.platform.ResponseObject;
import com.opensource.nredis.proxy.monitor.platform.RestStatus;
import com.opensource.nredis.proxy.monitor.service.IAutoPageSystemQrtzTriggerLogService;
import com.opensource.nredis.proxy.monitor.service.IPsDictService;
import com.opensource.nredis.proxy.monitor.string.utils.StringUtil;
/**
* controller
*
* @author liubing
* @date 2016/12/30 08:41
* @version v1.0.0
*/
@Controller
public class AutoPageSystemQrtzTriggerLogController {
	
	@Autowired
	private IAutoPageSystemQrtzTriggerLogService autoPageSystemQrtzTriggerLogService;
	
	@Autowired
	private  IPsDictService psDictService;
	/**
	 * 查询详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/searchAutoPageSystemQrtzTriggerLog", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchPageElement(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		AutoPageSystemQrtzTriggerLog autoPageSystemQrtzTriggerLog= autoPageSystemQrtzTriggerLogService.getEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		responseObject.setData(autoPageSystemQrtzTriggerLog);
		return responseObject;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeAutoPageSystemQrtzTriggerLog", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject removeAutoPageSystemQrtzTriggerLog(@Validated String ids) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		if(StringUtil.isEmpty(ids)){
			responseObject.setStatus(RestStatus.SERVER_ERROR.code);
			responseObject.setMessage("必须选择数据");
			return responseObject;
		}
		String[] idStrings=ids.split(",");
		for(String id:idStrings){
			autoPageSystemQrtzTriggerLogService.deleteEntityById(Integer.parseInt(id));
		}
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
	@RequestMapping(value = "/searchAutoPageSystemQrtzTriggerLogs", method = RequestMethod.GET)
	@ResponseBody
	public DataGridObject searchAutoPageSystemQrtzTriggerLogs(AutoPageSystemQrtzTriggerLog autoPageSystemQrtzTriggerLog,Integer page , Integer rows) throws Exception{
		DataGridObject responseObject=new DataGridObject();
		PageList<AutoPageSystemQrtzTriggerLog> pageList=null;
		if(page==null||page==0){
			page=1;
		}
		PageAttribute pageAttribute=new PageAttribute(page, rows);
		pageList=autoPageSystemQrtzTriggerLogService.queryEntityPageList(pageAttribute, autoPageSystemQrtzTriggerLog, null);
		List<AutoPageSystemQrtzTriggerLog> autoPageSystemQrtzTriggerLogs=pageList.getDatas();
		for(AutoPageSystemQrtzTriggerLog autoPageSystemQrtzTriggerLog2:autoPageSystemQrtzTriggerLogs){
			PsDict psDict=new PsDict();
			psDict.setPsMoudle("SYS_GROUP");
			psDict.setPsValue(String.valueOf(autoPageSystemQrtzTriggerLog2.getJobGroup()));
			List<PsDict> psDicts=psDictService.queryEntityList(psDict);
			if(psDicts!=null&&psDicts.size()>0){
				autoPageSystemQrtzTriggerLog2.setJobGroupName(psDicts.get(0).getPsKey());
				autoPageSystemQrtzTriggerLog2.setTriggerTimeString(DateBase.formatDate(autoPageSystemQrtzTriggerLog2.getTriggerTime(), DateBase.DATE_PATTERN_DATETIME));
				autoPageSystemQrtzTriggerLog2.setHandleTimeString(DateBase.formatDate(autoPageSystemQrtzTriggerLog2.getHandleTime(), DateBase.DATE_PATTERN_DATETIME));
				autoPageSystemQrtzTriggerLog2.setJobClassName(JobClassEnums.getMessage(autoPageSystemQrtzTriggerLog2.getJobClass()));
			}
		}
		
		responseObject.setTotal(pageList.getPage().getRowCount());
		responseObject.setRows(autoPageSystemQrtzTriggerLogs);
		return responseObject;
	}
	
}
