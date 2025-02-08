package io.github.wujun728.rest.plugin.impl;

import java.util.Map;

import io.github.wujun728.rest.plugin.CachePlugin;
import io.swagger.annotations.Api;

public class DemoCachePlugin extends CachePlugin {
    @Override
    public void init() {
        System.out.println("-----demo---------");
    }

    @Override
    public void set(Api config, Map<String, Object> requestParams, Object data) {
        System.out.println("--------------");
    }

    @Override
    public void clean(Api config) {
        System.out.println("--------------");
    }

    @Override
    public Object get(Api config, Map<String, Object> requestParams) {
        return null;
    }

    @Override
    public String getName() {
        return "demo缓存插件";
    }

    @Override
    public String getDescription() {
        return "demo缓存插件描述";
    }

    @Override
    public String getParamDescription() {
        return "demo缓存插件参数";
    }
}
