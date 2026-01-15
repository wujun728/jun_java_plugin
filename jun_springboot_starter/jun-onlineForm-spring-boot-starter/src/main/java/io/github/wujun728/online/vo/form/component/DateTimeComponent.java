package io.github.wujun728.online.vo.form.component;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期时间选择器组件实现类
 * 用于生成表单中的日期时间选择器组件配置
 */
public class DateTimeComponent extends AbstractComponent {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public DateTimeComponent(OnlineTableColumnEntity column) {
        super(column);
    }

    /**
     * 获取日期时间选择器组件配置
     * @return 日期时间选择器组件配置Map
     */
    @Override
    public Map<String, Object> getComponent() {
        // 创建组件配置Map
        Map<String, Object> componentMap = new HashMap<>();
        
        // 设置组件类型为日期时间选择器
        componentMap.put("type", "dateTimePicker");
        
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
        
        // 设置日期时间格式
        componentProps.put("format", "YYYY-MM-DD HH:mm:ss");
        
        // 设置日期时间值格式
        componentProps.put("valueFormat", "YYYY-MM-DD HH:mm:ss");
        
        // 设置占位符
        componentProps.put("placeholder", "请选择" + column.getComments());
        
        // 设置验证规则
        componentProps.put("rules", getRules());
        
        // 关联组件属性
        componentMap.put("componentProps", componentProps);
        
        return componentMap;
    }
}

