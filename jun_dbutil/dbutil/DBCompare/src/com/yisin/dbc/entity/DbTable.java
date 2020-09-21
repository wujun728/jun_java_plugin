package com.yisin.dbc.entity;

import java.sql.Timestamp;

public class DbTable {

    private String tableSchema;
    private String tableName;
    private String tableComment;
    private Timestamp createTime;

    public String getTableSchema() {
        return tableSchema;
    }

    public DbTable setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public DbTable setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getTableComment() {
        return tableComment;
    }

    public DbTable setTableComment(String tableComment) {
        this.tableComment = tableComment;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public DbTable setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getName());
        sb.append(" # tableSchema=" + (tableSchema == null ? "null" : tableSchema.toString()));
        sb.append("; tableName=" + (tableName == null ? "null" : tableName.toString()));
        sb.append("; tableComment=" + (tableComment == null ? "null" : tableComment.toString()));
        sb.append("; createTime=" + (createTime == null ? "null" : createTime.toString()));
        return sb.toString();
    }

}
