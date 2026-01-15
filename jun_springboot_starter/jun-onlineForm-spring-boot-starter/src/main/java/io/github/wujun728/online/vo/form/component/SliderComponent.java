package io.github.wujun728.online.vo.form.component;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * 滑块组件实现类
 * 用于生成表单中的滑块组件配置
 */
public class SliderComponent extends AbstractComponent {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public SliderComponent(OnlineTableColumnEntity column) {
        super(column);
    }

    /**
     * 获取滑块组件配置
     * @return 滑块组件配置Map
     */
    @Override
    public Map<String, Object> getComponent() {
        // 创建组件配置Map
        Map<String, Object> componentMap = new HashMap<>();
        
        // 设置组件类型为滑块
        componentMap.put("type", "slider");
        
        // 设置组件属性
        Map<String, Object> componentProps = new HashMap<>();
        
        // 设置组件名称
        componentProps.put("name", column.getName());
        
        // 设置组件标签（显示名称）
        componentProps.put("label", column.getComments());
        
        // 设置默认值
        if (column.getFormDefault() != null && !column.getFormDefault().isEmpty()) {
            try {
                componentProps.put("defaultValue", Integer.parseInt(column.getFormDefault()));
            } catch (NumberFormatException e) {
                // 如果默认值无法转换为整数，则使用0作为默认值
                componentProps.put("defaultValue", 0);
            }
        } else {
            componentProps.put("defaultValue", 0);
        }
        
        // 设置滑块最小值（默认为0）
        componentProps.put("min", 0);
        
        // 设置滑块最大值（默认为100）
        componentProps.put("max", 100);
        
        // 设置滑块步长（默认为1）
        componentProps.put("step", 1);
        
        // 设置是否显示数字输入框（可选）
        componentProps.put("showInput", true);
        
        // 设置是否显示标记
        componentProps.put("marks", true);
        
        // 设置是否显示提示
        componentProps.put("tooltipVisible", true);
        
        // 设置验证规则
        componentProps.put("rules", getRules());
        
        // 关联组件属性
        componentMap.put("componentProps", componentProps);
        
        return componentMap;
    }
}

