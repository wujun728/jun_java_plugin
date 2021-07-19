package com.buxiaoxia.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleInvalidRequestError(InvalidRequestException ex) {
        ex.printStackTrace();
        return "{\"message\":\"" + ex.getMessage() + "\"}";
    }

    @ExceptionHandler(DataBindingException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object handleBusinessException(DataBindingException ex) {
        ex.printStackTrace();
        return "{\"message\":\"" + ex.getMessage() + "\"}";
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleBusinessError(BusinessException ex) {
        return "{\"message\":\"" + ex.getMessage() + "\"}";
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleUnexpectedServerError(RuntimeException ex) {
        ex.printStackTrace();
        return "{\"message\":\"" + ex.getMessage() + "\"}";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleResourceNotFoundExceptionError(RuntimeException ex) {
        ex.printStackTrace();
        return "{\"message\":\"" + ex.getMessage() + "\"}";
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleResourceForbiddenExceptionError(RuntimeException ex) {
        ex.printStackTrace();
        return "{\"message\":\"" + ex.getMessage() + "\"}";
    }

}  