package com.jun.common.report;

import java.util.Vector;

import com.itextpdf.text.Font;

/**
 * 报表表格类
 * 
 * @author Wujun
 * 
 */
public class Table extends Rectangle {
	private Vector data = new Vector();
	
	/**
	 * 表格细边框样式
	 */
	private String style = "border-collapse:collapse;";

    /**
     * 表格填充
     */
	private int cellpadding = 2;
	/**
	 * 表格内间距
	 */
	private int cellspacing = 0;

	/**
	 * 表格类型
	 */
	private String type = Report.DATA_TYPE;

	/**
	 * 表格宽度
	 */
	protected int width = 0;

	/**
	 * 表格各列宽度
	 */
	private int[] widths;
	/**
	 * 页脚字体类型
	 */
	private int footerFontType = Font.NORMAL;
	/**
	 * 页脚字号大小
	 */
	private int footerFontSize = 10;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if (width > 100 && width < 0) {
			throw new RuntimeException("Width must bettwen 0 and 100!");
		}
		this.width = width;
	}
    
	public Vector getData() {
		return data;
	}

	public void setData(Vector data) {
		this.data = data;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getCellpadding() {
		return cellpadding;
	}

	public void setCellpadding(int cellpadding) {
		this.cellpadding = cellpadding;
	}

	public int getCellspacing() {
		return cellspacing;
	}

	public void setCellspacing(int cellspacing) {
		this.cellspacing = cellspacing;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int[] getWidths() {
		return widths;
	}

	public void setWidths(int[] widths) {
		this.widths = widths;
	}

	public int getFooterFontType() {
		return footerFontType;
	}

	public void setFooterFontType(int footerFontType) {
		this.footerFontType = footerFontType;
	}

	public int getFooterFontSize() {
		return footerFontSize;
	}

	public void setFooterFontSize(int footerFontSize) {
		this.footerFontSize = footerFontSize;
	}

	public Table(){
		this.width = 98;
		this.align = Rectangle.ALIGN_CENTER;
	}

	/**
	 * 初始化一个空表格
	 * @param row 行数
	 * @param col 列数
	 */
	public Table(int row, int col) {
		this();
		for (int i = 0; i < row; i++) {
			this.data.add(new TableRow(col));
		}
	}

	/**
	 * 获取表格所有行
	 * @return TableRow[]
	 */
	public TableRow[] getRows() {
		return (TableRow[]) data.toArray(new TableRow[0]);
	}

	/**
	 * 获取指定行
	 * @param index 行索引
	 * @return
	 */
	public TableRow getRow(int index) {
		return (TableRow) data.elementAt(index);
	}

	/**
	 * 获取所有列
	 * @return TableColumn[]
	 */
	public TableColumn[] getCols() {
		return null;
	}

	/**
	 * 获取指定列
	 * @param index 列索引
	 */
	public TableColumn getCol(int index) throws ReportException {
		TableColumn tableColumn = new TableColumn();
		for (int i = 0; i < data.size(); i++) {
			TableRow tr = (TableRow) data.elementAt(i);
			if (tr == null) {
				throw new ReportException("TableRow is null!");
			}
			tableColumn.addCell(tr.getCell(index));
		}
		return tableColumn;
	}

	/**
	 * 添加列
	 * @param c 待添加的列
	 */
	public void addCol(TableColumn c) throws ReportException {
		if (null != data && data.size() != 0) {
			if (c.getCellCount() != data.size()) {
				throw new ReportException("Column number is not same");
			}
			for (int i = 0; i < data.size(); i++) {
				((TableRow) data.elementAt(i)).addCell(c.getCell(i));
			}
		}
	}

	/**
	 * 添加行
	 * @param tr 待添加的行
	 */
	public void addRow(TableRow tr) throws ReportException {
		if (null != data && data.size() != 0) {
			if (this.getColCount() != tr.getCellCount()){
				throw new ReportException("Row number is not same");
			}
			data.add(tr);
		}
	}

	/**
	 * 删除行
	 * @param tr 待删除的行
	 */
	public void deleteRow(TableRow tr) {
		data.removeElement(tr);
	}

	/**
	 * 插入行
	 * @param index 插入位置索引
	 * @param tr    待插入行
	 */
	public void insertRow(int index, TableRow tr) {
		data.insertElementAt(tr, index);
	}

	/**
	 * 获取表格行数
	 * @return int 总行数
	 */
	public int getRowCount() {
		return data.size();
	}

	/**
	 * 设置表格列指定索引位置的单元格
	 * @param index 单元格索引
	 * @param c     列
	 */
	public void setCol(int index, TableColumn c) throws ReportException {
		if (index >= 0 && this.getColCount() >= index && c.getCellCount() == this.getRowCount()) {
			for (int i = 0; i < this.getRowCount(); i++) {
				this.getRow(i).setCell(index, c.getCell(i));
			}
		} else {
			throw new ReportException("Cloumn index is wrong or Column cellNumber is not same with table RowNumber");
		}
	}

	/**
	 * 设置表格指定索引位置的行
	 * @param index
	 * @param r 行
	 * @throws ReportException
	 */
	public void setRow(int index, TableRow r) throws ReportException {
		data.setElementAt(r, index);
	}

	/**
	 * 获取表格列数
	 * @return int
	 */
	public int getColCount() {
		int rt = 0;
		if (data != null && this.getRowCount() > 0) {
			rt = ((TableRow) data.elementAt(0)).getCellCount();
		}
		return rt;
	}

	/**
	 * 根据行号、列号获取单元格
	 * @param row  行索引
	 * @param col  列索引
	 * @return     单元格
	 */
	public TableCell getCell(int row, int col) throws ReportException {
		return this.getRow(row).getCell(col);
	}

	/**
	 * 设置表格指定行索引和列索引位置的单元格
	 * 
	 * @param row    行索引
	 * @param col    列索引
	 * @param tc
	 * @throws ReportException
	 */
	public void setCell(int row, int col, TableCell tc) throws ReportException {
		TableRow tr = this.getRow(row);
		tr.setCell(col, tc);
		this.setRow(row, tr);
	}

	/**
	 * 表格克隆(浅复制)
	 * 克隆副本和原对象是同一对象
	 */
	public Table clone() throws CloneNotSupportedException {
		Table rt = new Table();
		rt = (Table) this.clone(rt);
		rt.data = (Vector) this.data.clone();
		copyAttributesTo(rt);
		return rt;
	}
    
	/**
	 * 属性copy
	 * @param rt
	 */
	private void copyAttributesTo(Table rt) {
		rt.cellpadding = this.cellpadding;
		rt.cellspacing = this.cellspacing;
		rt.style = this.style;
		rt.type = this.type;
		rt.widths = this.widths;
		rt.width = this.width;
	}

	/**
	 * 表格克隆(深度复制)
	 * 克隆副本和原对象不是同一对象
	 */
	public Table deepClone() throws ReportException {
		Table rt = null;
		try {
			rt = (Table) this.clone();
		} catch (CloneNotSupportedException ex) {
			throw new ReportException(ex.getMessage());
		}
		for (int i = 0; i < this.getRowCount(); i++) {
			rt.setRow(i, (TableRow) this.getRow(i).deepClone());
		}
		return rt;
	}
	
	/**
	 * 表格行列互换
	 * @return
	 * @throws ReportException
	 */
	public Table getRotateTable() throws ReportException {
		Table result = new Table(this.getColCount(), this.getRowCount());
		this.copyAttributesTo(result);
		TableCell temp = new TableCell();
		for (int i = 0; i < this.getRowCount(); i++) {
			for (int j = 0; j < this.getColCount(); j++) {
				temp = this.getCell(i, j);
				temp.setColSpan(temp.getRowSpan());
				temp.setRowSpan(temp.getColSpan());
				result.setCell(j, i, temp);
			}
		}
		return result;
	}

	/**
	 * 两个Table合并(求行列的并集)
	 * @param table
	 * @return
	 */
	public Table addTableByCol(Table table){
		try {
			int rowCount1 = this.getRowCount();
			int rowCount2 = table.getRowCount();
			if (rowCount1 == 0) {
				for (int i = 0; i < rowCount2; i++) {
					this.addRow(table.getRow(i));
				}

			} else {
				Table nTable = null;
				if (rowCount1 > rowCount2) {
					nTable = new Table(rowCount1, table.getColCount());
					for (int c = 0; c < table.getColCount(); c++) {
						for (int i = 0; i < rowCount2; i++) {
							nTable.getCol(c).getCell(i).reverse_clone(table.getCol(c).getCell(i));
						}
						for (int i = rowCount2; i < rowCount1; i++) {
							nTable.getCol(c).addCell(new TableCell());
						}
					}

				} else if (rowCount1 < rowCount2) {
					nTable = new Table(rowCount2, table.getColCount());
					for (int c = 0; c < table.getColCount(); c++) {
						for (int i = 0; i < rowCount1; i++) {
							nTable.getCol(c).getCell(i).reverse_clone(table.getCol(c).getCell(i));
						}
						for (int i = rowCount1; i < rowCount2; i++) {
							nTable.getCol(c).addCell(new TableCell());
						}
					}
				} else if (rowCount1 == rowCount2) {
					nTable = table;
				}
				for (int i = 0; i < nTable.getColCount(); i++) {
					this.addCol(nTable.getCol(i));
				}
			}
			return this;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
