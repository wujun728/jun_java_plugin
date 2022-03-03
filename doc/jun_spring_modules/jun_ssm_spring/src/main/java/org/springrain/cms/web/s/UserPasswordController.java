package org.springrain.cms.web.s;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.SecUtils;
import org.springrain.system.entity.User;
import org.springrain.system.service.IUserService;

/**
 * 用户管理Controller,PC和手机浏览器用ACE自适应,APP提供JSON格式的数据接口
 * 
 * @copyright {@link weicms.net}
 * @author weicms.net<Auto generate>
 * @version 2014-06-26 11:36:47
 * @see org.springrain.springrain.web.User
 */
@Controller
@RequestMapping(value = "/s/password/{siteId}")
public class UserPasswordController extends BaseController {
	@Resource
	private IUserService userService;
	 
	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/modifiypwd/pre")
	public String modifiypwdpre(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = SessionUser.getUserId();
		if(StringUtils.isEmpty(userId)){
			return "/cms/userpassword/modifiypwd";
		}
		//获取当前登录人
		User currentUser = userService.findUserById(userId);
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setData(currentUser);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/cms/userpassword/modifiypwd";
	}
	@RequestMapping(value="/modifiypwd/ispwd")
	@ResponseBody 
	public  Map<String, Object> checkPwd(HttpServletRequest request,HttpServletResponse response,Model model)throws Exception{
		String userId = SessionUser.getUserId();
		String pwd=request.getParameter("pwd");
		Map<String, Object> maps=new HashMap<String, Object>();
		User user = userService.findById(userId, User.class);
		if(user==null){
			maps.put("msg", "-1");
			maps.put("msgbox", "数据有问题，正在返回");
			return maps;
		}
		if(user.getPassword().equals(SecUtils.encoderByMd5With32Bit(pwd))){
			maps.put("msg", "1");
			maps.put("msgbox", "正确");
			return maps;
		}else{
			maps.put("msg", "0");
			maps.put("msgbox", "原始密码错误，请修改");
			return maps;
		}
		
	}
	@RequestMapping(value="/modifiypwd/save")
	@ResponseBody 
	public  ReturnDatas modifiySave(HttpServletRequest request,HttpServletResponse response,Model model)throws Exception{
		ReturnDatas datas=ReturnDatas.getSuccessReturnDatas();
		String userId=request.getParameter("id");
		String pwd=request.getParameter("newpwd");
		String repwd=request.getParameter("renewpwd");
		User user = userService.findById(userId, User.class);
		if(user==null){
			datas.setStatus(ReturnDatas.ERROR);
			datas.setMessage("数据有问题，正在返回");
			return datas;
		}
		if(StringUtils.isEmpty(pwd)||StringUtils.isEmpty(repwd)){
			datas.setStatus(ReturnDatas.ERROR);
			datas.setMessage("新密码或重复密码为空，请修改。");
			return datas;
		}
		pwd=pwd.trim();
		repwd=repwd.trim();
		if(!pwd.equals(repwd)){
			datas.setStatus(ReturnDatas.ERROR);
			datas.setMessage("两次密码不一致，请修改。");
			return datas;
		}
		try{
			user.setPassword(SecUtils.encoderByMd5With32Bit(pwd));
			userService.update(user);
			datas.setMessage("修改成功，请用新密码登录，即将退出。");
			return datas;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			datas.setStatus(ReturnDatas.ERROR);
			datas.setMessage("系统故障，请稍后再试。");
			return datas;
		}
		
	}
	
	
	
}
