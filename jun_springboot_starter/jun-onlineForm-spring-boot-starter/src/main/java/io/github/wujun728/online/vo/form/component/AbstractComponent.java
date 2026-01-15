package io.github.wujun728.online.vo.form.component;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单组件抽象基类
 * 定义所有表单组件的共同行为和属性
 */
public abstract class AbstractComponent {
    
    /**
     * 表字段实体
     */
    protected OnlineTableColumnEntity column;
    
    /**
     * 构造函数
     * @param column 表字段实体
     */
    public AbstractComponent(OnlineTableColumnEntity column) {
        this.column = column;
    }
    
    /**
     * 获取组件配置信息
     * @return 组件配置Map
     */
    public abstract Map<String, Object> getComponent();
    
    /**
     * 获取表单验证规则
     * @return 验证规则列表
     */
    protected List<Map<String, Object>> getRules() {
        List<Map<String, Object>> rules = new ArrayList<>();
        
        // 如果字段为必填，则添加必填验证规则
        if (column.isFormRequired()) {
            Map<String, Object> requiredRule = new HashMap<>();
            requiredRule.put("required", true);
            requiredRule.put("message", column.getComments() + "不能为空");
            rules.add(requiredRule);
        }
        
        return rules;
    }
}

