package com.roncoo.jui.web.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.roncoo.jui.common.util.base.BaseController;
import com.roncoo.jui.common.util.base.RoncooException;

/**
 * 错误处理
 * 
 * @author Wujun
 */
@RestControllerAdvice
public class RoncooExceptionHandler extends BaseController {

	@ExceptionHandler({ RoncooException.class })
	@ResponseStatus(HttpStatus.OK)
	public String processBizException(RoncooException e) {
		logger.error(e.toString(), e);
		return error(e.getExpMsg());
	}

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.OK)
	public String processException(Exception e) {
		logger.error(e.getMessage(), e);
		return error("系统错误");
	}

}
