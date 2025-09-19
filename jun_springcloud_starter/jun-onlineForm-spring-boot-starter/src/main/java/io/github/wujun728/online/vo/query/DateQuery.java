package io.github.wujun728.online.vo.query;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期查询组件
 * 用于生成日期类型字段的查询配置
 */
public class DateQuery extends AbstractQuery {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public DateQuery(OnlineTableColumnEntity column) {
        this.column = column;
    }

    /**
     * 获取日期查询组件配置
     * @return 查询组件配置Map
     */
    @Override
    public Map<String, Object> getQuery() {
        // 创建组件配置Map
        Map<String, Object> componentMap = new HashMap<>();
        
        // 设置默认值
        componentMap.put("defaultValue", column.getFormDefault());
        
        // 设置组件类型为日期选择器
        componentMap.put("component", "date-picker");
        
        // 设置组件大小
        componentMap.put("size", 1);
        
        // 根据查询类型设置不同的配置
        if ("eq".equals(column.getQueryType())) {
            // 等于查询
            componentMap.put("type", "default");
            componentMap.put("label", column.getComments());
        } else {
            // 范围查询
            componentMap.put("type", "date-range");
            componentMap.put("label", column.getComments() + " | 开始");
            componentMap.put("endLabel", column.getComments() + " | 结束");
        }
        
        // 创建查询配置Map
        Map<String, Object> queryMap = new HashMap<>();
        
        // 设置查询控件类型
        queryMap.put("queryType", column.getQueryInput());
        
        // 设置字段名称
        queryMap.put("name", column.getName());
        
        // 关联组件配置
        queryMap.put("config", componentMap);
        
        return queryMap;
    }
}

