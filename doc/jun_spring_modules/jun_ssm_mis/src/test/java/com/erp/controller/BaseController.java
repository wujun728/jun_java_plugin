package com.erp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.erp.viewModel.Json;
import com.jun.plugin.utils.ExceptionUtil;
import com.jun.plugin.utils.biz.CustomDateEditor;

/**
 * 类功能说明 TODO:基类action 类修改者 修改日期 修改说明 Title: BaseAction.java Description:福产流通科技 Copyright: Copyright (c) 2012 Company:福产流通科技
 * 
 * @author Wujun
 * @date 2013-4-19 上午08:18:21
 * @version V1.0
 */
@Controller
@RequestMapping("/baseController.do")
public class BaseController {
	/*private static final Logger logger = Logger.getLogger(BaseController.class);

	*//**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 * 
	 * @param binder
	 * 
	 *//*
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		// binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}*/
	
//	@Autowired
//	public HttpServletResponse httpServletResponse;
	

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 7493364888065600947L;

	private static final Logger logger = Logger.getLogger(BaseController.class);

	@Autowired(required = false)
	@Qualifier("jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;

	public void OutputJson(Object object, HttpServletResponse httpServletResponse) {
		PrintWriter out = null;
		// HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
		httpServletResponse.setContentType("application/json");
		httpServletResponse.setCharacterEncoding("utf-8");
		String json = null;
		try {
			out = httpServletResponse.getWriter();
			json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(json);
		out.close();
	}
	
	
	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	public void writeJson(HttpServletResponse httpServletResponse,Object object) {
		try {
			// SerializeConfig serializeConfig = new SerializeConfig();
			// serializeConfig.setAsmEnable(false);
			// String json = JSON.toJSONString(object);
			// String json = JSON.toJSONString(object, serializeConfig, SerializerFeature.PrettyFormat);
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			// String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
			httpServletResponse.setContentType("text/html;charset=utf-8");
			httpServletResponse.getWriter().write(json);
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			logger.debug(ExceptionUtil.getExceptionMessage(e));
		}
	}
	public void writeJson(Object object, HttpServletResponse httpServletResponse) {
		try {
			// SerializeConfig serializeConfig = new SerializeConfig();
			// serializeConfig.setAsmEnable(false);
			// String json = JSON.toJSONString(object);
			// String json = JSON.toJSONString(object, serializeConfig, SerializerFeature.PrettyFormat);
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			// String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
			httpServletResponse.setContentType("text/html;charset=utf-8");
			httpServletResponse.getWriter().write(json);
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			logger.debug(ExceptionUtil.getExceptionMessage(e));
		}
	}

	// HttpSession session, HttpServletRequest request,HttpServletResponse response
	public void OutputJson(Object object, String type, HttpServletResponse httpServletResponse) {
		PrintWriter out = null;
		// HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
		httpServletResponse.setContentType(type);
		httpServletResponse.setCharacterEncoding("utf-8");
		String json = null;
		try {
			out = httpServletResponse.getWriter();
			json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(json);
		out.close();
	}


	public Json getMessage(boolean flag) {
		Json json = new Json();
		if (flag) {
			json.setStatus(true);
			json.setMessage("数据更新成功！");
		} else {
			json.setMessage("提交失败了！");
		}
		return json;
	}

	// @InitBinder
	// protected void initBinder(WebDataBinder binder){
	// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	// sdf.setLenient(false);
	// binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf,false));
	// }
	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 * 
	 * @param binder
	 * 
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		// binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
	
	
	

}
