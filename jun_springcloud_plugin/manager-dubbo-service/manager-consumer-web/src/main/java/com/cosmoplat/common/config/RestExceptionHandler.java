package com.cosmoplat.common.config;

import com.cosmoplat.common.exception.BusinessException;
import com.cosmoplat.common.exception.code.BaseResponseCode;
import com.cosmoplat.common.utils.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * RestExceptionHandler
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * 系统繁忙，请稍候再试"
     */
    @ExceptionHandler(Exception.class)
    public DataResult handleException(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            return new DataResult(businessException.getMessageCode(), businessException.getDetailMessage());
        }
        log.error("Exception,exception:{}", e.getMessage());
        return DataResult.getResult(BaseResponseCode.SYSTEM_BUSY);
    }

    /**
     * 自定义全局异常处理
     */
    @ExceptionHandler(value = BusinessException.class)
    DataResult businessExceptionHandler(BusinessException e) {
        log.error("Exception,exception:{}", e.getDetailMessage());
        return new DataResult(e.getMessageCode(), e.getDetailMessage());
    }


    /**
     * 处理validation 框架异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    DataResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{}", e.getBindingResult().getAllErrors());
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return DataResult.getResult(BaseResponseCode.METHODARGUMENTNOTVALIDEXCEPTION.getCode(), errors.get(0).getDefaultMessage());
    }

    /**
     * 校验List<entity>类型， 需要controller添加@Validated注解
     * 处理Validated List<entity> 异常
     */
    @ExceptionHandler
    public DataResult handle(ConstraintViolationException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{}", e.getMessage());
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            builder.append(violation.getMessage());
        }
        return DataResult.getResult(BaseResponseCode.METHODARGUMENTNOTVALIDEXCEPTION.getCode(), builder.toString());
    }

}
