package com.battcn.boot.extend.configuration.mybatis.plugins;

import java.util.Map;

/**
 * @author Levin
 */
public interface PreparePluginContent {

    /**
     * 将字段赋值
     *
     * @return 赋值内容
     */
    Map<String, Object> process();

}
