/*
 * 文件名：TableColumn.java
 * 版权：
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改内容：
 */
package com.jun.excel.excelTools;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Wujun
 * @version 1.0,2011-4-11 下午05:48:52
 * @email jiangxd@eastcom-sw.com
 */
public class TableColumn {
	/**
	 * 字符串型
	 */
	public static final int COLUMN_TYPE_STRING = 0;

	/**
	 * 浮点型，保留2位小数
	 */
	public static final int COLUMN_TYPE_FLOAT_2 = 1;

	/**
	 * 浮点型，保留3位小数
	 */
	public static final int COLUMN_TYPE_FLOAT_3 = 2;

	/**
	 * 整形
	 */
	public static final int COLUMN_TYPE_INTEGER = 3;

	/**
	 * 红色背景
	 */
	public static final int COLUMN_TYPE_RED_BG = 10;

	/**
	 * 黄色背景
	 */
	public static final int COLUMN_TYPE_YELLOW_BG = 11;

	/**
	 * 绿色背景
	 */
	public static final int COLUMN_TYPE_GREEN_BG = 12;

	/**
	 * 列id
	 */
	private String id;

	/**
	 * 列显示字符
	 */
	private String display;

	/**
	 * 列对应数据库字段
	 */
	private String dbField;

	/**
	 * 列数据类型，参考静态变量COLUMN_TYPE_* 默认为{@link #COLUMN_TYPE_STRING}
	 */
	private int columnType = COLUMN_TYPE_STRING;

	/**
	 * 是否合并相同行, 默认false
	 */
	private boolean grouped = false;

	/**
	 * 是否显示小计，只在该列为grouped情况下生效, 默认false
	 */
	private boolean displaySummary = false;

	/**
	 * 小计的聚集方法
	 */
	private String aggregateRule;

	/**
	 * 是否显示该列, 默认true
	 */
	private boolean isVisible = true;

	/**
	 * 标识该列是否为复合表头, 默认false
	 */
	private boolean isComplex = false;

	/**
	 * 列描述
	 */
	private String common;

	/**
	 * 列宽度,主要用于前台显示,默认100长度单位
	 */
	private int width = 100;

	/**
	 * 列宽度(百分比形式),主要用于前台显示
	 */
	private int percentWidth;

	/**
	 * 列的宽度类型,分为百分比宽度和实际宽度,分别以 P 和 A 表示,默认为P
	 */
	private String widthType = "P";

	protected int level;

	protected TableColumn parent = null;

	/**
	 * 复合表头，拥有子列
	 */
	protected List<TableColumn> children = new LinkedList<TableColumn>();

	public void addChild(TableColumn column) {
		children.add(column);
		column.parent = this;
		if (column.isVisible())
			isComplex = true;
	}

	public List<TableColumn> getChildren() {
		LinkedList<TableColumn> ret = new LinkedList<TableColumn>();
		for (TableColumn c : children) {
			if (c.isVisible())
				ret.add(c);
		}
		return ret;
	}

	public boolean isComplex() {
		return isComplex;
	}

	public int getLength() {
		int len = 0;
		if (isComplex()) {
			for (TableColumn col : getChildren()) {
				len += col.getLength();
			}
		} else {
			len = 1;
		}
		return len;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getDbField() {
		return dbField;
	}

	public void setDbField(String dbField) {
		this.dbField = dbField;
	}

	public int getColumnType() {
		return columnType;
	}

	public String getColumnStringType(int columnType) {
		String stringType = "string";
		if (columnType == this.COLUMN_TYPE_INTEGER) {
			stringType = "int";
		} else if (columnType == this.COLUMN_TYPE_FLOAT_2) {
			stringType = "float";
		} else if (columnType == this.COLUMN_TYPE_FLOAT_3) {
			stringType = "float";
		}
		return stringType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public boolean isGrouped() {
		return grouped;
	}

	public void setGrouped(boolean grouped) {
		this.grouped = grouped;
	}

	public boolean isDisplaySummary() {
		return displaySummary;
	}

	public void setDisplaySummary(boolean displaySummary) {
		this.displaySummary = displaySummary;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getAggregateRule() {
		return aggregateRule;
	}

	public void setAggregateRule(String aggregateRule) {
		this.aggregateRule = aggregateRule;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommon() {
		return common;
	}

	public void setCommon(String common) {
		this.common = common;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getPercentWidth() {
		return percentWidth;
	}

	public void setPercentWidth(int percentWidth) {
		this.percentWidth = percentWidth;
	}

	public String getWidthType() {
		return widthType;
	}

	public void setWidthType(String widthType) {
		this.widthType = widthType;
	}
}
