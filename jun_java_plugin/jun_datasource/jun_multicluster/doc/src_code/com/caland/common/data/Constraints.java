package com.caland.common.data;

public class Constraints {
	// fields
	private java.lang.String name;
	private java.lang.String tableName;
	private java.lang.String columnName;
	private java.lang.String referencedTableName;
	private java.lang.String referencedColumnName;

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getTableName() {
		return tableName;
	}

	public void setTableName(java.lang.String tableName) {
		this.tableName = tableName;
	}

	public java.lang.String getColumnName() {
		return columnName;
	}

	public void setColumnName(java.lang.String columnName) {
		this.columnName = columnName;
	}

	public java.lang.String getReferencedTableName() {
		return referencedTableName;
	}

	public void setReferencedTableName(java.lang.String referencedTableName) {
		this.referencedTableName = referencedTableName;
	}

	public java.lang.String getReferencedColumnName() {
		return referencedColumnName;
	}

	public void setReferencedColumnName(java.lang.String referencedColumnName) {
		this.referencedColumnName = referencedColumnName;
	}

	@Override
	public String toString() {
		return "Constraints [name=" + name + ", tableName=" + tableName
				+ ", columnName=" + columnName + ", referencedTableName="
				+ referencedTableName + ", referencedColumnName="
				+ referencedColumnName + "]";
	}
}
