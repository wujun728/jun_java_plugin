package io.github.wujun728.admin.db.data;

import lombok.Data;

import java.util.Objects;

/***
 * 字段信息
 */
@Data
public class ColumnInfo {
    //字段名,用来区分新增更新
    private String oldColumnName;
    //字段名称
    private String columnName;
    //字段注释
    private String columnComment;
    //字段类型
    private String columnType;
    //是否允许为空
    private String isNullable;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColumnInfo that = (ColumnInfo) o;
        return columnName.equals(that.columnName) && Objects.equals(columnComment, that.columnComment) && columnType.equals(that.columnType) && isNullable.equals(that.isNullable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnName, columnComment, columnType, isNullable);
    }
}
