package io.github.wujun728.online.vo.form;

/**
 * 表单配置详情VO类
 * 存储表单的具体配置参数
 */
public class WidgetFormConfigVO {
    /**
     * 表单尺寸
     * 可选值：small, medium, large
     */
    private String size;
    
    /**
     * 标签位置
     * 可选值：left, right, top
     */
    private String labelPosition;
    
    /**
     * 标签宽度
     * 单位：像素
     */
    private Integer labelWidth;
    
    /**
     * 表单样式
     * 以JSON字符串形式存储的样式配置
     */
    private String style;
    
    /**
     * 获取表单尺寸
     */
    public String getSize() {
        return size;
    }
    
    /**
     * 设置表单尺寸
     */
    public void setSize(String size) {
        this.size = size;
    }
    
    /**
     * 获取标签位置
     */
    public String getLabelPosition() {
        return labelPosition;
    }
    
    /**
     * 设置标签位置
     */
    public void setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
    }
    
    /**
     * 获取标签宽度
     */
    public Integer getLabelWidth() {
        return labelWidth;
    }
    
    /**
     * 设置标签宽度
     */
    public void setLabelWidth(Integer labelWidth) {
        this.labelWidth = labelWidth;
    }
    
    /**
     * 获取表单样式
     */
    public String getStyle() {
        return style;
    }
    
    /**
     * 设置表单样式
     */
    public void setStyle(String style) {
        this.style = style;
    }
}

