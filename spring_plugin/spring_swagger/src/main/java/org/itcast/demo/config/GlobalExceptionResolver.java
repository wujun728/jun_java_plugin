package org.itcast.demo.config;

import org.itcast.demo.exception.MyException;
import org.itcast.demo.util.ResponseCode;
import org.itcast.demo.util.ResponseData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
