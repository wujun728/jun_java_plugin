package com.jun.plugin.freemark.code.entity;
/**
 * 实体类
 * @author shichenyang89@gmail.com
 *
 */
public class Columns {
	//列名
	private String columnName;
	//列类型
	private String columnType;
	//备注
	private String REMARKS;
	//大小
	private int datasize;
	private int digits;
	//是否为空
	private String nullable;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getREMARKS() {
		return REMARKS;
	}
	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}
	public int getDatasize() {
		return datasize;
	}
	public void setDatasize(int datasize) {
		this.datasize = datasize;
	}
	public int getDigits() {
		return digits;
	}
	public void setDigits(int digits) {
		this.digits = digits;
	}
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

}
