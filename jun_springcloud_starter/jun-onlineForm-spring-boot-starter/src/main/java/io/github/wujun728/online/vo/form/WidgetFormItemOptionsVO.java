package io.github.wujun728.online.vo.form;

import lombok.Data;

/**
 * 表单组件选项配置VO类
 * 用于存储表单组件的各种配置选项
 */
@Data
public class WidgetFormItemOptionsVO {
    private String defaultValue;
    private boolean allowHalf;
    private boolean clearable;
    private String options;
    private boolean multiple;
    private String placeholder;
    private boolean isRange;
    private Integer step;
    private boolean readonly;
    private boolean showPassword;
    private boolean filterable;
    private String controlsPosition;
    private Integer maxlength;
    private String startPlaceholder;
    private String endPlaceholder;
    private boolean showWordLimit;
    private String type;
    private Integer gutter;
    private Integer min;
    private String justify;
    private String rules;
    private String width;
    private Integer precision;
    private Integer rows;
    private Integer max;
    private boolean button;
    private String format;
    private String align;
    private boolean disabled;
    private boolean labelHide;
}

