package io.github.wujun728.db.orm.mapping;

import java.lang.reflect.Method;

/**
 * Model属性与数据库字段关系
 */
public class ColumnMap {

    private String property; //model属性

    private String field; //数据库字段

    private Method setter;

    private Method getter;

    public String getProperty() {
        return property;
    }

    public ColumnMap setProperty(String property) {
        this.property = property;
        return this;
    }

    public String getField() {
        return field;
    }

    public ColumnMap setField(String field) {
        this.field = field;
        return this;
    }

    public Method getSetter() {
        return setter;
    }

    public ColumnMap setSetter(Method setter) {
        this.setter = setter;
        return this;
    }

    public Method getGetter() {
        return getter;
    }

    public ColumnMap setGetter(Method getter) {
        this.getter = getter;
        return this;
    }
}
