package com.ibeetl.admin.core.conf;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibeetl.admin.core.util.FormFieldException;
import com.ibeetl.admin.core.util.PlatformException;
import com.ibeetl.admin.core.web.JsonResult;

/**
 * 自定义的全局错误页面
 *
 * @author Wujun
 */
@Controller
public class CustomErrorController  extends AbstractErrorController {

	private static final String ERROR_PATH = "/error";
	Log log = LogFactory.getLog(ErrorController.class);
	
	@Autowired
	ObjectMapper objectMapper;
	
	public CustomErrorController() {
		super(new DefaultErrorAttributes());
	}

	@RequestMapping(ERROR_PATH)
	public ModelAndView getErrorPath(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(
				request, false));
		Throwable cause =getCause(request);
		int status =  (Integer)model.get("status");
		//错误信息
		String message = (String)model.get("message");
		//友好提示
		String errorMessage = getErrorMessage(cause);
		String requestPath = (String)model.get("path");
		
		List<FieldError> filedErrors = this.getFieldError(model, cause);



		//后台打印日志信息方方便查错
		log.info(status+":"+message+filedErrors, cause);
		log.info("requestPath"+":"+requestPath);
		
		
		response.setStatus(status);
		if(!isJsonRequest(request)){
			ModelAndView view = new ModelAndView("/error.html");
			view.addAllObjects(model);
			view.addObject("errorMessage", errorMessage);
			view.addObject("filedErrors", filedErrors);
			view.addObject("cause", cause);
			view.addObject("requestPath", requestPath);
			
			return view;
			
		}else{
			if(filedErrors==null){
				if(status==404){
					writeJson(response,JsonResult.http404(requestPath));
				}else{
					writeJson(response,JsonResult.failMessage(getErrorMessage(cause)));
				}
				
			}else{
				
				writeJson(response,JsonResult.fail(this.wrapFieldErrors(filedErrors)));
			}
			
		
			return null;
		}
		
		
	}
	
	protected List<FieldError> getFieldError(Map<String, Object> model,Throwable cause){
		List<FieldError> filedErrors = (List<FieldError>)model.get("errors");
		if(filedErrors!=null){
			return filedErrors;
		}
		if(cause instanceof FormFieldException){
			FormFieldException  fe = (FormFieldException)cause;
			return fe.getErrors();
		}
		return null;
		
		
	}
	
	protected List<Map<String,String>> wrapFieldErrors(List<FieldError> errors){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(FieldError e:errors){
			Map<String,String> error = new HashMap<String,String>();
			error.put("field", e.getField());
			error.put("message", e.getDefaultMessage());
			list.add(error);
		}
		return list;
	}
	
	
	
	protected boolean isJsonRequest(HttpServletRequest request){
		String requestUri =  (String)request.getAttribute("javax.servlet.error.request_uri");
		if(requestUri!=null&&requestUri.endsWith(".json")){
			return true;
		}else{
			return (request.getHeader("Accept").contains("application/json") || (request.getHeader("X-Requested-With") != null
					&& request.getHeader("X-Requested-With").contains("XMLHttpRequest")));
		}
	}
	
	protected void writeJson(HttpServletResponse response,JsonResult error){
		response.setContentType("application/json;charset=utf-8");
		try {
			response.getWriter().write(objectMapper.writeValueAsString(error));
		} catch (IOException e) {
			// ignore
		}
	}
	
	protected String getErrorMessage(Throwable ex) {
		if(ex instanceof PlatformException){
			return ex.getMessage();
		}else{
			return "服务器错误,请联系管理员";
		}
		
	}
	
	protected Throwable getCause(HttpServletRequest request) {
		Throwable error = (Throwable)request.getAttribute("javax.servlet.error.exception");
		if (error != null) {
			while (error instanceof ServletException && error.getCause() != null) {
				error = ((ServletException) error).getCause();
			}
		}
		return error;
	}
	
	

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
