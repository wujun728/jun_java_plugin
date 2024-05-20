package com.jun.plugin.groovy.plugin.impl;

import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.jun.plugin.groovy.plugin.TransformPlugin;

public class DemoTransformPlugin extends TransformPlugin {
    @Override
    public void init() {
        System.out.println("------demo transform------");
    }

    @Override
    public Object transform(List<JSONObject> data, String params) {
        return null;
    }

    @Override
    public String getName() {
        return "demo数据转换插件";
    }

    @Override
    public String getDescription() {
        return "demo数据转换插件描述";
    }

    @Override
    public String getParamDescription() {
        return "demo数据转换参数插件";
    }
}
