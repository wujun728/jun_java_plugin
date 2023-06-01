package com.jun.plugin.common.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.plugin.common.exception.CustomException;
import com.jun.plugin.common.utils.R;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)// 需要捕获的异常
    public R error(CustomException e) {
        System.out.println("wo lu guo1");
        System.out.println("wo lu guo2");
        e.printStackTrace();
        System.out.println("wo lu guo3");
        System.out.println("wo lu guo4");
        return R.error().code(e.getCode()).msg(e.getMsg());
    }
}
