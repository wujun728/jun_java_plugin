package com.jun.plugin.demo.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jun.plugin.demo.exception.MyException;
import com.jun.plugin.demo.util.ResponseCode;
import com.jun.plugin.demo.util.ResponseData;

/**
 * @description 全局异常处理
 * @auther: CDHong
 * @date: 2019/6/24-11:30
 **/
@RestControllerAdvice
public class GlobalExceptionResolver{

    @ExceptionHandler(MyException.class)
    public ResponseData myException(MyException e){
        return ResponseData.exception(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseData exception(Exception e) {
        return ResponseData.exception(e.getMessage());
    }

}
