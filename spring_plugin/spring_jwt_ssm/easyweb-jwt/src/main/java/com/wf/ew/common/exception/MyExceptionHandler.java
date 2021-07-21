package com.wf.ew.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wf.jwtp.exception.TokenException;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * Created by wangfan on 2018-02-22 上午 11:29.
 */
@ControllerAdvice
public class MyExceptionHandler {
    private Logger logger = LoggerFactory.getLogger("MyExceptionHandler");

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Map<String, Object> errorHandler(Exception ex, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        // 根据不同错误获取错误信息
        if (ex instanceof IException) {
            map.put("code", ((IException) ex).getCode());
            map.put("msg", ex.getMessage());
        } else if (ex instanceof TokenException) {
            map.put("code", ((TokenException) ex).getCode());
            map.put("msg", ex.getMessage());
        } else {
            String message = ex.getMessage();
            map.put("code", 500);
            map.put("msg", message == null || message.trim().isEmpty() ? "未知错误" : message);
            logger.error(message, ex);
            ex.printStackTrace();
        }
        // 支持跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");
        return map;
    }

}
