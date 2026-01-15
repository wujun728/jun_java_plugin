package io.github.wujun728.online.vo.form.component;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * 数字输入框组件实现类
 * 用于生成表单中的数字输入组件配置
 */
public class NumberComponent extends AbstractComponent {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public NumberComponent(OnlineTableColumnEntity column) {
        super(column);
    }

    /**
     * 获取数字输入组件配置
     * @return 数字输入组件配置Map
     */
    @Override
    public Map<String, Object> getComponent() {
        // 创建组件配置Map
        Map<String, Object> componentMap = new HashMap<>();
        
        // 设置组件类型为数字输入框
        componentMap.put("type", "number");
        
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
        
        // 设置最小值（默认0）
        componentProps.put("min", 0);
        
        // 设置最大值（默认99999）
        componentProps.put("max", 99999);
        
        // 设置步长
        componentProps.put("step", 1);
        
        // 设置占位符
        componentProps.put("placeholder", "请输入" + column.getComments());
        
        // 设置验证规则
        componentProps.put("rules", getRules());
        
        // 关联组件属性
        componentMap.put("componentProps", componentProps);
        
        return componentMap;
    }
}

