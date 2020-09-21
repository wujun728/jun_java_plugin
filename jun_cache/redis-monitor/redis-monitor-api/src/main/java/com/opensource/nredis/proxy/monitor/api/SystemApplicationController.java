package com.opensource.nredis.proxy.monitor.api;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opensource.nredis.proxy.monitor.date.utils.DateBase;
import com.opensource.nredis.proxy.monitor.enums.FinalStatusEnums;
import com.opensource.nredis.proxy.monitor.enums.OperateStatusEnums;
import com.opensource.nredis.proxy.monitor.jvm.tools.MonitorJVM;
import com.opensource.nredis.proxy.monitor.model.SystemApplication;
import com.opensource.nredis.proxy.monitor.model.SystemApplicationComponent;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.platform.DataGridObject;
import com.opensource.nredis.proxy.monitor.platform.ResponseObject;
import com.opensource.nredis.proxy.monitor.platform.RestStatus;
import com.opensource.nredis.proxy.monitor.service.ISystemApplicationComponentService;
import com.opensource.nredis.proxy.monitor.service.ISystemApplicationService;
import com.opensource.nredis.proxy.monitor.string.utils.StringUtil;
/**
* controller
*
* @author liubing
* @date 2016/12/31 08:09
* @version v1.0.0
*/
@Controller
public class SystemApplicationController {
	
	@Autowired
	private ISystemApplicationService systemApplicationService;
	
	@Autowired
	private ISystemApplicationComponentService systemApplicationComponentService;
	/**
	 * 新增
	 * @param systemApplication
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/systemApplication", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject systemApplication(SystemApplication systemApplication,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		Object username=request.getSession().getAttribute("username");
		if(username==null){
			responseObject.setStatus(RestStatus.NOT_CURRENTUSER.code);
			responseObject.setMessage(RestStatus.NOT_CURRENTUSER.message);
			return responseObject;
		}
		if(StringUtil.isEmpty(systemApplication.getJmxUserName())){
			systemApplication.setJmxUserName(" ");
		}
		
		if(StringUtil.isEmpty(systemApplication.getJmxPassWord())){
			systemApplication.setJmxPassWord(" ");
		}
		if(systemApplication.getApplicationPort()==null){
			systemApplication.setApplicationPort(0);
		}
		
		Date date=new Date();
		systemApplication.setVersion(0);
		systemApplication.setCreateUserName(String.valueOf(username));
		systemApplication.setCreateTime(date);
		systemApplication.setJmxStatus(FinalStatusEnums.FAIL.getCode());//默认没有通
		systemApplication.setStatus(OperateStatusEnums.DOING.getCode());
		systemApplicationService.create(systemApplication);
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
	@RequestMapping(value = "/editSystemApplication", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editSystemApplication(SystemApplication systemApplication,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		Object username=request.getSession().getAttribute("username");
		if(username==null){
			responseObject.setStatus(RestStatus.NOT_CURRENTUSER.code);
			responseObject.setMessage(RestStatus.NOT_CURRENTUSER.message);
			return responseObject;
		}
		SystemApplication oldSystemApplication=systemApplicationService.getEntityById(systemApplication.getId());
		if(!oldSystemApplication.getCreateUserName().equals(String.valueOf(username))){
			responseObject.setStatus(RestStatus.CAN_NOT_AUTH.code);
			responseObject.setMessage("不能编辑别人的数据");
			return responseObject;
		}
		if(StringUtil.isEmpty(systemApplication.getJmxUserName())){
			systemApplication.setJmxUserName(" ");
		}
		if(StringUtil.isEmpty(systemApplication.getJmxPassWord())){
			systemApplication.setJmxPassWord(" ");
		}
		systemApplication.setVersion(oldSystemApplication.getVersion());//版本号
		systemApplication.setCreateTime(oldSystemApplication.getCreateTime());
		systemApplication.setCreateUserName(oldSystemApplication.getCreateUserName());
		systemApplication.setJmxStatus(oldSystemApplication.getJmxStatus());
		systemApplication.setStatus(oldSystemApplication.getStatus());
		systemApplicationService.modifyEntityById(systemApplication);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	/**
	 * 测试是否开通
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/testOpenSystemComponent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject testOpenSystemComponent(@Validated Integer id)throws Exception{
		ResponseObject responseObject=new ResponseObject();
		SystemApplication systemApplication= systemApplicationService.getEntityById(id);
		MonitorJVM monitorJVM=new MonitorJVM(systemApplication.getJmxHost(), systemApplication.getJmxPort(), systemApplication.getJmxUserName(), systemApplication.getJmxPassWord());
		if(monitorJVM.getMbsc()!=null){
			systemApplication.setJmxStatus(FinalStatusEnums.SUCCESS.getCode());
			systemApplicationService.modifyEntityById(systemApplication);
			monitorJVM.close();
		}else{
			systemApplication.setJmxStatus(FinalStatusEnums.FAIL.getCode());
			systemApplicationService.modifyEntityById(systemApplication);
		}
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 关联
	 * @param applicationId
	 * @param componentIds
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/linkSystemComponent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject linkSystemComponent(@Validated Integer applicationId,String componentIds) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		
		if(StringUtil.isEmpty(componentIds)||applicationId==null){
			responseObject.setStatus(RestStatus.PARAMETER_REQUIRED.code);
			responseObject.setMessage(RestStatus.PARAMETER_REQUIRED.message);
			return responseObject;
		}
		SystemApplicationComponent systemApplicationComponent=new SystemApplicationComponent();
		systemApplicationComponent.setApplicationId(applicationId);
		List<SystemApplicationComponent> applicationComponents=systemApplicationComponentService.queryEntityList(systemApplicationComponent);
		if(applicationComponents!=null&&applicationComponents.size()>0){
			for(SystemApplicationComponent applicationComponent:applicationComponents){
				systemApplicationComponentService.deleteEntityById(applicationComponent.getId());
			}
		}
		String [] components=componentIds.split(",");
		for(String componentId:components){
			SystemApplicationComponent applicationComponent=new SystemApplicationComponent();
			applicationComponent.setApplicationId(applicationId);
			applicationComponent.setComponentId(Integer.parseInt(componentId));
			systemApplicationComponentService.create(applicationComponent);
		}
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 查询
	 * @param applicationId
	 * @param componentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchSystemComponentByapplicationIdAndcomponentId", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchSystemComponentByapplicationIdAndcomponentId(@Validated Integer applicationId,Integer componentId) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		if(applicationId==null||applicationId==0||componentId==null||componentId==0){
			responseObject.setStatus(RestStatus.SERVER_ERROR.code);
			responseObject.setMessage(RestStatus.SERVER_ERROR.message);
			return responseObject;
		}
		SystemApplicationComponent systemApplicationComponent=new SystemApplicationComponent();
		systemApplicationComponent.setApplicationId(applicationId);
		systemApplicationComponent.setComponentId(componentId);
		List<SystemApplicationComponent> applicationComponents=systemApplicationComponentService.queryEntityList(systemApplicationComponent);
		if(applicationComponents!=null&&applicationComponents.size()>0){
			responseObject.setStatus(RestStatus.SUCCESS.code);
			responseObject.setMessage(RestStatus.SUCCESS.message);
			return responseObject;
		}else{
			responseObject.setStatus(RestStatus.SERVER_ERROR.code);
			responseObject.setMessage(RestStatus.SERVER_ERROR.message);
			return responseObject;
		}
		
	}
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/searchSystemApplication", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchPageElement(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		SystemApplication systemApplication= systemApplicationService.getEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		responseObject.setData(systemApplication);
		return responseObject;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeSystemApplication", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject removeSystemApplication(@Validated Integer id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		Object username=request.getSession().getAttribute("username");
		if(username==null){
			responseObject.setStatus(RestStatus.NOT_CURRENTUSER.code);
			responseObject.setMessage(RestStatus.NOT_CURRENTUSER.message);
			return responseObject;
		}
		SystemApplication oldSystemApplication=systemApplicationService.getEntityById(id);
		if(!oldSystemApplication.getCreateUserName().equals(String.valueOf(username))){
			responseObject.setStatus(RestStatus.CAN_NOT_AUTH.code);
			responseObject.setMessage("不能编辑别人的数据");
			return responseObject;
		}
		systemApplicationService.deleteEntityById(id);
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
	@RequestMapping(value = "/searchSystemApplications", method = RequestMethod.GET)
	@ResponseBody
	public DataGridObject searchSystemApplications(SystemApplication systemApplication,Integer page , Integer rows,HttpServletRequest request, HttpServletResponse response) throws Exception{
		DataGridObject responseObject=new DataGridObject();
		PageList<SystemApplication> pageList=null;
		if(page==null||page==0){
			page=1;
		}
		if(StringUtil.isNotEmpty(systemApplication.getApplicationName())){
			String applicationName=new String(systemApplication.getApplicationName().getBytes("ISO-8859-1"), "UTF-8");
			systemApplication.setApplicationName(applicationName);
		}
		
		Object username=request.getSession().getAttribute("username");
		if(username!=null){
			systemApplication.setCreateUserName(String.valueOf(username));
		}
		PageAttribute pageAttribute=new PageAttribute(page, rows);
		pageList=systemApplicationService.queryEntityPageList(pageAttribute, systemApplication, null);
		List<SystemApplication> systemApplications=pageList.getDatas();
		for(SystemApplication application:systemApplications){
			application.setJmxStatusString(FinalStatusEnums.getMessage(application.getJmxStatus()));
			application.setCreateTimeString(DateBase.formatDate(application.getCreateTime(), DateBase.DATE_PATTERN_DATETIME));
		}
		
		responseObject.setTotal(pageList.getPage().getRowCount());
		responseObject.setRows(systemApplications);
		return responseObject;
	}
	
}
