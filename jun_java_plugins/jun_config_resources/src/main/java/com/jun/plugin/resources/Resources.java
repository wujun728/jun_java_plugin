package com.jun.plugin.resources;

import java.util.Map;

import com.jun.plugin.resources.config.Config;

/**
 * 资源接口
 * Created by Hong on 2017/12/26.
 */
public interface Resources extends Config {

    /**
     * 读取所有资源
     * @return key,value
     */
    Map<Object, Object> getResources();

    /**
     * 将资源写入本地属性中
     */
    void writeLocalProperties();
}