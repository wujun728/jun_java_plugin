package com.chentongwei.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.chentongwei.common.constant.ResponseEnum;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.exception.BussinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 统一异常处理器
 * 
 * @author TongWei.Chen 2017-5-14 18:57:55
 */
@Order(-1000)
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
	private static final Logger LOG = LoggerFactory.getLogger("exceptionLog");
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		LOG.error("程序出现异常：" + ex.getMessage(), ex);

		Result result = new Result();
    	StringBuilder sb = new StringBuilder();
    	
    	//处理异常
    	if(ex instanceof BussinessException) {
    		resolverBussinessException(ex, sb, result);
    	} else if (ex instanceof BindException) {
    		resolverBindException(ex, sb, result);
    	} else {
    		resolverOtherException(ex, sb, result);	
    	}

    	result.setResult(sb);
    	
    	response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    	response.setCharacterEncoding("UTF-8");
    	response.setHeader("Cache-Control", "no-cache, must-revalidate");  
	    try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException e) {
			LOG.error("将数据返回给客户端时发生异常：" + e.getMessage(), e);
			e.printStackTrace();
		}
	    //打印堆栈信息到控制台，方便调试
    	ex.printStackTrace();
		return new ModelAndView();
	}
    
    /*
     * 处理业务层异常 
     */
    private void resolverBussinessException(Exception ex, StringBuilder sb, Result result) {
    	BussinessException businessException = (BussinessException) ex;
		sb.append(businessException.getResponseEnum().getMsg());
		addResult(result, businessException.getResponseEnum().getCode(), businessException.getResponseEnum().getMsg());
    }
    
    /*
     * 处理参数绑定异常
     */
    private void resolverBindException(Exception ex, StringBuilder sb, Result result) {
    	BindException be = (BindException) ex;
        List<FieldError> errorList = be.getBindingResult().getFieldErrors();
        for (FieldError error : errorList) {
            sb.append(error.getObjectName());
            sb.append("对象的");
            sb.append(error.getField());
            sb.append("字段");
            sb.append(error.getDefaultMessage());
        }
        addResult(result, ResponseEnum.PARAM_ERROR.getCode(), ResponseEnum.PARAM_ERROR.getMsg());
    }
    
    /*
     * 处理其他异常
     */
    private void resolverOtherException(final Exception ex, StringBuilder sb, Result result) {
    	sb.append(ex.getMessage());
		addResult(result, ResponseEnum.FAILED.getCode(), ResponseEnum.FAILED.getMsg());
    }

    /*
     * 封装code和msg
     */
    private void addResult(Result result, final int code, final String msg) {
    	result.setCode(code);
		result.setMsg(msg);
    }
    
}