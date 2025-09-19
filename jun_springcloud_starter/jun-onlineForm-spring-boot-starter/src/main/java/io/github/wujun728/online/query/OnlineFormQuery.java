package io.github.wujun728.online.query;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 在线表单数据查询参数类
 * 用于分页查询表单数据
 */
@Data
public class OnlineFormQuery {
    /**
     * 当前页码
     */
    private Integer page;
    
    /**
     * 每页数量
     */
    private Integer limit;
    
    /**
     * 查询参数
     * 存储表单字段名和对应的值
     */
    private Map<String, Object> params;
    
    /**
     * 构造函数
     * 初始化参数Map
     */
    public OnlineFormQuery() {
        this.params = new HashMap<>();
    }
}

