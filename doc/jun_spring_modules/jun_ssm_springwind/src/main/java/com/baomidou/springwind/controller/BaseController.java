package com.baomidou.springwind.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.framework.controller.SuperController;
import com.baomidou.framework.mail.MailHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.service.IPermissionService;
import com.baomidou.springwind.service.IUserService;

/**
 * <p>
 * 基础控制器
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-13
 */
public class BaseController extends SuperController implements HandlerInterceptor {

	@Autowired
	protected MailHelper mailHelper;

	@Autowired
	protected IUserService userService;

	@Autowired
	private IPermissionService permissionService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		/*
		 * 方法调用后调用该方法，返回数据给请求页
		 */
		if (isLegalView(modelAndView)) {
			modelAndView.addObject("currentUser", userService.selectById(getCurrentUserId()));
			modelAndView.addObject("menuList", permissionService.selectMenuVOByUserId(getCurrentUserId()));
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/**
	 * 判断是否为合法的视图地址
	 * <p>
	 * 
	 * @param modelAndView
	 *            spring 视图对象
	 * @return boolean
	 */
	protected boolean isLegalView(ModelAndView modelAndView) {
		boolean legal = false;
		if (modelAndView != null) {
			String viewUrl = modelAndView.getViewName();
			if (viewUrl != null && viewUrl.contains("redirect:")) {
				legal = false;
			} else {
				legal = true;
			}
		}
		return legal;
	}

	/**
	 * <p>
	 * 转换为 bootstrap-table 需要的分页格式 JSON
	 * </p>
	 * 
	 * @param page
	 *            分页对象
	 * @return
	 */
	protected String jsonPage(Page<?> page) {
		JSONObject jo = new JSONObject();
		jo.put("total", page.getTotal());
		jo.put("rows", page.getRecords());
		return toJson(jo);
	}

	@Override
	protected <T> Page<T> getPage(int size) {
		int _size = size, _index = 1;
		if (request.getParameter("_size") != null) {
			_size = Integer.parseInt(request.getParameter("_size"));
		}
		if (request.getParameter("_index") != null) {
			int _offset = Integer.parseInt(request.getParameter("_index"));
			_index = _offset / _size + 1;
		}
		return new Page<T>(_index, _size);
	}

	protected String booleanToString(boolean rlt) {
		return rlt ? "true" : "false";
	}

}
