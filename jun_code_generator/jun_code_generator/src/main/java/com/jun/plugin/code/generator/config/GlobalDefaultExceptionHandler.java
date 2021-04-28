package com.jun.plugin.code.generator.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.plugin.code.generator.model.ReturnInfo;

/**
 * @author zhengkai.blog.csdn.net
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ReturnInfo defaultExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        return ReturnInfo.error("代码生成失败:"+e.getMessage());
    }

}
