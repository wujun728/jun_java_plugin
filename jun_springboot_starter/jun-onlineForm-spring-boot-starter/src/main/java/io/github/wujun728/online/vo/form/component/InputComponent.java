package io.github.wujun728.online.vo.form.component;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * 输入框组件实现类
 * 用于生成表单中的输入框组件配置
 */
public class InputComponent extends AbstractComponent {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public InputComponent(OnlineTableColumnEntity column) {
        super(column);
    }

    /**
     * 获取输入框组件配置
     * @return 输入框组件配置Map
     */
    @Override
    public Map<String, Object> getComponent() {
        // 创建组件配置Map
        Map<String, Object> componentMap = new HashMap<>();
        
        // 设置组件类型为输入框
        componentMap.put("type", "input");
        
        // 设置组件属性
        Map<String, Object> componentProps = new HashMap<>();
        
        // 设置默认值
        componentProps.put("defaultValue", column.getFormDefault());
        
        // 设置组件名称
        componentProps.put("name", column.getName());
        
        // 设置组件标签（显示名称）
        componentProps.put("label", column.getComments());
        
        // 设置组件占位符
        componentProps.put("placeholder", "请输入" + column.getComments());
        
        // 设置验证规则
        componentProps.put("rules", getRules());
        
        // 关联组件属性
        componentMap.put("componentProps", componentProps);
        
        return componentMap;
    }
}

