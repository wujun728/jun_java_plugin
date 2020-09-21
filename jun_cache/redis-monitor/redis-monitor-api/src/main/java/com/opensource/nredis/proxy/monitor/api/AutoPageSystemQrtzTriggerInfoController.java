package com.opensource.nredis.proxy.monitor.api;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opensource.nredis.proxy.monitor.date.utils.DateBase;
import com.opensource.nredis.proxy.monitor.model.AutoPageSystemQrtzTriggerInfo;
import com.opensource.nredis.proxy.monitor.model.PsDict;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.platform.DataGridObject;
import com.opensource.nredis.proxy.monitor.platform.ResponseObject;
import com.opensource.nredis.proxy.monitor.platform.RestStatus;
import com.opensource.nredis.proxy.monitor.quartz.utils.DynamicSchedulerUtil;
import com.opensource.nredis.proxy.monitor.service.IAutoPageSystemQrtzTriggerInfoService;
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
public class AutoPageSystemQrtzTriggerInfoController {
	
	@Autowired
	private IAutoPageSystemQrtzTriggerInfoService autoPageSystemQrtzTriggerInfoService;
	
	@Autowired
	private  IPsDictService psDictService;
	
	/**
	 * 新增
	 * @param autoPageSystemQrtzTriggerInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/autoPageSystemQrtzTriggerInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject autoPageSystemQrtzTriggerInfo(AutoPageSystemQrtzTriggerInfo autoPageSystemQrtzTriggerInfo,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		Object username=request.getSession().getAttribute("username");
		if(username==null){
			responseObject.setStatus(RestStatus.NOT_CURRENTUSER.code);
			responseObject.setMessage(RestStatus.NOT_CURRENTUSER.message);
			return responseObject;
		}else{
			autoPageSystemQrtzTriggerInfo.setCreateUserName(String.valueOf(username));
		}
		
		if(autoPageSystemQrtzTriggerInfo.getJobClass()==null||autoPageSystemQrtzTriggerInfo.getJobClass()==0){
			responseObject.setMessage("执行方式不能为空");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		
		if (autoPageSystemQrtzTriggerInfo.getJobGroup()==null||autoPageSystemQrtzTriggerInfo.getJobGroup()==0) {
			responseObject.setMessage("请选择任务组");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		if (StringUtil.isEmpty(autoPageSystemQrtzTriggerInfo.getJobName())) {
			responseObject.setMessage("请输入任务名");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}

		if (StringUtil.isEmpty(autoPageSystemQrtzTriggerInfo.getClassName())) {
			responseObject.setMessage("请输入类名");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		if (!CronExpression.isValidExpression(autoPageSystemQrtzTriggerInfo.getJobCron())) {
			responseObject.setMessage("corn”不合法");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		if (StringUtil.isEmpty(autoPageSystemQrtzTriggerInfo.getJobDesc())) {
			responseObject.setMessage("请输入任务描述");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		if (StringUtil.isEmpty(autoPageSystemQrtzTriggerInfo.getAuthor())) {
			responseObject.setMessage("请输入负责人");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		
		if (StringUtil.isEmpty(autoPageSystemQrtzTriggerInfo.getAlarmEmail())) {
			responseObject.setMessage("请输入报警邮件");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}

		try {
			if (DynamicSchedulerUtil.checkExists(autoPageSystemQrtzTriggerInfo.getJobName(), String.valueOf(autoPageSystemQrtzTriggerInfo.getJobGroup()))) {
				responseObject.setMessage("此任务已存在，请更换任务组或任务名");
	            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
	            return responseObject;
			}
		} catch (SchedulerException e1) {
			responseObject.setMessage("此任务已存在，请更换任务组或任务名");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		autoPageSystemQrtzTriggerInfo.setAddTime(new Date());
		autoPageSystemQrtzTriggerInfo.setUpdateTime(new Date());
		autoPageSystemQrtzTriggerInfoService.create(autoPageSystemQrtzTriggerInfo);
		DynamicSchedulerUtil.addJob(autoPageSystemQrtzTriggerInfo);
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
	@RequestMapping(value = "/editAutoPageSystemQrtzTriggerInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editAutoPageSystemQrtzTriggerInfo(AutoPageSystemQrtzTriggerInfo autoPageSystemQrtzTriggerInfo,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		Object username=request.getSession().getAttribute("username");
		if(username==null){
			responseObject.setStatus(RestStatus.NOT_CURRENTUSER.code);
			responseObject.setMessage(RestStatus.NOT_CURRENTUSER.message);
			return responseObject;
		}
		if(autoPageSystemQrtzTriggerInfo.getJobClass()==null||autoPageSystemQrtzTriggerInfo.getJobClass()==0){
			responseObject.setMessage("执行方式不能为空");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		
		if (autoPageSystemQrtzTriggerInfo.getJobGroup()==null||autoPageSystemQrtzTriggerInfo.getJobGroup()==0) {
			responseObject.setMessage("请选择任务组");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		if (StringUtil.isEmpty(autoPageSystemQrtzTriggerInfo.getJobName())) {
			responseObject.setMessage("请输入任务名");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		if (!CronExpression.isValidExpression(autoPageSystemQrtzTriggerInfo.getJobCron())) {
			responseObject.setMessage("corn”不合法");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		
		if (StringUtil.isEmpty(autoPageSystemQrtzTriggerInfo.getClassName())) {
			responseObject.setMessage("请输入类名");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		if (StringUtil.isEmpty(autoPageSystemQrtzTriggerInfo.getJobDesc())) {
			responseObject.setMessage("请输入任务描述");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		if (StringUtil.isEmpty(autoPageSystemQrtzTriggerInfo.getAuthor())) {
			responseObject.setMessage("请输入负责人");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}
		
		if (StringUtil.isEmpty(autoPageSystemQrtzTriggerInfo.getAlarmEmail())) {
			responseObject.setMessage("请输入报警邮件");
            responseObject.setStatus(RestStatus.SERVER_ERROR.code);
            return responseObject;
		}

		
		AutoPageSystemQrtzTriggerInfo pageSystemQrtzTriggerInfo=autoPageSystemQrtzTriggerInfoService.getEntityById(autoPageSystemQrtzTriggerInfo.getId());
		try {
			if(!autoPageSystemQrtzTriggerInfo.getJobName().equals(pageSystemQrtzTriggerInfo.getJobName())||autoPageSystemQrtzTriggerInfo.getJobGroup()!=pageSystemQrtzTriggerInfo.getJobGroup()) {
				if (DynamicSchedulerUtil.checkExists(autoPageSystemQrtzTriggerInfo.getJobName(), String.valueOf(autoPageSystemQrtzTriggerInfo.getJobGroup()))) {
					responseObject.setMessage("此任务已存在，请更换任务组或任务名");
					responseObject.setStatus(RestStatus.SERVER_ERROR.code);
					return responseObject;
				}
			}
		} catch (SchedulerException e1) {
			responseObject.setMessage("此任务已存在，请更换任务组或任务名");
			responseObject.setStatus(RestStatus.SERVER_ERROR.code);
			return responseObject;
		}
		if(StringUtil.isEmpty(autoPageSystemQrtzTriggerInfo.getClassName())){
			autoPageSystemQrtzTriggerInfo.setClassName(" ");
		}
		autoPageSystemQrtzTriggerInfo.setUpdateTime(new Date());
		autoPageSystemQrtzTriggerInfo.setId(pageSystemQrtzTriggerInfo.getId());
		autoPageSystemQrtzTriggerInfo.setCreateUserName(pageSystemQrtzTriggerInfo.getCreateUserName());
		autoPageSystemQrtzTriggerInfoService.modifyEntityById(autoPageSystemQrtzTriggerInfo);
		DynamicSchedulerUtil.rescheduleJob(autoPageSystemQrtzTriggerInfo);
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
	@RequestMapping(value = "/{id}/searchAutoPageSystemQrtzTriggerInfo", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchPageElement(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		AutoPageSystemQrtzTriggerInfo autoPageSystemQrtzTriggerInfo= autoPageSystemQrtzTriggerInfoService.getEntityById(id);			
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		responseObject.setData(autoPageSystemQrtzTriggerInfo);
		return responseObject;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeAutoPageSystemQrtzTriggerInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject removeAutoPageSystemQrtzTriggerInfo(@Validated Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		AutoPageSystemQrtzTriggerInfo ffanJobQrtzTriggerInfo=autoPageSystemQrtzTriggerInfoService.getEntityById(id);
		DynamicSchedulerUtil.removeJob(ffanJobQrtzTriggerInfo.getJobName(), String.valueOf(ffanJobQrtzTriggerInfo.getJobGroup()));
		autoPageSystemQrtzTriggerInfoService.deleteEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	
	/**
	 * 暂停
	 * @param idString
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/pause", method = RequestMethod.POST)
	public ResponseObject pause(@Validated Integer id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResponseObject responseObject = new ResponseObject();
		AutoPageSystemQrtzTriggerInfo ffanJobQrtzTriggerInfo=autoPageSystemQrtzTriggerInfoService.getEntityById(id);
		DynamicSchedulerUtil.pauseJob(ffanJobQrtzTriggerInfo.getJobName(), String.valueOf(ffanJobQrtzTriggerInfo.getJobGroup()));
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
	    return responseObject;
	}
	
	/**
	 * 恢复
	 * @param idString
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/resume", method = RequestMethod.POST)
	public ResponseObject resume(@Validated Integer id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResponseObject responseObject = new ResponseObject();
		AutoPageSystemQrtzTriggerInfo ffanJobQrtzTriggerInfo=autoPageSystemQrtzTriggerInfoService.getEntityById(id);
		DynamicSchedulerUtil.resumeJob(ffanJobQrtzTriggerInfo.getJobName(), String.valueOf(ffanJobQrtzTriggerInfo.getJobGroup()));
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
	    return responseObject;
	}
	
	/**
	 * 执行JOB
	 * @param idString
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/trigger", method = RequestMethod.POST)
	public ResponseObject triggerJob(@Validated Integer id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResponseObject responseObject = new ResponseObject();
		AutoPageSystemQrtzTriggerInfo ffanJobQrtzTriggerInfo=autoPageSystemQrtzTriggerInfoService.getEntityById(id);
		DynamicSchedulerUtil.triggerJob(ffanJobQrtzTriggerInfo.getJobName(), String.valueOf(ffanJobQrtzTriggerInfo.getJobGroup()));
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
	@RequestMapping(value = "/searchAutoPageSystemQrtzTriggerInfos", method = RequestMethod.GET)
	@ResponseBody
	public DataGridObject searchAutoPageSystemQrtzTriggerInfos(AutoPageSystemQrtzTriggerInfo autoPageSystemQrtzTriggerInfo,Integer page , Integer rows,HttpServletRequest request, HttpServletResponse response) throws Exception{
		DataGridObject responseObject=new DataGridObject();
		Object username=request.getAttribute("username");
		if(username!=null){
			autoPageSystemQrtzTriggerInfo.setCreateUserName(String.valueOf(username));
		}
		if(StringUtil.isNotEmpty(autoPageSystemQrtzTriggerInfo.getJobName())){
			String applicationName=new String(autoPageSystemQrtzTriggerInfo.getJobName().getBytes("ISO-8859-1"), "UTF-8");
			autoPageSystemQrtzTriggerInfo.setJobName(applicationName);
		}
		PageList<AutoPageSystemQrtzTriggerInfo> pageList=null;
		if(page==null||page==0){
			page=1;
		}
		PageAttribute pageAttribute=new PageAttribute(page, rows);
		pageList=autoPageSystemQrtzTriggerInfoService.queryEntityPageList(pageAttribute, autoPageSystemQrtzTriggerInfo, null);
		responseObject.setTotal(pageList.getPage().getRowCount());
		List<AutoPageSystemQrtzTriggerInfo> autoPageSystemQrtzTriggerInfos=pageList.getDatas();
		for(AutoPageSystemQrtzTriggerInfo info:autoPageSystemQrtzTriggerInfos){
			PsDict psDict=new PsDict();
			psDict.setPsMoudle("SYS_GROUP");
			psDict.setPsValue(String.valueOf(info.getJobGroup()));
			List<PsDict> psDicts=psDictService.queryEntityList(psDict);
			if(psDicts!=null&&psDicts.size()>0){
				info.setJobGroupName(psDicts.get(0).getPsKey());
			}
			info.setAddTimeString(DateBase.formatDate(info.getAddTime(), DateBase.DATE_PATTERN_DATETIME));
			DynamicSchedulerUtil.fillJobInfo(info);
		}
		responseObject.setRows(autoPageSystemQrtzTriggerInfos);
		return responseObject;
	}
	
}
