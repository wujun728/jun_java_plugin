package com.zhaodui.springboot.common.controller;

import com.zhaodui.springboot.common.mdoel.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 全局异常处理
 *
 * @author Wujun
 */
@Slf4j
@ControllerAdvice
@RestController
public class ExceptionController {

    /**
     * 400异常
     *
     * @param e 异常信息
     * @return 响应对象
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数解析失败", e);
        return Result.error("参数解析失败" + e.getMessage());
    }

    /**
     * 405异常
     *
     * @param e 异常信息
     * @return 响应对象
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return Result.error("不支持当前请求方法" + e.getMessage());
    }

    /**
     * 415异常
     *
     * @param e 异常信息
     * @return 响应对象
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("不支持当前媒体类型", e);
        return Result.error("不支持当前媒体类型" + e.getMessage());
    }

    /**
     * 文件大小限制异常
     *
     * @param e 异常信息
     * @return 响应对象
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("文件大小不能超过1M", e);
        return Result.error("文件大小不能超过1M");
    }

    /**
     * Exception异常
     *
     * @param e 异常信息
     * @return 响应对象
     */
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage());
    }
}
