package com.jun.common.report;

import java.util.*;

/**
 * 表格列
 */
public class TableColumn extends TableLine {

	/**
	 * 数据容器
	 */
	private Vector data = new Vector();

	public TableColumn() {}

	/**
	 * 创建一个空列
	 * @param cellCount 列中单元格个数
	 *  
	 */
	public TableColumn(int cellCount) {
		for (int i = 0; i < cellCount; i++) {
			TableCell c = new TableCell("");
			this.data.add(c);
		}
	}

	/**
	 * 获得列中所有单元格
	 * 
	 * @return TableCell[]
	 */
	public TableCell[] getCells() {
		return null;
	}

	/**
	 * 获得列中单元格个数
	 */
	public int getCellCount() {
		return data.size();
	}

	/**
	 * 向列中添加单元格
	 */
	public void addCell(TableCell tc) {
		data.add(tc);
	}

	/**
	 * 获得指定的单元格
	 * 
	 * @param index 列中单元格索引
	 */
	public TableCell getCell(int index) {
		return (TableCell) data.elementAt(index);
	}

	/**
	 * 克隆当前TableColumn对象的副本并返回(浅复制)
	 */
	public Object cloneAttribute() {
		TableColumn tableColumn = new TableColumn();
		tableColumn = (TableColumn) this.clone(tableColumn);
		tableColumn.data = (Vector) this.data.clone();
		return tableColumn;
	}

	/**
	 * 克隆当前TableColumn对象的副本并返回(深度复制)
	 * 副本与原对象不是同一对象
	 */
	public TableColumn cloneAll() throws ReportException,CloneNotSupportedException {
		TableColumn tableColumn = new TableColumn();
		tableColumn = (TableColumn) this.cloneAttribute();
		for (int i = 0; i < this.getCellCount(); i++) {
			tableColumn.addCell((TableCell) this.getCell(i).clone());
		}
		return tableColumn;
	}

	public TableColumn cloneAllCell() throws ReportException,CloneNotSupportedException {
		TableColumn tableColumn = new TableColumn();
		for (int i = 0; i < this.getCellCount(); i++) {
			tableColumn.addCell((TableCell) this.getCell(i).clone());
		}
		return tableColumn;
	}

	/**
	 * 设置单元格的跨度值
	 * 
	 * @param tc    单元格
	 * @param span  跨度值
	 */
	public void setSpan(TableCell tc, int span) {
		tc.setRowSpan(span);
	}

	/**
	 * 获得单元格的跨度值.
	 * 
	 * @param tc  单元格
	 * @return
	 */
	public int getSpan(TableCell tc) {
		return tc.getRowSpan();
	}

	/**
	 * 获得列数据
	 * 
	 * @return
	 */
	public Vector getData() {
		return data;
	}

	/**
	 * 设置列数据
	 * 
	 * @param data
	 */
	public void setData(Vector data) {
		this.data = data;
	}
}