package com.jun.common.report;

import java.util.*;

/**
 * 表格行
 * 
 * @author Wujun
 * 
 */
public class TableRow extends TableLine {
	/**
	 * 数据容器
	 */
	private Vector data = new Vector();

	public TableRow() {}

	/**
	 * 创建一个空行。
	 * @param rowCount 行中单元格的个数。
	 */
	public TableRow(int cellCount) {
		this();
		for (int i = 0; i < cellCount; i++) {
			TableCell c = new TableCell("");
			data.add(c);
		}
	}

	/**
	 * 获得所有的单元格
	 * 
	 * @return TableCell[]
	 */
	public TableCell[] getCells() {
		return (TableCell[]) data.toArray(new TableCell[0]);
	}

	/**
	 * 添加单元格
	 * 
	 * @param cell
	 */
	public void addCell(TableCell cell) {
		data.add(cell);
	}

	/**
	 * 删除指定的单元格
	 * 
	 * @param cell 待删除的单元格
	 */
	public void deleteCell(TableCell cell) {
		data.removeElement(cell);
	}

	/**
	 * 获取单元格个数
	 * 
	 * @return int
	 */
	public int getCellCount() {
		return data.size();
	}

	/**
	 * 获得指定位置的单元格
	 * 
	 * @param index 单元的索引位置
	 * @return TableCell
	 */
	public TableCell getCell(int index) throws ReportException {
		if (index < 0 || index > (data.size() - 1)) {
			throw new ReportException("The cell you found is not at the index of + " + index);
		}
		return (TableCell) data.elementAt(index);
	}

	/**
	 * 设置指定位置的单元格
	 * 
	 * @param index 单元格索引
	 * @param tc  单元格对象
	 */
	public void setCell(int index, TableCell tc) {
		data.setElementAt(tc, index);
	}

	/**
	 * 克隆当前TableRow对象的副本并返回(浅复制)
	 */
	public Object clone() {
		TableRow tableRow = new TableRow();
		tableRow = (TableRow) this.clone(tableRow);
		tableRow.data = (Vector) this.data.clone();
		return tableRow;
	}

	/**
	 * 克隆当前TableRow对象的副本并返回(深度复制)
	 * 副本与原对象不是同一对象
	 */
	public TableRow deepClone() throws ReportException {
		TableRow tableRow = null;
		try {
			tableRow = (TableRow) this.clone();
			for (int i = 0; i < this.getCellCount(); i++) {
				tableRow.setCell(i, (TableCell) this.getCell(i).clone());
			}
		} catch (CloneNotSupportedException ex) {
			throw new ReportException(ex.getMessage());
		}
		return tableRow;
	}

	/**
	 * 设置单元格跨度值
	 * 
	 * @param tc   单元格
	 * @param span 跨度值
	 */
	public void setSpan(TableCell tc, int span) {
		tc.setColSpan(span);
	}

	/**
	 * 获得单元格跨度值
	 * 
	 * @param tc 单元格
	 * @return   跨度值
	 */
	public int getSpan(TableCell tc) {
		return tc.getColSpan();
	}

	/**
	 * 插入单元格。
	 * 
	 * @param cell  单元格
	 * @param index 单元格索引
	 */
	public void insertCell(TableCell cell, int index) {
		data.insertElementAt(cell, index);
	}
}