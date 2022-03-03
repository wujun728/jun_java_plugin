package com.jun.plugin.poi.poiTemplate;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;

/**
 * 单元格模板对象
 * @author Wujun
 *
 */
public class TempCell {
	
	public int row;//行号
	
	public int column;//列号
	
	public Object value;//值
	
	public CellType cellType;//单元格类型
	
	public CellStyle style;//单元格样式
	
	public boolean isTag;//是否为标签
	
	public Tag tag;//标签对象
	
	public int endIndex;//标签结束序号
	
	public boolean isHandled;//是否已处理
	
	public float rowHeight;//行高

	public TempCell() {
		super();
	}

	public TempCell(int row, int column, Object value, CellStyle style) {
		super();
		this.row = row;
		this.column = column;
		this.value = value;
		this.style = style;
	}

	@Override
	public String toString() {
		return "TempCell [row=" + row + ", column=" + column + ", value="
				+ value + ", style=" + style + ", isTag=" + isTag + ", tag=" + tag + "]";
	}
	
	

}
