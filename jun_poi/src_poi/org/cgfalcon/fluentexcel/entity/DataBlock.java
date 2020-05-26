package org.cgfalcon.fluentexcel.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: falcon.chu
 * Date: 13-5-31
 * Time: 下午3:39
 */
public class DataBlock {

    private List<DataRow> rows;
    private int startRow = 1;

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public List<DataRow> getRows() {
        return rows;
    }

    public void setRows(List<DataRow> rows) {
        this.rows = rows;
    }

    public void addRow(DataRow row) {
        if (rows == null) {
            rows = new ArrayList<DataRow>();
        }
        rows.add(row);
    }
}
