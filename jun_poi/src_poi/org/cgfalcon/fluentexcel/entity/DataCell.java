package org.cgfalcon.fluentexcel.entity;

import org.apache.poi.ss.usermodel.CellStyle;


public class DataCell {
	
	private String content = "";
	private CellStyle css;
    private String rawStyle;
	private Integer type;
	private int row;
	private int col;

    public String getRawStyle() {
        return rawStyle;
    }

    public void setRawStyle(String rawStyle) {
        this.rawStyle = rawStyle;
    }

    public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public CellStyle getCss() {
		return css;
	}
	
	public void setCss(CellStyle css) {
		this.css = css;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setCol(int col) {
		this.col = col;
	}

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
