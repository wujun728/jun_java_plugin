package com.jun.plugin.groovy.plugin;

public interface BasePlugin {

    /**
     * 插件名称，用于在页面上显示，提示用户
     * @return
     */
    String getName();

    /**
     * 插件功能描述，用于在页面上显示，提示用户
     * @return
     */
    String getDescription();

    /**
     * 插件参数描述，用于在页面上显示，提示用户
     * @return
     */
    String getParamDescription();
    
    /**
     * 插件初始化方法，实例化插件的时候执行，永远只会执行一次，
     * 一般是用来创建连接池
     */
    void init();
}
