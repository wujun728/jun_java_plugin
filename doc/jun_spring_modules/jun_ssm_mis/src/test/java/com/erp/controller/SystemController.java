package com.erp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.service.ILoginService;
import com.erp.shiro.CaptchaUsernamePasswordToken;
import com.erp.shiro.IncorrectCaptchaException;
import com.erp.viewModel.Json;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.StringUtil;
import com.jun.plugin.utils.WebUtil;

/**
 * 类功能说明 TODO: 类修改者 修改日期 修改说明
 * <p>
 * Title: LoginAction.java
 * </p>
 * <p>
 * Description:福产流通科技
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company:福产流通科技
 * </p>
 * 
 * @author Wujun
 * @date 2013-4-19 上午10:13:47
 * @version V1.0
 */
// @Action(value = "systemAction", results = { @Result(name = Constants.LOGIN_SUCCESS_URL, location = "/index.jsp"),
// @Result(name = Constants.LOGIN_URL,location = "/login.jsp"),
// @Result(name = Constants.LOGIN_LOGIN_OUT_URL,type="redirect",location = "systemAction!loginInit.action")})
@Controller
@RequestMapping("/systemController.do")
public class SystemController extends BaseController {
	private static final Logger logger = Logger.getLogger(SystemController.class);
	private static final long serialVersionUID = -6019556530071263499L;
	// private String userName;
	// private String password;
	// private String captcha;
	// private String userMacAddr;
	// private String userKey;

//	@Autowired(required = false)
//	@Qualifier("commonServiceImpl")
//	protected ICommonService commonServiceImpl;

	private ILoginService loginService;

	public ILoginService getLoginService() {
		return loginService;
	}

	@Autowired
	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(params = "method=load")
	public String load(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = WebUtil.getAllParameters(request);
		String userName = StringUtil.decodeToUtf(request.getParameter("userName"));
		String password = StringUtil.decodeToUtf(request.getParameter("password"));
		String captcha = StringUtil.decodeToUtf(request.getParameter("captcha"));
		// model.addAttribute("path", path);
		// model.addAttribute("status", status);
		String _captcha =(String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		System.err.println("_captcha="+_captcha+",captcha="+captcha);
		
		Subject subject = SecurityUtils.getSubject();
		CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken();
		token.setUsername(userName);
		token.setPassword(password.toCharArray());
		token.setCaptcha(captcha);
		token.setRememberMe(true);
		Json json = new Json();
		json.setTitle("登录提示");
		try {
			subject.login(token);
			System.out.println("sessionTimeout===>" + subject.getSession().getTimeout());
			json.setStatus(true);
		} catch (UnknownSessionException use) {
			subject = new Subject.Builder().buildSubject();
			subject.login(token);
			logger.error(Constants.UNKNOWN_SESSION_EXCEPTION);
			json.setMessage(Constants.UNKNOWN_SESSION_EXCEPTION);
		} catch (UnknownAccountException ex) {
			logger.error(Constants.UNKNOWN_ACCOUNT_EXCEPTION);
			json.setMessage(Constants.UNKNOWN_ACCOUNT_EXCEPTION);
		} catch (IncorrectCredentialsException ice) {
			json.setMessage(Constants.INCORRECT_CREDENTIALS_EXCEPTION);
		} catch (LockedAccountException lae) {
			json.setMessage(Constants.LOCKED_ACCOUNT_EXCEPTION);
		} catch (IncorrectCaptchaException e) {
			json.setMessage(Constants.INCORRECT_CAPTCHA_EXCEPTION);
		} catch (AuthenticationException ae) {
			json.setMessage(Constants.AUTHENTICATION_EXCEPTION);
		} catch (Exception e) {
			json.setMessage(Constants.UNKNOWN_EXCEPTION);
		}
		OutputJson(json, Constants.TEXT_TYPE_PLAIN, response);
		// token.clear();
		return null;
	}

	/**
	 * 函数功能说明 TODO:用户登出 Administrator修改者名字 2013-5-9修改日期 修改内容
	 * 
	 * @Title: logout
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=logout")
	public String logout(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SecurityUtils.getSubject().logout();
		Json json = new Json();
		json.setStatus(true);
		OutputJson(json, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:查询用户所有权限菜单 Administrator修改者名字 2013-5-9修改日期 修改内容
	 * 
	 * @Title: findAllFunctionList
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findAllFunctionList")
	public String findAllFunctionList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputJson(loginService.findMenuList(), response);
		return null;
	}

}
