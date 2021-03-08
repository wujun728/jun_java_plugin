package com.zh.springbootexception.exception.handler;

import com.alibaba.fastjson.JSONObject;
import com.zh.springbootexception.dto.Result;
import com.zh.springbootexception.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.StringJoiner;

/**
 * @author Wujun
 * @date 2019/6/5
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result businessExceptionHandler(BusinessException ex){
        log.error("BusinessException异常信息：[{}]", ex.getMsg(),ex);
        return Result.genFailResult(ex.getAppResultCode());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,BindException.class})
    public Result bindingResultExceptionHandler(Exception ex){
        BindingResult bindingResult;
        String msg;
        if(ex instanceof MethodArgumentNotValidException) {
            msg = "BindException异常信息";
            bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
        }else{
            bindingResult = ((BindException) ex).getBindingResult();
            msg = "BindException异常信息";
        }
        StringJoiner sj = new StringJoiner(";");
        bindingResult.getAllErrors().forEach(e -> sj.add(e.getDefaultMessage()));
        JSONObject jsonResult = new JSONObject();
        jsonResult.put(bindingResult.getObjectName(),sj.toString());
        log.error("{}：[{}]",msg,jsonResult.toJSONString(),ex);
        return Result.genFailResult(jsonResult);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(ConstraintViolationException ex){
        log.error("ConstraintViolationException异常信息：[{}]", ex.getMessage(),ex);
        JSONObject jsonResult = new JSONObject();
        ex.getConstraintViolations().forEach(e -> jsonResult.put(e.getPropertyPath().toString(),e.getMessageTemplate()));
        return Result.genFailResult(jsonResult);
    }

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception ex){
        log.error("Exception异常信息：[{}]", ex.getMessage(),ex);
        return Result.genFailResult(ex.getMessage());
    }

}
