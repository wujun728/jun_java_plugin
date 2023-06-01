package com.doumuxie.config;

import com.doumuxie.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author liangliang
 * @version 1.0
 * @date 2019/5/28 11:17
 * @description GlobalExceptionHandler 全局异常处理
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 捕获全局异常
     *
     * @param e 异常信息
     * @return 返回统一格式
     */
    @ExceptionHandler(Exception.class)
    public ResultUtil exceptionHandler(Exception e) {
        logger.error(e.getMessage());
        return ResultUtil.error("服务器繁忙!");
    }


    /**
     * 捕获 文件大小超出限制异常
     *
     * @param e 异常信息
     * @return 返回统一格式
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResultUtil exceptionHandler(MaxUploadSizeExceededException e) {
        logger.error("文件过大，请传入不大于10M的文件。");
        return ResultUtil.error("文件过大，请传入不大于10M的文件。");
    }

    /**
     * 捕获 请求方式不正确
     *
     * @param e 异常信息
     * @return 返回统一格式
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultUtil exceptionHandler(HttpRequestMethodNotSupportedException e) {
        logger.error("错误的请求方式");
        return ResultUtil.error("错误的请求方式");
    }
}
