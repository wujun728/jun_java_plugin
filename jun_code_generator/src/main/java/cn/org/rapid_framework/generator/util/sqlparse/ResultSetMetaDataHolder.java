package cn.org.rapid_framework.generator.util.sqlparse;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import cn.org.rapid_framework.generator.util.StringHelper;

public class ResultSetMetaDataHolder {
	public ResultSetMetaDataHolder() {
	}
	public ResultSetMetaDataHolder(ResultSetMetaData m, int i) throws SQLException {
        String catalogName = m.getCatalogName(i);
        String columnClassName = m.getColumnClassName(i);
        int columnDisplaySize = m.getColumnDisplaySize(i);
        String columnLabel = m.getColumnLabel(i);
        String columnName = m.getColumnName(i);
        
        int columnType = m.getColumnType(i);
        String columnTypeName = m.getColumnTypeName(i);
        int precision = m.getPrecision(i);
        int scale = m.getScale(i);
        
        String schemaName = m.getSchemaName(i);
        String tableName = m.getTableName(i);
        
        this.catalogName = catalogName;
        this.columnClassName = columnClassName ;
        this.columnDisplaySize = columnDisplaySize;
        this.columnLabel = columnLabel;
        this.columnName = columnName;
        this.columnType=  columnType;
        this.columnTypeName = columnTypeName;
        this.precision  = precision;
        this.scale = scale;
        this.schemaName = schemaName;
        this.tableName = tableName;    		
	}
    String catalogName ;
    String columnClassName ;
    int columnDisplaySize ;
    String columnLabel ;
    String columnName;
    
    int columnType ;
    String columnTypeName ;
    int precision  ;
    int scale ;
    
    String schemaName ;
    String tableName ;
    public String getCatalogName() {
        return catalogName;
    }
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }
    public String getColumnClassName() {
        return columnClassName;
    }
    public void setColumnClassName(String columnClassName) {
        this.columnClassName = columnClassName;
    }
    public int getColumnDisplaySize() {
        return columnDisplaySize;
    }
    public void setColumnDisplaySize(int columnDisplaySize) {
        this.columnDisplaySize = columnDisplaySize;
    }
    public String getColumnLabel() {
        return columnLabel;
    }
    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }
    public String getColumnNameOrLabel() {
    	return StringHelper.isBlank(columnName) ? columnLabel : columnName;
    }
    public String getColumnName() {
        return columnName;
    }
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    public int getColumnType() {
        return columnType;
    }
    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }
    public String getColumnTypeName() {
        return columnTypeName;
    }
    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }
    public int getPrecision() {
        return precision;
    }
    public void setPrecision(int precision) {
        this.precision = precision;
    }
    public int getScale() {
        return scale;
    }
    public void setScale(int scale) {
        this.scale = scale;
    }
    public String getSchemaName() {
        return schemaName;
    }
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
}