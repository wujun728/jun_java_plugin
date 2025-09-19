package io.github.wujun728.online.vo.query;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * 输入框查询组件
 * 用于生成文本输入类型字段的查询配置
 */
public class InputQuery extends AbstractQuery {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public InputQuery(OnlineTableColumnEntity column) {
        this.column = column;
    }

    /**
     * 获取输入框查询组件配置
     * @return 查询组件配置Map
     */
    @Override
    public Map<String, Object> getQuery() {
        // 创建组件属性Map
        Map<String, String> componentProps = new HashMap<>();
        
        // 设置组件标签（显示名称）
        componentProps.put("label", column.getComments());
        
        // 创建查询配置Map
        Map<String, Object> queryMap = new HashMap<>();
        
        // 设置查询控件类型
        queryMap.put("queryType", "input");
        
        // 设置字段名称
        queryMap.put("name", column.getName());
        
        // 关联组件属性
        queryMap.put("config", componentProps);
        
        return queryMap;
    }
}

