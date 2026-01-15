package io.github.wujun728.online.vo.form;

import java.util.List;
import java.util.Map;

/**
 * 在线表单完整配置VO类
 * 包含表单、查询和表格的完整配置信息
 */
public class OnlineFormVO {
    /**
     * 表单配置
     * 包含表单的布局和字段组件配置
     */
    private WidgetFormVO form;
    
    /**
     * 查询表单配置
     * 包含查询条件的组件配置
     */
    private WidgetFormVO query;
    
    /**
     * 表格配置
     * 包含表格列的配置信息
     */
    private List<Map<String, Object>> table;
    
    /**
     * 获取表单配置
     */
    public WidgetFormVO getForm() {
        return form;
    }
    
    /**
     * 设置表单配置
     */
    public void setForm(WidgetFormVO form) {
        this.form = form;
    }
    
    /**
     * 获取查询表单配置
     */
    public WidgetFormVO getQuery() {
        return query;
    }
    
    /**
     * 设置查询表单配置
     */
    public void setQuery(WidgetFormVO query) {
        this.query = query;
    }
    
    /**
     * 获取表格配置
     */
    public List<Map<String, Object>> getTable() {
        return table;
    }
    
    /**
     * 设置表格配置
     */
    public void setTable(List<Map<String, Object>> table) {
        this.table = table;
    }
}

