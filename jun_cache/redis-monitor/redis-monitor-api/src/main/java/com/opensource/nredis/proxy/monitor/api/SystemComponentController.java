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
import com.opensource.nredis.proxy.monitor.model.SystemApplicationComponent;
import com.opensource.nredis.proxy.monitor.model.SystemComponent;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.platform.DataGridObject;
import com.opensource.nredis.proxy.monitor.platform.ResponseObject;
import com.opensource.nredis.proxy.monitor.platform.RestStatus;
import com.opensource.nredis.proxy.monitor.service.ISystemApplicationComponentService;
import com.opensource.nredis.proxy.monitor.service.ISystemComponentService;
import com.opensource.nredis.proxy.monitor.string.utils.StringUtil;
/**
* controller
*
* @author liubing
* @date 2017/01/02 15:27
* @version v1.0.0
*/
@Controller
public class SystemComponentController {
	
	@Autowired
	private ISystemComponentService systemComponentService;
	
	@Autowired
	private ISystemApplicationComponentService systemApplicationComponentService;
	/**
	 * 新增
	 * @param systemComponent
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/systemComponent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject systemComponent(SystemComponent systemComponent,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		Object username=request.getSession().getAttribute("username");
		if(username==null){
			responseObject.setStatus(RestStatus.NOT_CURRENTUSER.code);
			responseObject.setMessage(RestStatus.NOT_CURRENTUSER.message);
			return responseObject;
		}
		systemComponent.setCreateUserName(String.valueOf(username));
		systemComponent.setCreateTime(new Date());
		systemComponentService.create(systemComponent);
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
	@RequestMapping(value = "/editSystemComponent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editSystemComponent(SystemComponent systemComponent,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		Object username=request.getSession().getAttribute("username");
		if(username==null){
			responseObject.setStatus(RestStatus.NOT_CURRENTUSER.code);
			responseObject.setMessage(RestStatus.NOT_CURRENTUSER.message);
			return responseObject;
		}
		SystemComponent component=systemComponentService.getEntityById(systemComponent.getId());
		systemComponent.setCreateUserName(component.getCreateUserName());
		systemComponent.setCreateTime(component.getCreateTime());
		
		systemComponentService.modifyEntityById(systemComponent);
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
	@RequestMapping(value = "/{id}/searchSystemComponent", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchPageElement(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		SystemComponent systemComponent= systemComponentService.getEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		responseObject.setData(systemComponent);
		return responseObject;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeSystemComponent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject removeSystemComponent(@Validated Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		SystemApplicationComponent systemApplicationComponent=new SystemApplicationComponent();
		systemApplicationComponent.setComponentId(id);
		List<SystemApplicationComponent> applicationComponents=systemApplicationComponentService.queryEntityList(systemApplicationComponent);
		if(applicationComponents!=null&&applicationComponents.size()>0){
			responseObject.setStatus(RestStatus.SERVER_ERROR.code);
			responseObject.setMessage("已经关联系统应用，不能删除");
			return responseObject;
		}
		
		systemComponentService.deleteEntityById(id);
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
	@RequestMapping(value = "/searchSystemComponents", method = RequestMethod.GET)
	@ResponseBody
	public DataGridObject searchSystemComponents(SystemComponent systemComponent,Integer page , Integer rows) throws Exception{
		DataGridObject responseObject=new DataGridObject();
		PageList<SystemComponent> pageList=null;
		if(page==null||page==0){
			page=1;
		}
		if(StringUtil.isNotEmpty(systemComponent.getComponentKey())){
			String componentKey=new String(systemComponent.getComponentKey().getBytes("ISO-8859-1"), "UTF-8");
			systemComponent.setComponentKey(componentKey);
		}
		
		PageAttribute pageAttribute=new PageAttribute(page, rows);
		pageList=systemComponentService.queryEntityPageList(pageAttribute, systemComponent, null);
		List<SystemComponent> systemComponents=pageList.getDatas();
		for(SystemComponent component:systemComponents){
			component.setCreateTimeString(DateBase.formatDate(component.getCreateTime(), DateBase.DATE_PATTERN_DATETIME));
		}
		responseObject.setTotal(pageList.getPage().getRowCount());
		responseObject.setRows(pageList.getDatas());
		return responseObject;
	}
	
}
