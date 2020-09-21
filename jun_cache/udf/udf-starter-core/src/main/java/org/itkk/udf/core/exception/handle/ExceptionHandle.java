package org.itkk.udf.core.exception.handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.itkk.udf.core.ApplicationConfig;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.core.exception.*;
import org.itkk.udf.core.exception.alert.IExceptionAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * ExceptionHandle
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle extends ResponseEntityExceptionHandler {

    /**
     * 描述 : 系统配置
     */
    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * 描述 : objectMapper
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * exceptionCors
     */
    @Autowired
    private ExceptionCors exceptionCors;

    /**
     * httpServletResponse
     */
    @Autowired
    private HttpServletResponse httpServletResponse;

    /**
     * iExceptionAlert
     */
    @Autowired(required = false)
    private IExceptionAlert iExceptionAlert;

    /**
     * 异常处理
     *
     * @param ex      ex
     * @param request request
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
        //异常处理
        ResponseEntity<Object> objectResponseEntity = this.handleException(ex, request);
        return this.handleExceptionInternal(ex, null, objectResponseEntity.getHeaders(), objectResponseEntity.getStatusCode(), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //cors特殊处理
        exceptionCors.fixCors(httpServletResponse);
        //异常处理
        HttpStatus localHttpStatus = status;
        ErrorResult errorResult = buildError(applicationConfig, ex);
        if (ex instanceof PermissionException) { //权限异常
            localHttpStatus = HttpStatus.FORBIDDEN;
        } else if (ex instanceof AuthException) { //认证异常
            localHttpStatus = HttpStatus.FORBIDDEN;
        } else if (ex instanceof ParameterValidException) { //参数校验异常
            localHttpStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof RestClientResponseException) { //rest请求异常
            try {
                RestClientResponseException restClientResponseException = (RestClientResponseException) ex;
                String data = restClientResponseException.getResponseBodyAsString();
                if (StringUtils.isNotBlank(data)) {
                    RestResponse<String> child = objectMapper.readValue(data, objectMapper.getTypeFactory().constructParametricType(RestResponse.class, String.class));
                    errorResult.setChild(child);
                }
            } catch (IOException e) {
                throw new SystemRuntimeException(e);
            }
        } else if (ex instanceof MethodArgumentNotValidException) { //参数校验异常
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            try {
                errorResult.setMessage(objectMapper.writeValueAsString(methodArgumentNotValidException.getBindingResult().getAllErrors()));
            } catch (JsonProcessingException e) {
                throw new SystemRuntimeException(e);
            }
        }
        RestResponse<String> restResponse = new RestResponse<>(localHttpStatus, errorResult);
        //发出通知
        if (iExceptionAlert != null) {
            iExceptionAlert.alert(restResponse);
        }
        log.error(restResponse.getId(), ex);
        return super.handleExceptionInternal(ex, restResponse, headers, localHttpStatus, request);
    }

    /**
     * 描述 : 构造错误响应对象
     *
     * @param applicationConfig 系统配置
     * @param throwable         异常
     * @return 错误响应对象
     */
    public static ErrorResult buildError(ApplicationConfig applicationConfig, Throwable throwable) {
        ErrorResult error = new ErrorResult();
        error.setType(throwable.getClass().getName());
        error.setMessage(ExceptionUtils.getMessage(throwable));
        if (applicationConfig.isOutputExceptionStackTrace()) {
            error.setStackTrace(ExceptionUtils.getStackTrace(throwable));
        }
        error.setDate(new Date());
        return error;
    }
}
