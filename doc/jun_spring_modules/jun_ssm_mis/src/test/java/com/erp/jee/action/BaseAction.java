package com.erp.jee.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.controller.BaseController;

/**
 * 基础ACTION,其他ACTION继承此ACTION来获得writeJson和ActionSupport的功能
 * 
 * @author Wujun
 * 
 */
//@ParentPackage("defaultPackage")
//@Namespace("/")
@Controller
@RequestMapping("/baseAction.do")
public class BaseAction extends BaseController {

	private static final Logger logger = Logger.getLogger(BaseAction.class);
	

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	/*public void writeJson(Object object) {
		try {
			// SerializeConfig serializeConfig = new SerializeConfig();
			// serializeConfig.setAsmEnable(false);
			// String json = JSON.toJSONString(object);
			// String json = JSON.toJSONString(object, serializeConfig, SerializerFeature.PrettyFormat);
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			// String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
		} catch (IOException e) {
			logger.debug(ExceptionUtil.getExceptionMessage(e));
		}
	}*/
	
	public void writeJson(Object object, HttpServletResponse httpServletResponse) {
		super.writeJson(object, httpServletResponse);
	}
	public void writeJson(HttpServletResponse httpServletResponse,Object object) {
		super.writeJson(httpServletResponse, object);
	}
}
