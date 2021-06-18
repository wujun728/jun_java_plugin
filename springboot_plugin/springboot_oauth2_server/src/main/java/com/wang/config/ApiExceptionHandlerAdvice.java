package com.wang.config;

import com.google.common.base.Throwables;
import com.wang.utils.ResultJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by wangxiangyun on 2017/1/24.
 */
@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandlerAdvice {
    Logger logger = LoggerFactory.getLogger(ApiExceptionHandlerAdvice.class);
    /**
     * Handle exceptions thrown by handlers.
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResultJson exception(Exception exception, WebRequest request) {
        ResultJson errorResult =new ResultJson();
        errorResult.setMessage(Throwables.getRootCause(exception).getMessage());
        errorResult.setCode(-1);
        logger.error("GlobalException====",exception);
        return errorResult;
    }
}
