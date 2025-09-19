package io.github.wujun728.online.vo.form.component;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.HashMap;
import java.util.Map;
import cn.hutool.core.util.StrUtil;

/**
 * 单选框组件实现类
 * 用于生成在线表单中的单选框控件配置
 */
public class RadioComponent extends AbstractComponent {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public RadioComponent(OnlineTableColumnEntity column) {
        super(column);
    }

    /**
     * 获取单选框组件配置
     * @return 组件配置Map
     */
    @Override
    public Map<String, Object> getComponent() {
        // 组件配置
        Map<String, Object> componentConfig = new HashMap<>();
        componentConfig.put("defaultValue", column.getFormDefault());
        componentConfig.put("placeholder", "请选择");
        componentConfig.put("multiple", false);
        componentConfig.put("clearable", false);
        componentConfig.put("filterable", false);
        componentConfig.put("remote", false);
        componentConfig.put("dictCode", column.getFormDict());
        componentConfig.put("rules", getRules());
        
        // 设置字典类型
        if (StrUtil.isBlank(column.getFormDict())) {
            componentConfig.put("dictType", "static");
        } else {
            componentConfig.put("dictType", "dict");
        }
        
        // 设置样式
        Map<String, String> styleConfig = new HashMap<>();
        styleConfig.put("width", "100%");
        styleConfig.put("height", "32px");
        componentConfig.put("style", styleConfig);
        
        // 组件基本信息
        Map<String, Object> componentInfo = new HashMap<>();
        componentInfo.put("component", column.getFormInput());
        componentInfo.put("label", column.getComments());
        componentInfo.put("prop", column.getName());
        componentInfo.put("config", componentConfig);
        
        return componentInfo;
    }
}

