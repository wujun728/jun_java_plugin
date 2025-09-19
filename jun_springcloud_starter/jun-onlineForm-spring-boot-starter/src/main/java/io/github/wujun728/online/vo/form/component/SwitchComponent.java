package io.github.wujun728.online.vo.form.component;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * 开关组件实现类
 * 用于生成表单中的开关组件配置
 */
public class SwitchComponent extends AbstractComponent {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public SwitchComponent(OnlineTableColumnEntity column) {
        super(column);
    }

    /**
     * 获取开关组件配置
     * @return 开关组件配置Map
     */
    @Override
    public Map<String, Object> getComponent() {
        // 创建组件配置Map
        Map<String, Object> componentMap = new HashMap<>();
        
        // 设置组件类型为开关
        componentMap.put("type", "switch");
        
        // 设置组件属性
        Map<String, Object> componentProps = new HashMap<>();
        
        // 设置组件名称
        componentProps.put("name", column.getName());
        
        // 设置组件标签（显示名称）
        componentProps.put("label", column.getComments());
        
        // 设置默认值
        if (column.getFormDefault() != null && !column.getFormDefault().isEmpty()) {
            componentProps.put("defaultValue", "true".equalsIgnoreCase(column.getFormDefault()) || "1".equals(column.getFormDefault()));
        } else {
            componentProps.put("defaultValue", false);
        }
        
        // 设置开启和关闭的值
        componentProps.put("checkedValue", true);
        componentProps.put("uncheckedValue", false);
        
        // 设置开关尺寸（'small', 'default', 'large'）
        componentProps.put("size", "default");
        
        // 设置开启时的背景颜色
        componentProps.put("checkedColor", "#1890ff");
        
        // 设置验证规则
        componentProps.put("rules", getRules());
        
        // 关联组件属性
        componentMap.put("componentProps", componentProps);
        
        return componentMap;
    }
}

