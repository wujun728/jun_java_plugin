package io.github.wujun728.db.orm.mapping;

/**
 * 关联表
 */
public class JoinMap {

    //关联modelClass
    private Class<?> type;

    //关联属性名
    private String propertyName;

    //主model 关联列名
    private String column;

    //被关联实体关联列名
    private String refColumn;

    public Class<?> getType() {
        return type;
    }

    public JoinMap setType(Class<?> type) {
        this.type = type;
        return this;
    }

    public String getColumn() {
        return column;
    }

    public JoinMap setColumn(String column) {
        this.column = column;
        return this;
    }

    public String getRefColumn() {
        return refColumn;
    }

    public JoinMap setRefColumn(String refColumn) {
        this.refColumn = refColumn;
        return this;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public JoinMap setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }
}
