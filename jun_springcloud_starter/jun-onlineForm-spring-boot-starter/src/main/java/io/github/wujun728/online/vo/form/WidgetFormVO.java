package io.github.wujun728.online.vo.form;

import java.util.List;
import java.util.Map;

/**
 * 表单组件配置VO类
 * 存储表单的配置信息和组件列表
 */
public class WidgetFormVO {
    /**
     * 表单组件列表
     * 存储表单中的各个组件配置
     */
    private List<Map<String, Object>> list;
    
    /**
     * 表单配置信息
     */
    private WidgetFormConfigVO config;
    
    /**
     * 获取表单组件列表
     */
    public List<Map<String, Object>> getList() {
        return list;
    }
    
    /**
     * 设置表单组件列表
     */
    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }
    
    /**
     * 获取表单配置信息
     */
    public WidgetFormConfigVO getConfig() {
        return config;
    }
    
    /**
     * 设置表单配置信息
     */
    public void setConfig(WidgetFormConfigVO config) {
        this.config = config;
    }
}

