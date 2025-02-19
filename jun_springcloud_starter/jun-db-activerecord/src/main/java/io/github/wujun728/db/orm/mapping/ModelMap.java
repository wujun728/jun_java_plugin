package io.github.wujun728.db.orm.mapping;

import java.util.List;

/**
 * Model映射
 */
public class ModelMap {

    //对应表名
    private String table;

    private boolean isCache;

    private String primaryKey;

    //属性与数据库字段
    private List<ColumnMap> columnMaps;

    //关联Models
    private List<JoinMap> joinMaps;

    public String getTable() {
        return table;
    }

    public ModelMap setTable(String table) {
        this.table = table;
        return this;
    }

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    public List<ColumnMap> getColumnMaps() {
        return columnMaps;
    }

    public ModelMap setColumnMaps(List<ColumnMap> columnMaps) {
        this.columnMaps = columnMaps;
        return this;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public ModelMap setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public List<JoinMap> getJoinMaps() {
        return joinMaps;
    }

    public ModelMap setJoinMaps(List<JoinMap> joinMaps) {
        this.joinMaps = joinMaps;
        return this;
    }
}
