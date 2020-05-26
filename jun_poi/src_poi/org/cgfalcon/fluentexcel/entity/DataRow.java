package org.cgfalcon.fluentexcel.entity;

import java.util.ArrayList;
import java.util.List;


public class DataRow {
	
	private List<DataCell> 	cells ;
    private int						row_index;
    private int                     startCol = 1;
    private Short                   height;
    private String code="";

    public Short getHeight() {
        return height;
    }

    public void setHeight(Short height) {
        this.height = height;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public List<DataCell> getCells() {
        return cells;
    }

    public void setCells(List<DataCell> cells) {
        this.cells = cells;
    }

    public void addCell(DataCell cell) {
        if (cells == null) {
            cells = new ArrayList<DataCell>();
        }
        cells.add(cell);
    }

    public int getRow_index() {
		return row_index;
	}

	public void setRow_index(int row_index) {
		this.row_index = row_index;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
