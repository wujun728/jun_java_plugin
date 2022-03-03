package com.erp.jee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.jee.pageModel.Json;
import com.erp.jee.pageModel.SessionInfo;
import com.erp.jee.pageModel.User;
import com.erp.jee.service.UserServiceI;
import com.jun.plugin.utils.ExceptionUtil;
import com.jun.plugin.utils.ConfigUtil;
import com.jun.plugin.utils.WebUtil;

/**
 * 用户ACTION
 * 
 * @author Wujun
 * 
 */
//@Action(value = "userAction", results = { @Result(name = "user", location = "/com/jeecg/user.jsp"), @Result(name = "showUserInfo", location = "/user/userInfo.jsp") })
@Controller
@RequestMapping("/userAction.do")
public class UserAction extends BaseAction{

	private static final Logger logger = Logger.getLogger(UserAction.class);

	private User user = new User();
	private UserServiceI userService;

	public UserServiceI getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}

	public User getModel() {
		return user;
	}

	/**
	 * 用户登录
	 */
	@RequestMapping(params = "login")
	public void login(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		WebUtil.paramToBean(request, user);
		User u = userService.login(user);
		if (u != null) {
			SessionInfo sessionInfo = saveSessionInfo(request, u);
			j.setSuccess(true);
			j.setMsg("用户登录成功！");
			j.setObj(sessionInfo);

			changeUserAuths(request, u);
		} else {
			j.setMsg("用户名或密码错误!");
		}
		writeJson(response, j);
	}

	/**
	 * 改变用户的权限列表
	 * 
	 * @param u
	 */
	@RequestMapping(params = "method=login")
	private void changeUserAuths(HttpServletRequest request,User u) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		sessionInfo.setAuths(userService.getAuths(u));
	}

	/**
	 * 登录成功将用户信息保存到SESSION中
	 * 
	 * @param u
	 *            用户对象
	 * @return
	 */
	@RequestMapping(params = "method=login")
	private SessionInfo saveSessionInfo(HttpServletRequest request,User u) {
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserId(u.getCid());
		sessionInfo.setLoginName(u.getCname());
		//update-begin author:zhangdaihao date:20121121 for:将取的账号改为用户名
		sessionInfo.setRealName(u.getRealname());
		//update-end author:zhangdaihao date:20121121 for:将取的账号改为用户名
		sessionInfo.setLoginPassword(user.getCpwd());
		sessionInfo.setUserType(u.getUsertype());
		sessionInfo.setIp(WebUtil.getIpAddr(request));
		request.getSession().setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
		request.getSession().setAttribute("usertype", u.getUsertype());
		return sessionInfo;
	}

	/**
	 * 退出系统
	 */
	@RequestMapping(params = "method=login")
	public void logout(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
		}
		j.setSuccess(true);
		writeJson(response, j);
	}

	/**
	 * 表格方式用户登录
	 */
	@RequestMapping(params = "method=login")
	public void loginDatagrid(Model model, HttpServletRequest request, HttpServletResponse response) {
		if (user.getQ() != null && !user.getQ().trim().equals("")) {
			user.setCname(user.getQ());
		}
		writeJson(response, userService.datagrid(user));
	}

	/**
	 * 补全方式用户登录
	 */
	@RequestMapping(params = "method=login")
	public void loginCombobox(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, userService.loginCombobox(user));
	}

	/**
	 * 用户注册
	 */
	@RequestMapping(params = "method=reg")
	public void reg(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			userService.reg(user);
			j.setSuccess(true);
			j.setMsg("注册成功！");
		} catch (Exception e) {
			j.setMsg("用户名已存在！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		writeJson(response, j);
	}

	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=goUser")
	public String goUser(Model model, HttpServletRequest request, HttpServletResponse response) {
		return "user";
	}

	/**
	 * 获得用户datagrid
	 */
	@RequestMapping(params = "method=datagrid")
	public void datagrid(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, userService.datagrid(user));
	}

	/**
	 * 添加一个用户
	 */
	@RequestMapping(params = "method=add")
	public void add(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			userService.add(user);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("用户名已存在！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		writeJson(response, j);
	}

	/**
	 * 编辑一个用户
	 */
	@RequestMapping(params = "method=edit")
	public void edit(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			userService.update(user);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("编辑失败，用户名已存在！");
		}
		writeJson(response, j);
	}

	/**
	 * 删除一个或多个用户
	 */
	@RequestMapping(params = "method=delete")
	public void delete(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		userService.delete(user.getIds());
		j.setSuccess(true);
		writeJson(response, j);
	}

	/**
	 * 批量修改用户密码
	 */
	@RequestMapping(params = "method=modifyPwd")
	public void modifyPwd(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		userService.modifyPwd(user);
		j.setSuccess(true);
		j.setMsg("密码修改成功！");
		writeJson(response, j);
	}

	/**
	 * 跳转到当前用户信息页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=showUserInfo")
	public String showUserInfo(Model model, HttpServletRequest request, HttpServletResponse response) {
		return "showUserInfo";
	}

	/**
	 * 修改自己的密码
	 */
	@RequestMapping(params = "method=modifySelfPwd")
	public void modifySelfPwd(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		user.setCid(sessionInfo.getUserId());
		if (userService.modifySelfPwd(user)) {
			j.setSuccess(true);
			j.setMsg("密码修改成功！");
		} else {
			j.setMsg("修改密码失败！原密码错误！");
		}
		writeJson(response, j);
	}

	/**
	 * 批量修改用户角色
	 */
	@RequestMapping(params = "method=modifyUserRole")
	public void modifyUserRole(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		userService.modifyUserRole(user);
		j.setSuccess(true);
		j.setMsg("角色修改成功！");
		writeJson(response, j);
	}
}
