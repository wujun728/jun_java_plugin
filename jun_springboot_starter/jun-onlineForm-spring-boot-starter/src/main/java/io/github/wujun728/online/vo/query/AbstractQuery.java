package io.github.wujun728.online.vo.query;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.Map;

/**
 * 查询组件抽象基类
 * 所有具体的查询组件都需要继承此类并实现getQuery()方法
 */
public abstract class AbstractQuery {

    // 表字段实体
    public OnlineTableColumnEntity column;

    /**
     * 获取查询组件配置
     * 具体查询组件需要实现此方法来返回各自的查询配置
     * @return 查询组件配置Map
     */
    public abstract Map<String, Object> getQuery();

    /**
     * 构造函数
     */
    public AbstractQuery() {
        // 默认构造函数
    }
}

