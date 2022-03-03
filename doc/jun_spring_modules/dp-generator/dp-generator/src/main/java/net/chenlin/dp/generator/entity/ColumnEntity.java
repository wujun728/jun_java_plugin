package net.chenlin.dp.generator.entity;

import java.io.Serializable;

/**
 * 数据表列属性
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月28日 下午8:04:40
 */
public class ColumnEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 列名
	 */
	private String columnName;
	
	/**
	 * 数据类型
	 */
	private String dataType;
	
	/**
	 * 列注释
	 */
	private String columnComment;
	
	/**
	 * 属性名，作为类属性名（userId）
	 */
	private String fieldName;
	
	/**
	 * 属性名，作为类方法名（UserId）
	 */
	private String methodName;
	
	/**
	 * 列数据类型对应java数据类型
	 */
	private String fieldType;
	
	/**
	 * 键类型标识
	 */
	private String columnKey;
	
	/**
	 * 自增标识 auto_increment
	 */
	private String extra;

	public ColumnEntity() {
		super();
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}
	
}
