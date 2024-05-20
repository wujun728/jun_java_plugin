package com.jun.plugin.groovy.plugin;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class TransformPlugin implements BasePlugin  {

    public Logger logger = LoggerFactory.getLogger(CachePlugin.class);

    /**
     * 数据转换逻辑
     *
     * @param data   sql查询结果
     * @param params 缓存插件局部参数
     * @return
     */
    public abstract Object transform(List<JSONObject> data, String params);
}
