package io.github.wujun728.online.vo.form.component;

import cn.hutool.core.util.StrUtil;
import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.Map;

/**
 * 表单组件上下文类
 * 根据字段类型创建相应的表单组件
 */
public class ComponentContext {
    
    /**
     * 创建的表单组件实例
     */
    private AbstractComponent component;

    /**
     * 构造函数
     * 根据字段的表单输入类型创建对应的组件
     * @param column 表字段实体
     */
    public ComponentContext(OnlineTableColumnEntity column) {
        if (column == null) {
            throw new IllegalArgumentException("OnlineTableColumnEntity cannot be null");
        }
        
        String formInput = column.getFormInput();
        
        if (StrUtil.equalsIgnoreCase(formInput, "input")) {
            component = new InputComponent(column);
        } else if (StrUtil.equalsIgnoreCase(formInput, "number")) {
            component = new NumberComponent(column);
        } else if (StrUtil.equalsIgnoreCase(formInput, "checkbox")) {
            component = new CheckboxComponent(column);
        } else if (StrUtil.equalsIgnoreCase(formInput, "date")) {
            component = new DateComponent(column);
        } else if (StrUtil.equalsIgnoreCase(formInput, "datetime")) {
            component = new DateTimeComponent(column);
        } else if (StrUtil.equalsIgnoreCase(formInput, "radio")) {
            component = new RadioComponent(column);
        } else if (StrUtil.equalsIgnoreCase(formInput, "rate")) {
            component = new RateComponent(column);
        } else if (StrUtil.equalsIgnoreCase(formInput, "select")) {
            component = new SelectComponent(column);
        } else if (StrUtil.equalsIgnoreCase(formInput, "slider")) {
            component = new SliderComponent(column);
        } else if (StrUtil.equalsIgnoreCase(formInput, "switch")) {
            component = new SwitchComponent(column);
        } else if (StrUtil.equalsIgnoreCase(formInput, "textarea")) {
            component = new TextareaComponent(column);
        } else if (StrUtil.equalsIgnoreCase(formInput, "time")) {
            component = new TimeComponent(column);
        } else {
            // 默认使用输入框组件
            component = new InputComponent(column);
        }
    }

    /**
     * 获取组件配置信息
     * @return 组件配置Map
     */
    public Map<String, Object> getComponent() {
        if (component == null) {
            throw new IllegalStateException("Component has not been initialized");
        }
        return component.getComponent();
    }
}

