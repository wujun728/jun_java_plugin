/**
 * 
 */
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
import com.opensource.nredis.proxy.monitor.model.PsUser;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.platform.DataGridObject;
import com.opensource.nredis.proxy.monitor.platform.ResponseObject;
import com.opensource.nredis.proxy.monitor.platform.RestStatus;
import com.opensource.nredis.proxy.monitor.service.IPsUserService;
import com.opensource.nredis.proxy.monitor.string.utils.StringUtil;

/**
 * 用户动态控制类
 * @author liubing
 *
 */
@Controller
public class PsUserController {
	
	@Autowired
	private IPsUserService psUserService;
	
	/**
	 * 判断用户登录状态
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkAuth", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject checkAuth(@Validated String username,String password,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		if(StringUtil.isEmpty(username)||StringUtil.isEmpty(password)){
			responseObject.setStatus(RestStatus.PARAMETER_REQUIRED.code);
			responseObject.setMessage(RestStatus.PARAMETER_REQUIRED.message);
			return responseObject;
		}
		PsUser psUser=new PsUser();
		psUser.setUsername(username);
		psUser.setPassword(password);
		List<PsUser> psUsers=psUserService.queryEntityList(psUser);
		if(psUsers==null||psUsers.size()<1){
			responseObject.setStatus(RestStatus.NOT_RIGHT.code);
			responseObject.setMessage(RestStatus.NOT_RIGHT.message);
			return responseObject;
		}
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("userid", psUsers.get(0).getId());
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 获取当前用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/currentUser", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Object username=request.getSession().getAttribute("username");
		ResponseObject responseObject=new ResponseObject();
		if(username==null){
			responseObject.setStatus(RestStatus.NOT_CURRENTUSER.code);
			responseObject.setMessage(RestStatus.NOT_CURRENTUSER.message);
			return responseObject;
		}else{
			responseObject.setData(username.toString());
			responseObject.setStatus(RestStatus.SUCCESS.code);
			responseObject.setMessage(RestStatus.SUCCESS.message);
			return responseObject;
		}
	}
	
	/**
	 * 失效
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unvalidate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject unValidate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		request.getSession().removeAttribute("username");
		request.getSession().removeAttribute("userid");
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 新增用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/psUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject user(@Validated String username,String password,String userEmail,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		if(StringUtil.isEmpty(username)||StringUtil.isEmpty(password)||StringUtil.isEmpty(userEmail)){
			responseObject.setStatus(RestStatus.PARAMETER_REQUIRED.code);
			responseObject.setMessage(RestStatus.PARAMETER_REQUIRED.message);
			return responseObject;
		}
		Object currentUser=request.getSession().getAttribute("username");
		if(currentUser==null){
			currentUser="root";
		}
		PsUser psUser=new PsUser();
		psUser.setCreateTime(new Date());
		psUser.setCreateUser(currentUser.toString());
		psUser.setPassword(password);
		psUser.setUserEmail(userEmail);
		psUser.setUsername(username);
		//-------------------
		PsUser user=new PsUser();
		user.setPassword(password);
		user.setUsername(username);
		List<PsUser> psUsers=psUserService.queryEntityList(user);
		if(psUsers!=null&&psUsers.size()>0){
			responseObject.setStatus(RestStatus.NOT_UNIQUE.code);
			responseObject.setMessage(RestStatus.NOT_UNIQUE.message);
			return responseObject;
		}
		//----------判断唯一性------------
		psUserService.create(psUser);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 修改用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editPsUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editUser(@Validated Integer id, String username,String password,String userEmail,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		if(StringUtil.isEmpty(username)||StringUtil.isEmpty(password)||StringUtil.isEmpty(userEmail)||id==null||id==0){
			responseObject.setStatus(RestStatus.PARAMETER_REQUIRED.code);
			responseObject.setMessage(RestStatus.PARAMETER_REQUIRED.message);
			return responseObject;
		}
		PsUser oldUser=psUserService.getEntityById(id);
		if(oldUser.getUsername().equals(username)&&oldUser.getPassword().equals(password)){
			oldUser.setUserEmail(userEmail);
			psUserService.modifyEntityById(oldUser);
			responseObject.setStatus(RestStatus.SUCCESS.code);
			responseObject.setMessage(RestStatus.SUCCESS.message);
			return responseObject;
		}
		//-------------------
	    PsUser user=new PsUser();
		user.setPassword(password);
		user.setUsername(username);
		List<PsUser> psUsers=psUserService.queryEntityList(user);
		if(psUsers==null||psUsers.size()<1){
					responseObject.setStatus(RestStatus.NOT_UNIQUE.code);
					responseObject.setMessage(RestStatus.NOT_UNIQUE.message);
					return responseObject;
		}
		//----------判断唯一性------------
		oldUser.setPassword(password);
		oldUser.setUsername(username);
		oldUser.setUserEmail(userEmail);
		psUserService.modifyEntityById(oldUser);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/removePsUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject removeUser(@Validated Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		if(id==null||id==0){
			responseObject.setStatus(RestStatus.PARAMETER_REQUIRED.code);
			responseObject.setMessage(RestStatus.PARAMETER_REQUIRED.message);
			return responseObject;
		}
		psUserService.deleteEntityById(id);
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
	@RequestMapping(value = "/{id}/searchPsUser", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchUser(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		if(id==null||id==0){
			responseObject.setStatus(RestStatus.PARAMETER_REQUIRED.code);
			responseObject.setMessage(RestStatus.PARAMETER_REQUIRED.message);
			return responseObject;
		}
		PsUser psUser=psUserService.getEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		responseObject.setData(psUser);
		return responseObject;
	}
	
	/**
	 * 查询列表页
	 * @param username
	 * @param userEmail
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchPsUsers", method = RequestMethod.GET)
	@ResponseBody
	public DataGridObject searchUsers(@Validated String username,String userEmail, Integer page , Integer rows,HttpServletRequest request, HttpServletResponse response) throws Exception{
		DataGridObject responseObject=new DataGridObject();
		PageList<PsUser> pageList=null;
		PsUser psUser=new PsUser();
		if(!StringUtil.isEmpty(userEmail)){
			psUser.setUserEmail(userEmail);
		}
		if(!StringUtil.isEmpty(username)){
			psUser.setUsername(username);
		}
		if(page==null||page==0){
			page=1;
		}
		Object usernamesesion=request.getSession().getAttribute("username");
		if(usernamesesion!=null){
			psUser.setCreateUser(String.valueOf(usernamesesion));
		}
		
		PageAttribute pageAttribute=new PageAttribute(page, rows);
		pageList=psUserService.queryEntityPageList(pageAttribute, psUser, null);
		responseObject.setTotal(pageList.getPage().getRowCount());
		List<PsUser> psUsers=pageList.getDatas();
		for(PsUser user:psUsers){
			user.setCreateTimestring(DateBase.formatDate(user.getCreateTime(), DateBase.DATE_PATTERN_DATETIME));
		}
		responseObject.setRows(psUsers);
		return responseObject;
	}
}
