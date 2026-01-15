package io.github.wujun728.online.vo.form;

import java.util.List;

import lombok.Data;

/**
 * 表单组件列配置VO类
 * 用于表示表单布局中的列配置信息
 */
@Data
public class WidgetFormItemColumnVO {
    private List<WidgetFormItemVO> list;
    private Integer number;
}

