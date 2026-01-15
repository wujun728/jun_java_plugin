package io.github.wujun728.online.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 在线表字段实体类
 * 用于表示在线表单表结构中的字段信息
 */
@Data
@TableName("online_table_column")
public class OnlineTableColumnEntity {
    /**
     * 字段ID
     */
    @TableId
    private Long id;
    
    /**
     * 字段名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 字段描述
     */
    @TableField("comments")
    private String comments;
    
    /**
     * 字段长度
     */
    @TableField("length")
    private Integer length;
    
    /**
     * 小数点位数
     */
    @TableField("point_length")
    private Integer pointLength;
    
    /**
     * 默认值
     */
    @TableField("default_value")
    private String defaultValue;
    
    /**
     * 字段类型
     */
    @TableField("column_type")
    private String columnType;
    
    /**
     * 字段主键 0：否  1：是
     */
    @TableField("column_pk")
    private boolean columnPk;
    
    /**
     * 字段可空 0：否  1：是
     */
    @TableField("column_null")
    private boolean columnNull;
    
    /**
     * 表单项 0：否  1：是
     */
    @TableField("form_item")
    private boolean formItem;
    
    /**
     * 表单必填 0：否  1：是
     */
    @TableField("form_required")
    private boolean formRequired;
    
    /**
     * 表单控件
     */
    @TableField("form_input")
    private String formInput;
    
    /**
     * 表单控件默认值
     */
    @TableField("form_default")
    private String formDefault;
    
    /**
     * 表单字典
     */
    @TableField("form_dict")
    private String formDict;
    
    /**
     * 列表项 0：否  1：是
     */
    @TableField("grid_item")
    private boolean gridItem;
    
    /**
     * 列表排序 0：否  1：是
     */
    @TableField("grid_sort")
    private boolean gridSort;
    
    /**
     * 查询项 0：否  1：是
     */
    @TableField("query_item")
    private boolean queryItem;
    
    /**
     * 查询方式
     */
    @TableField("query_type")
    private String queryType;
    
    /**
     * 查询控件
     */
    @TableField("query_input")
    private String queryInput;
    
    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
    
    /**
     * 表ID
     */
    @TableField("table_id")
    private String tableId;
    
    /**
     * 判断是否为主键
     */
    public boolean isPrimaryKey() {
        return columnPk;
    }
}

