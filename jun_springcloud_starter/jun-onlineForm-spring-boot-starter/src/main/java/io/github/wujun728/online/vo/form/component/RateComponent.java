package io.github.wujun728.online.vo.form.component;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * 评分组件实现类
 * 用于生成表单中的评分组件配置
 */
public class RateComponent extends AbstractComponent {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public RateComponent(OnlineTableColumnEntity column) {
        super(column);
    }

    /**
     * 获取评分组件配置
     * @return 评分组件配置Map
     */
    @Override
    public Map<String, Object> getComponent() {
        // 创建组件配置Map
        Map<String, Object> componentMap = new HashMap<>();
        
        // 设置组件类型为评分组件
        componentMap.put("type", "rate");
        
        // 设置组件属性
        Map<String, Object> componentProps = new HashMap<>();
        
        // 设置组件名称
        componentProps.put("name", column.getName());
        
        // 设置组件标签（显示名称）
        componentProps.put("label", column.getComments());
        
        // 设置默认值
        if (column.getFormDefault() != null && !column.getFormDefault().isEmpty()) {
            componentProps.put("defaultValue", column.getFormDefault());
        }
        
        // 设置最大评分值
        componentProps.put("max", 5);
        
        // 设置验证规则
        componentProps.put("rules", getRules());
        
        // 关联组件属性
        componentMap.put("componentProps", componentProps);
        
        return componentMap;
    }
}

