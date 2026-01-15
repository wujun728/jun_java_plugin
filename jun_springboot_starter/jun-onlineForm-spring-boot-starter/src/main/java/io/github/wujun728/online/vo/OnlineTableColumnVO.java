package io.github.wujun728.online.vo;

import lombok.Data;

/**
 * 在线表字段VO类
 * 用于表示在线表单中的表字段信息
 */
//@Schema(description="Online表单字段")
@Data
public class OnlineTableColumnVO {
    //@Schema(description="表单项")
    private boolean formItem;
    //@Schema(description="字段描述")
    private String comments;
    //@Schema(description="表单字典")
    private String formDict;
    //@Schema(description="表单控件默认值")
    private String formDefault;
    //@Schema(description="列表排序")
    private boolean gridSort;
    //@Schema(description="字段名称")
    private String name;
    //@Schema(description="小数点")
    private Integer pointLength;
    //@Schema(description="id")
    private Long id;
    //@Schema(description="列表项")
    private boolean gridItem;
    //@Schema(description="字段为空")
    private boolean columnNull;
    //@Schema(description="字段主键")
    private boolean columnPk;
    //@Schema(description="查询方式")
    private String queryType;
    //@Schema(description="字段长度")
    private Integer length;
    //@Schema(description="表单控件")
    private String formInput;
    //@Schema(description="查询控件")
    private String queryInput;
    //@Schema(description="查询项")
    private boolean queryItem;
    //@Schema(description="排序")
    private Integer sort;
    //@Schema(description="字段类型")
    private String columnType;
    //@Schema(description="表单必填")
    private boolean formRequired;
    //@Schema(description="默认值")
    private String defaultValue;
}

