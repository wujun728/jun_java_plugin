package com.kind.core.domain;

public class ColumnDO {
    private String columnName;

    private String columnNameNoDash;

    private String columnNameCapitalized;

    private String columnType;

    private String columnTypeRsGetter;

    private String columnComment;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnNameNoDash() {
        return columnNameNoDash;
    }

    public void setColumnNameNoDash(String columnNameNoDash) {
        this.columnNameNoDash = columnNameNoDash;
    }

    public String getColumnNameCapitalized() {
        return columnNameCapitalized;
    }

    public void setColumnNameCapitalized(String columnNameCapitalized) {
        this.columnNameCapitalized = columnNameCapitalized;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnTypeRsGetter() {
        return columnTypeRsGetter;
    }

    public void setColumnTypeRsGetter(String columnTypeRsGetter) {
        this.columnTypeRsGetter = columnTypeRsGetter;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
}
