package io.github.wujun728.online.vo.form;

import java.util.List;

import lombok.Data;

/**
 * 表单组件项VO类
 * 表示表单中的一个组件配置，包含组件名称、类型、标签、选项和子列等信息
 */
@Data
public class WidgetFormItemVO {
    private String name;
    private WidgetFormItemOptionsVO options;
    private List<WidgetFormItemColumnVO> columns;
    private String label;
    private String type;
}

