package com.kdyzm.validation.spring.bootdemo.config;

import com.kdyzm.validation.spring.bootdemo.model.WrapperResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class ValidationAdvice {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WrapperResult handler(Exception e) {
        //获取异常信息,获取异常堆栈的完整异常信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        //日志输出异常详情
        log.error(sw.toString());
        return WrapperResult.faild("服务异常，请稍后再试");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public WrapperResult handler(ConstraintViolationException e) {
        StringBuffer errorMsg = new StringBuffer();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        violations.forEach(x -> errorMsg.append(x.getMessage()).append(";"));
        return WrapperResult.faild(errorMsg.toString());
    }

    //处理校验异常，对于对象类型的数据的校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public WrapperResult handler(MethodArgumentNotValidException e) {
        StringBuffer sb = new StringBuffer();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        allErrors.forEach(msg -> sb.append(msg.getDefaultMessage()).append(";"));
        return WrapperResult.faild(sb.toString());
    }

    //文件上传文件大小超出限制
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public WrapperResult<Map<String,Object>> fileSizeException(MaxUploadSizeExceededException exception) {
        log.error("文件太大，上传失败",exception);
        return WrapperResult.faild("只允许上传不大于"+exception.getMaxUploadSize()+"的文件");
    }
}
