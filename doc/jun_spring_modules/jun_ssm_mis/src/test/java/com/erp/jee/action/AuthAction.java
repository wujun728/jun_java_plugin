package com.erp.jee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.jee.pageModel.Auth;
import com.erp.jee.pageModel.Json;
import com.erp.jee.service.AuthServiceI;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.ExceptionUtil;

/**
 * 权限ACTION
 * 
 * @author Wujun
 * 
 */
//@Action(value = "authAction", results = { @Result(name = "auth", location = "/com/jeecg/auth.jsp") })
@Controller
@RequestMapping("/authAction.do")
public class AuthAction extends BaseAction{

	private static final Logger logger = Logger.getLogger(AuthAction.class);

	private Auth auth = new Auth();
	private AuthServiceI authService;

	public AuthServiceI getAuthService() {
		return authService;
	}

	@Autowired
	public void setAuthService(AuthServiceI authService) {
		this.authService = authService;
	}

	public Auth getModel() {
		return auth;
	}

	public String goAuth() {
		return "auth";
	}

	/**
	 * 获得权限treegrid
	 */
	@RequestMapping(params = "method=treegrid")
	public void treegrid(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, authService.treegrid(auth));
	}

	/**
	 * 删除一个权限
	 */
	@RequestMapping(params = "method=delete")
	public void delete(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			authService.delete(auth);
			j.setSuccess(true);
			j.setMsg("删除成功！");
			j.setObj(auth.getCid());
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("删除失败！");
		}
		writeJson(response, j);
	}

	/**
	 * 获取权限树
	 */
	@RequestMapping(params = "method=tree")
	public void tree(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, authService.tree(auth, false));
	}

	/**
	 * 获取权限树,递归子节点
	 */
	@RequestMapping(params = "method=treeRecursive")
	public void treeRecursive(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, authService.tree(auth, true));
	}

	/**
	 * 权限树,所有人都有权限访问这个
	 */
	@RequestMapping(params = "method=authTreeRecursive")
	public void authTreeRecursive(Model model, HttpServletRequest request, HttpServletResponse response) {
		//根据参数，判断是查询菜单权限还是按钮权限
		if(StringUtils.isNotBlank(auth.getCtype())){
			writeJson(response, authService.treeByCtype(auth, true));
		}else{
			writeJson(response, authService.tree(auth, true));
		}
	}
	
	/**
	 * 编辑权限
	 */
	@RequestMapping(params = "method=edit")
	public void edit(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			authService.edit(auth);
			j.setSuccess(true);
			j.setMsg("编辑成功!");
			j.setObj(auth.getCpid());
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("编辑失败！");
		}
		writeJson(response, j);
	}

	/**
	 * 添加权限
	 */
	@RequestMapping(params = "method=add")
	public void add(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			auth.setCtype(Constants.TAUTH_CTYPE_02);
			authService.add(auth);
			j.setSuccess(true);
			j.setMsg("添加成功!");
			j.setObj(auth.getCpid());
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("添加失败！");
		}
		writeJson(response, j);
	}

}
