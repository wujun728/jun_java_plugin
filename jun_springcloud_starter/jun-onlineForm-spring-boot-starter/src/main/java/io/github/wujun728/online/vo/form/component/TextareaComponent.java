package io.github.wujun728.online.vo.form.component;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * 文本域组件实现类
 * 用于生成表单中的多行文本输入组件配置
 */
public class TextareaComponent extends AbstractComponent {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public TextareaComponent(OnlineTableColumnEntity column) {
        super(column);
    }

    /**
     * 获取文本域组件配置
     * @return 文本域组件配置Map
     */
    @Override
    public Map<String, Object> getComponent() {
        // 创建组件配置Map
        Map<String, Object> componentMap = new HashMap<>();
        
        // 设置组件类型为文本域
        componentMap.put("type", "textarea");
        
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
        
        // 设置占位符
        componentProps.put("placeholder", "请输入" + column.getComments());
        
        // 设置文本域行数（默认3行）
        componentProps.put("rows", 3);
        
        // 设置是否自动调整高度
        componentProps.put("autoSize", false);
        
        // 设置最大长度（默认1000字符）
        componentProps.put("maxLength", 1000);
        
        // 设置是否显示字数统计
        componentProps.put("showCount", true);
        
        // 设置是否可清空
        componentProps.put("allowClear", true);
        
        // 设置验证规则
        componentProps.put("rules", getRules());
        
        // 关联组件属性
        componentMap.put("componentProps", componentProps);
        
        return componentMap;
    }
}

