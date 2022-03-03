package net.chenlin.dp.generator.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据表属性
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月28日 下午8:12:53
 */
public class TableEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 表格备注
	 */
	private String tableComment;
	
	/**
	 * 主键
	 */
	private ColumnEntity pk;
	
	/**
	 * 表格列
	 */
	private List<ColumnEntity> columns;
	
	/**
	 * 类名，作为实例对象使用（sysUser）
	 */
	private String objName;
	
	/**
	 * 类名，作为类型使用（SysUser）
	 */
	private String className;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * 是否含有decimal类型数据
	 */
	private Boolean hasDecimal;

	public TableEntity buildHasDecimal() {
		for (ColumnEntity columnEntity : columns) {
			if ("decimal".equals(columnEntity.getDataType().toLowerCase())) {
				this.hasDecimal = true;
				return this;
			}
		}
		return this;
	}

	public Boolean getHasDecimal() {
		return hasDecimal;
	}

	public void setHasDecimal(Boolean hasDecimal) {
		this.hasDecimal = hasDecimal;
	}

	public TableEntity() {
		super();
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

	public ColumnEntity getPk() {
		return pk;
	}

	public void setPk(ColumnEntity pk) {
		this.pk = pk;
	}

	public List<ColumnEntity> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnEntity> columns) {
		this.columns = columns;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void addColumn(ColumnEntity columnEntity) {
		if (this.columns == null) {
			columns = new ArrayList<>();
		}
		columns.add(columnEntity);
	}

	@Override
	public String toString() {
		return "TableEntity{" +
				"tableName='" + tableName + '\'' +
				", tableComment='" + tableComment + '\'' +
				", pk=" + pk +
				", columns=" + columns +
				", objName='" + objName + '\'' +
				", className='" + className + '\'' +
				", createTime=" + createTime +
				'}';
	}

}
