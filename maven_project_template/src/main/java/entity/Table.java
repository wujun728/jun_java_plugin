package entity;

import java.util.List;
/**
 * 表实体
 * @author Administrator
 *
 */
public class Table {
	
	private String name;//表名称
	private String name2;//处理后的表名称
	private String comment;//介绍
	private String key;// 主键列
	private String key2;// 主键列（驼峰）
	private String key2Upper;// 主键列（驼峰）
	private String keyType;//主键类型

	public String getKey2Upper() {
		return key2Upper;
	}

	public void setKey2Upper(String key2Upper) {
		this.key2Upper = key2Upper;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getKey() {
		return key;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public void setKey(String key) {
		this.key = key;
	}
	private List<Column> columns;//列集合
	
	
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
