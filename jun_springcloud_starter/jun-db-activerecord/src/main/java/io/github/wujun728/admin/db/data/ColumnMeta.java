package io.github.wujun728.admin.db.data;

import lombok.Data;

/***
 * 查询语句返回的列元数据
 */
@Data
public class ColumnMeta {
    //表名
    private String tableName;
    //字段名称
    private String columnName;
    //字段别名
    private String columnLabel;
    //数据库字段类型
    private String columnType;
    //java类型
    private String columnClassName;
    //字段注释
    private String columnComment;
}
