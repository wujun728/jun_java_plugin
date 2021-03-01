package com.jun.plugin.dbtracer;

import java.util.Map;

public class UpdateHistory {
    private String table;

    private String key;

    private String where;

    // 原始的列-值
    private Map<String, Object> originalCol2Val;

    // 更新后的键值队
    private Map<String, String> newCol2Val;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Map<String, Object> getOriginalCol2Val() {
        return originalCol2Val;
    }

    public void setOriginalCol2Val(Map<String, Object> originalCol2Val) {
        this.originalCol2Val = originalCol2Val;
    }

    // 执行更新操作的用户
    private String operator;

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public Map<String, String> getNewCol2Val() {
        return newCol2Val;
    }

    public void setNewCol2Val(Map<String, String> newCol2Val) {
        this.newCol2Val = newCol2Val;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("table=").append(table);
        sb.append(",originalCol2Val=").append(Util.toString(originalCol2Val));
        sb.append(",newCol2Val=").append(Util.toString(newCol2Val)).append(",where=").append(where)
                .append(",operator=").append(operator).append(",key=").append(key);

        return sb.toString();
    }

}
