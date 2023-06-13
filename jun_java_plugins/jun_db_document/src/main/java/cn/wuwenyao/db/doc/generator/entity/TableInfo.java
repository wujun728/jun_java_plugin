package cn.wuwenyao.db.doc.generator.entity;

import java.util.List;

/***
 * 表信息
 * 
 * @author wwy
 *
 */
public class TableInfo {

	/***
	 * 表名
	 */
	private String tableName;

	/***
	 * 备注信息
	 */
	private String tableRemark;

	/***
	 * 列
	 */
	private List<TableFieldInfo> fields;

	/**
	 * 索引信息
	 */
	private List<TableKeyInfo> keys;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<TableFieldInfo> getFields() {
		return fields;
	}

	public void setFields(List<TableFieldInfo> fields) {
		this.fields = fields;
	}

	public String getTableRemark() {
		return tableRemark;
	}

	public void setTableRemark(String tableRemark) {
		this.tableRemark = tableRemark;
	}

	public List<TableKeyInfo> getKeys() {
		return keys;
	}

	public void setKeys(List<TableKeyInfo> keys) {
		this.keys = keys;
	}

}
