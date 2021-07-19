package com.kind.core.domain;

import java.util.ArrayList;
import java.util.List;

public class TableDO {
    private String tableName;

    private String tableComment;

    private String tableNameNoDash;

    private String tableNameCapitalized;

    private List<ColumnDO> columnBeanList = new ArrayList<ColumnDO>();

    private boolean hasDateColumn;

    private boolean hasBigDecimal;

    public void addColumn(ColumnDO columnDO) {
        columnBeanList.add(columnDO);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<ColumnDO> getColumnBeanList() {
        return columnBeanList;
    }

    public void setColumnBeanList(List<ColumnDO> columnBeanList) {
        this.columnBeanList = columnBeanList;
    }

    public String getTableNameNoDash() {
        return tableNameNoDash;
    }

    public void setTableNameNoDash(String tableNameNoDash) {
        this.tableNameNoDash = tableNameNoDash;
    }

    public String getTableNameCapitalized() {
        return tableNameCapitalized;
    }

    public void setTableNameCapitalized(String tableNameCapitalized) {
        this.tableNameCapitalized = tableNameCapitalized;
    }

    public boolean isHasDateColumn() {
        return hasDateColumn;
    }

    public void setHasDateColumn(boolean hasDateColumn) {
        this.hasDateColumn = hasDateColumn;
    }

    public boolean isHasBigDecimal() {
        return hasBigDecimal;
    }

    public void setHasBigDecimal(boolean hasBigDecimal) {
        this.hasBigDecimal = hasBigDecimal;
    }
}
