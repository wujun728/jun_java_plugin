package com.mall.admin.exception;

import com.mall.common.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/**
 * 捕获系统异常
 * @author Wujun
 * @version 1.0
 * @create_at 2018/11/2513:28
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public Map resolveException(Exception ex) {
        Map result = new HashMap<>();
        if (ex instanceof AuthException) {
            result.put("code", ResultCode.UN_AUTH_ERROR_CODE);
            result.put("message", "用户未认证");
        } else if (ex instanceof BusinessException) {
            BusinessException exception = (BusinessException)ex;
            result.put("code", exception.getResultCode());
            result.put("message", exception.getResultCode().getMessage());
        } else if (ex instanceof UnauthorizedException) {
            result.put("code", ResultCode.FAIL);
            result.put("message", "权限不足,无法访问");
        } else {
            result.put("code", ResultCode.FAIL);
            result.put("message", "操作异常");
        }
        log.error("操作异常", ex);
        return result;
    }
}
