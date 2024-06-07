package com.jun.plugin.common.config;

import com.jun.plugin.common.Result;
import com.jun.plugin.common.exception.BusinessException;
import com.jun.plugin.common.exception.code.BaseResponseCode;
import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * RestExceptionHandler
 *
 * @author wujun
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
    public Result handleException(Exception e) {
        log.error("Exception,exception:{}", e, e);
        return Result.getResult(500,"系统繁忙，请稍候再试!异常信息："+e.getMessage());
        //return Result.getResult(BaseResponseCode.SYSTEM_BUSY);
    }
    
//    @ExceptionHandler(org.springframework.dao.QueryTimeoutException.class)
//    public Result handleQueryTimeoutException(Exception e) {
//    	log.error("Exception,exception:{}", e, e);
//    	return Result.getResult(BaseResponseCode.SYSTEM_REDIS_BUSY);
//    }

    /**
     * 自定义全局异常处理
     */
    @ExceptionHandler(value = BusinessException.class)
    Result businessExceptionHandler(BusinessException e) {
        log.error("Exception,exception:{}", e, e);
        return new Result(e.getMessageCode(), e.getDetailMessage());
    }

    /**
     * 没有权限 返回403视图
     */
//    @ExceptionHandler(value = AuthorizationException.class)
//    public Result errorPermission(AuthorizationException e) {
//        log.error("Exception,exception:{}", e, e);
//        return new Result(BaseResponseCode.UNAUTHORIZED_ERROR);
//    }

    /**
     * 处理validation 框架异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return Result.getResult(BaseResponseCode.METHODARGUMENTNOTVALIDEXCEPTION.getCode(), errors.get(0).getDefaultMessage());
    }

    /**
     * 校验List<entity>类型， 需要controller添加@Validated注解
     * 处理Validated List<entity> 异常
     */
//    @ExceptionHandler
//    public Result handle(ConstraintViolationException exception) {
//        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", exception, exception);
//        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
//        StringBuilder builder = new StringBuilder();
//        for (ConstraintViolation<?> violation : violations) {
//            builder.append(violation.getMessage());
//            break;
//        }
//        return Result.getResult(BaseResponseCode.METHODARGUMENTNOTVALIDEXCEPTION.getCode(), builder.toString());
//    }

}
