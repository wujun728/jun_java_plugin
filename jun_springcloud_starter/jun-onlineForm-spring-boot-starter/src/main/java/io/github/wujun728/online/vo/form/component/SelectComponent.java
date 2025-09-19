package io.github.wujun728.online.vo.form.component;

import cn.hutool.core.util.StrUtil;
import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下拉选择框组件实现类
 * 用于生成表单中的下拉选择组件配置
 */
public class SelectComponent extends AbstractComponent {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public SelectComponent(OnlineTableColumnEntity column) {
        super(column);
    }

    /**
     * 获取下拉选择组件配置
     * @return 下拉选择组件配置Map
     */
    @Override
    public Map<String, Object> getComponent() {
        // 创建组件配置Map
        Map<String, Object> componentMap = new HashMap<>();
        
        // 设置组件类型为下拉选择框
        componentMap.put("type", "select");
        
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
        componentProps.put("placeholder", "请选择" + column.getComments());
        
        // 设置验证规则
        componentProps.put("rules", getRules());
        
        // 处理字典选项
        List<Map<String, String>> options = new ArrayList<>();
        
        // 检查是否有字典数据
        if (column.getFormDict() != null && !column.getFormDict().isEmpty()) {
            // 解析字典数据并添加到选项列表中
            options = parseDictOptions(column.getFormDict());
            
            // 设置远程搜索为false（本地选项）
            componentProps.put("showSearch", false);
        } else {
            // 如果没有字典数据，设置远程搜索为true
            componentProps.put("showSearch", true);
        }
        
        // 设置选项列表
        componentProps.put("options", options);
        
        // 设置值键和标签键
        Map<String, String> fieldNames = new HashMap<>();
        fieldNames.put("value", "value");
        fieldNames.put("label", "label");
        componentProps.put("fieldNames", fieldNames);
        
        // 关联组件属性
        componentMap.put("componentProps", componentProps);
        
        return componentMap;
    }
    
    /**
     * 解析字典选项
     * @param formDict 表单字典字符串
     * @return 解析后的选项列表
     */
    private List<Map<String, String>> parseDictOptions(String formDict) {
        List<Map<String, String>> options = new ArrayList<>();
        
        // 检查字典数据是否为空
        if (StrUtil.isBlank(formDict)) {
            return options;
        }
        
        try {
            // 按逗号分割字典项
            String[] dictItems = formDict.split(",");
            
            for (String item : dictItems) {
                // 按冒号分割键值对
                String[] keyValue = item.split(":");
                
                if (keyValue.length >= 2) {
                    Map<String, String> option = new HashMap<>();
                    option.put("value", keyValue[0].trim());
                    option.put("label", keyValue[1].trim());
                    options.add(option);
                }
            }
        } catch (Exception e) {
            // 解析异常时返回空列表
            e.printStackTrace();
        }
        
        return options;
    }
}

