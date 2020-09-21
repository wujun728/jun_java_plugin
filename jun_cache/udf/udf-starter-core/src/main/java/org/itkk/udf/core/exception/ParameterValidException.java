/**
 * ParameterValidException.java
 * Created at 2016-09-27
 * Created by wangkang
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.core.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 描述 : 参数校验错误
 *
 * @author wangkang
 */
public class ParameterValidException extends RuntimeException {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 参数静态校验错误信息
     */
    private final List<ObjectError> allErrors;

    /**
     * 描述 : 构造函数
     *
     * @param message   异常信息
     * @param allErrors 参数静态校验错误信息
     */
    public ParameterValidException(String message, List<ObjectError> allErrors) {
        super(message);
        this.allErrors = allErrors;
    }

    /**
     * 描述 : 构造函数
     *
     * @param cause     堆栈
     * @param allErrors 参数静态校验错误信息
     */
    public ParameterValidException(Throwable cause, List<ObjectError> allErrors) {
        super(cause);
        this.allErrors = allErrors;
    }

    /**
     * 描述 : 构造函数
     *
     * @param message   异常信息
     * @param cause     堆栈
     * @param allErrors 参数静态校验错误信息
     */
    public ParameterValidException(String message, Throwable cause, List<ObjectError> allErrors) {
        super(message, cause);
        this.allErrors = allErrors;
    }

    /**
     * 描述 : 获取allErrors
     *
     * @return the allErrors
     */
    public List<ObjectError> getAllErrors() {
        return allErrors;
    }

}
