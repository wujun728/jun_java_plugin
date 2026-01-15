package io.github.wujun728.online.query;

import lombok.Data;
import io.github.wujun728.online.common.Query;

/**
 * 在线表查询参数类
 * 用于查询在线表单的基本信息
 */
@Data
public class OnlineTableQuery extends Query {
    /**
     * 表单名称
     */
    private String name;
    
    /**
     * 表单描述
     */
    private String comments;
}

