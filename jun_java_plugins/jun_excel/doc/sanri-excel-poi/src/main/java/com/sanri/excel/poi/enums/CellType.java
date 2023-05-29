package com.sanri.excel.poi.enums;

/**
 * 单元格类型,用于导出的时候设置单元格类型
 */
public enum  CellType {
    CELL_TYPE_NUMERIC(0),CELL_TYPE_STRING(1),
    CELL_TYPE_BLANK(3),CELL_TYPE_BOOLEAN(4),;

    private int value;

    CellType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
