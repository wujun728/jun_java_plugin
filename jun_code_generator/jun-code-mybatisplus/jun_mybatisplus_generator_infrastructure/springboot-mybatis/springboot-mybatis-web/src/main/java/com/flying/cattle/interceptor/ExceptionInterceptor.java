/**
 * @filename: ExceptionInterceptor 2019年5月20日
 * @project springboot-mybatis  V1.0
 * Copyright(c) 2018 flying-cattle Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flying.cattle.aid.IllegalPathException;
import com.github.flying.cattle.mdg.aid.JsonResult;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 全局异常处理</P>
 * @version: V1.0
 * @author: flying-cattle
 * 
 */
@ControllerAdvice
public class ExceptionInterceptor {

	@ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult<Object> exceptionHandler(Exception ex){
       return new JsonResult<Object>(ex);
    }
	
	@ExceptionHandler(value = IllegalPathException.class)
    public ModelAndView exceptionPathHandler(Exception ex){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("404.html");
		return mv;
    }
}
