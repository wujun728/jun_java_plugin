package org.supercall.domainModel.framework;

/**
 * Created by kira on 16/8/3.
 */
public class TableFilterConfig {
    private String columnName;
    private String displayName;
    private String columnType;
    private String dataSourcePluginName;
    private String dataSourcePlginFunction;

    public TableFilterConfig(String columnName, String displayName, String columnType, String dataSourcePluginName, String dataSourcePlginFunction) {
        this.columnName = columnName;
        this.displayName = displayName;
        this.columnType = columnType;
        this.dataSourcePluginName = dataSourcePluginName;
        this.dataSourcePlginFunction = dataSourcePlginFunction;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getDataSourcePluginName() {
        return dataSourcePluginName;
    }

    public void setDataSourcePluginName(String dataSourcePluginName) {
        this.dataSourcePluginName = dataSourcePluginName;
    }

    public String getDataSourcePlginFunction() {
        return dataSourcePlginFunction;
    }

    public void setDataSourcePlginFunction(String dataSourcePlginFunction) {
        this.dataSourcePlginFunction = dataSourcePlginFunction;
    }
}
