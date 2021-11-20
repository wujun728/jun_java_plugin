package com.jun.plugin.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by admin on 2018/6/13.
 */
@Data
public class HttpResultParent<T> implements Serializable {

    private static final long serialVersionUID = -3404886040638951329L;

    protected T model;

    public HttpResultParent(){}

    public static <T> HttpResultParent<T> result(T t) {
        HttpResultParent<T> result = new HttpResultParent<T>();
        result.setModel(t);
        return result;
    }
}
