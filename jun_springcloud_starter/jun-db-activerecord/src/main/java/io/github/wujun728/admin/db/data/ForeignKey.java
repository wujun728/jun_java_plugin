package io.github.wujun728.admin.db.data;

import lombok.Data;

/***
 * 外键
 */
@Data
public class ForeignKey {
    //原外键名称
    private String oldConstraintName;
    //外键名称
    private String constraintName;
//    //主表
//    private String tableName;
    //字段名
    private String columnName;
    //关联表
    private String referencedTableName;
    //关联字段
    private String referencedColumnName;
}
