package com.jun.plugin.common.generator;

import lombok.Data;

/**
 * field info
 *
 */
@Data
public class FieldInfo {
    private String columnName;
    private String columnType;
    private String fieldName;
    private String fieldClass;
    private String fieldType;
    private String fieldComment;
    private Boolean isPrimaryKey;
    private Boolean isAutoIncrement;
    private long columnSize;
    private Boolean nullable;
    private Boolean comment;
    private String defaultValue;



    /**
     * 主键
     */
    private Long id;

    /**
     * 代码生成主表ID
     */
    private Long codeGenId;

    /**
     * 数据库字段名
     */
//    private String columnName;

    /**
     * java类字段名
     */
    private String javaName;

    /**
     * 字段描述
     */
    private String columnComment;

    /**
     * java类型
     */
    private String javaType;

    /**
     * 作用类型（字典）
     */
    private String effectType;

    /**
     * 字典code
     */
    private String dictTypeCode;

    /**
     * 列表是否缩进（字典）
     */
    private String whetherRetract;

    /**
     * 是否必填（字典）
     */
    private String whetherRequired;

    /**
     * 是否是查询条件
     */
    private String queryWhether;

    /**
     * 查询方式
     */
    private String queryType;

    /**
     * 列表显示
     */
    private String whetherTable;

    /**
     * 增改
     */
    private String whetherAddUpdate;

    /**
     * 主外键
     */
    public String columnKey;

    /**
     * 首字母大写名称（用于代码生成get set方法）
     */
    public String columnKeyName;

    /**
     * 数据库中类型（物理类型）
     */
    public String dataType;

    /**
     * 是否是通用字段
     */
    public String whetherCommon;

}
