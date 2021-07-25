package org.ssm.dufy.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
/**
 * 
 * @author dufyun
 *
 */
@ControllerAdvice
public class BaseControllerAdvice {
	
	private static Logger log = LoggerFactory.getLogger("loger");
    /**
     * Handle exceptions thrown by handlers.
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(Exception e, WebRequest request) {
    	log.error(e.getMessage(),e);
    	
    	return "redirect:/errorpage/505.jsp";
    }
    //下面可以定义各种异常的捕获处理方法
    //还可以是自定义异常捕获处理方法
    
      
}
