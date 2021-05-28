package com.demo.weixin.response.handlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;


public class BasicWeixinResponseHandler<T> extends AbstractWeixinResponseHandler<T>{

    /**
     * 泛型类型，在运行时不能保存，采用成员变量
     */
    private Class<T> type;

    private Feature features;

    public BasicWeixinResponseHandler(Class<T> type) {
        this.type = type;
    }

    public BasicWeixinResponseHandler(Class<T> type, Feature features) {
        this.type = type;
        this.features = features;
    }

    @Override
    protected T getResponseObject(String responseStr) {
        T responseObject = null;
        if (features != null) {
            responseObject = JSON.parseObject(responseStr, type, features);
        } else {
            responseObject = JSON.parseObject(responseStr, type);
        }

        return responseObject;
    }

}
