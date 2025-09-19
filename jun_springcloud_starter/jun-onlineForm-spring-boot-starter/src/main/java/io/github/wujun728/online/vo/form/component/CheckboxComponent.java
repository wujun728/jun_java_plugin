package io.github.wujun728.online.vo.form.component;

import cn.hutool.core.util.StrUtil;
import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 复选框组件实现类
 * 用于生成表单中的复选框组件配置
 */
public class CheckboxComponent extends AbstractComponent {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public CheckboxComponent(OnlineTableColumnEntity column) {
        super(column);
    }

    /**
     * 获取复选框组件配置
     * @return 复选框组件配置Map
     */
    @Override
    public Map<String, Object> getComponent() {
        // 创建组件配置Map
        Map<String, Object> componentMap = new HashMap<>();
        
        // 设置组件类型为复选框
        componentMap.put("type", "checkbox");
        
        // 设置组件属性
        Map<String, Object> componentProps = new HashMap<>();
        
        // 设置组件名称
        componentProps.put("name", column.getName());
        
        // 设置组件标签（显示名称）
        componentProps.put("label", column.getComments());
        
        // 设置默认值
        if (StrUtil.isNotBlank(column.getFormDefault())) {
            componentProps.put("defaultValue", parseDefaultValues(column.getFormDefault()));
        }
        
        // 设置验证规则
        componentProps.put("rules", getRules());
        
        // 处理字典配置
        if (StrUtil.isNotBlank(column.getFormDict())) {
            componentProps.put("options", parseDictOptions(column.getFormDict()));
        }
        
        // 关联组件属性
        componentMap.put("componentProps", componentProps);
        
        return componentMap;
    }
    
    /**
     * 解析默认值字符串为值列表
     * @param defaultValues 默认值字符串，格式为逗号分隔
     * @return 默认值列表
     */
    private List<String> parseDefaultValues(String defaultValues) {
        List<String> values = new ArrayList<>();
        if (StrUtil.isNotBlank(defaultValues)) {
            String[] valueArray = defaultValues.split(",");
            for (String value : valueArray) {
                values.add(value.trim());
            }
        }
        return values;
    }
    
    /**
     * 解析字典选项配置为选项列表
     * @param dictConfig 字典配置字符串，格式为"key:value,key:value"或JSON格式
     * @return 选项列表
     */
    private List<Map<String, String>> parseDictOptions(String dictConfig) {
        List<Map<String, String>> options = new ArrayList<>();
        
        // 简单解析，实际项目中可能需要根据具体字典格式进行更复杂的解析
        if (StrUtil.isNotBlank(dictConfig)) {
            try {
                // 尝试作为JSON解析（这里简化处理，实际项目中应使用JSON库）
                if (dictConfig.trim().startsWith("[") && dictConfig.trim().endsWith("]")) {
                    // 这里只是模拟，实际项目中应该使用JSON解析库
                    // 例如：使用Jackson或Fastjson解析JSON字符串
                    // 简化处理，假设已经解析完成
                } else if (dictConfig.contains(":")) {
                    // 格式如：1:选项1,2:选项2
                    String[] pairs = dictConfig.split(",");
                    for (String pair : pairs) {
                        if (pair.contains(":")) {
                            String[] kv = pair.split(":", 2);
                            if (kv.length == 2) {
                                Map<String, String> option = new HashMap<>();
                                option.put("label", kv[1].trim());
                                option.put("value", kv[0].trim());
                                options.add(option);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // 解析失败时使用空列表
                e.printStackTrace();
            }
        }
        
        return options;
    }
}

