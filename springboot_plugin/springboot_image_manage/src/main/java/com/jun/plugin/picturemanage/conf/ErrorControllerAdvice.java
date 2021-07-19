package com.jun.plugin.picturemanage.conf;

import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jun.plugin.picturemanage.util.JsonResult;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/11/4 11:36
 */
@RestControllerAdvice
@Log4j2
public class ErrorControllerAdvice {

    @ExceptionHandler(FileUploadBase.SizeLimitExceededException.class)
    public JsonResult uploadSizeMax(Exception e) {
        log.error("上传文件过大:", e);
        return JsonResult.error("上传文件不能超过1024MB,建议上传到第三方云盘");
    }

    @ExceptionHandler(CustomException.class)
    public JsonResult customException(CustomException e) {
        log.error("抛出自定义CustomException:", e);
        return JsonResult.error(e.getMessage(), e.getCode());
    }

}
