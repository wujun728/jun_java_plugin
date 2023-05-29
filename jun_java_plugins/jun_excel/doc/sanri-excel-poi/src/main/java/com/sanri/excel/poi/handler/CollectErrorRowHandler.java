package com.sanri.excel.poi.handler;

import com.sanri.excel.poi.ColumnConfig;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 默认错误行处理器，收集所有错误行
 */
public class CollectErrorRowHandler implements ErrorRowHandler {
    private List<CellData> errorDatas = new ArrayList<>();

    @Override
    public boolean handlerRow(Cell cell, Method writeMethod, ColumnConfig columnConfig) {
        CellData cellData = new CellData(cell.getRowIndex(), cell.getColumnIndex(), cell.getStringCellValue(), columnConfig);
        errorDatas.add(cellData);
        return true;
    }

    public static class CellData{
        private int rowIndex;
        private int cellIndex;
        private String cellValue;
        private ColumnConfig columnConfig;

        public CellData() {
        }

        public CellData(int rowIndex, int cellIndex, String cellValue, ColumnConfig columnConfig) {
            this.rowIndex = rowIndex;
            this.cellIndex = cellIndex;
            this.cellValue = cellValue;
            this.columnConfig = columnConfig;
        }
    }

    public List<CellData> getErrorDatas() {
        return errorDatas;
    }
}
