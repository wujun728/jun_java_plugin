package io.github.wujun728.online.vo.query;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.Map;

/**
 * 查询组件上下文类
 * 根据字段类型创建对应的查询组件实例
 */
public class QueryContext {

    // 查询组件实例
    private AbstractQuery query;

    /**
     * 构造函数
     * 根据字段的查询类型创建对应的查询组件
     * @param column 表字段实体
     */
    public QueryContext(OnlineTableColumnEntity column) {
        // 根据字段的查询输入类型创建对应的查询组件
        String queryInput = column.getQueryInput();
        
        if ("input".equals(queryInput)) {
            // 文本输入框查询组件
            query = new InputQuery(column);
        } else if ("select".equals(queryInput)) {
            // 下拉选择查询组件
            query = new SelectQuery(column);
        } else if ("date".equals(queryInput)) {
            // 日期选择查询组件
            query = new DateQuery(column);
        } else if ("datetime".equals(queryInput)) {
            // 日期时间查询组件
            query = new DateTimeQuery(column);
        } else {
            // 默认使用输入框查询组件
            query = new InputQuery(column);
        }
    }

    /**
     * 获取查询组件配置
     * @return 查询组件配置Map
     */
    public Map<String, Object> getQuery() {
        return query.getQuery();
    }
}

