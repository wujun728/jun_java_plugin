package com.jun.utils.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

import java.util.HashMap;

public final class ExcelUtils {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void setHashMapValue(HSSFCell keyCell, HSSFCell valueCell, HashMap map) {

        if (valueCell != null) {
            String key = getStringValueFromCell(keyCell);
            switch (valueCell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    Double doubleValue = valueCell.getNumericCellValue();
                    // 判断是写小数还是整数
                    if (doubleValue.intValue() == doubleValue) {
                        map.put(key, doubleValue.intValue());
                    } else {
                        map.put(key, doubleValue);
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING: // 字符串
                    map.put(key, valueCell.getStringCellValue());
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    map.put(key, valueCell.getBooleanCellValue());
                    break;
                case HSSFCell.CELL_TYPE_BLANK://空值
                    break;
                default:
                    System.err.println("ExcelUtils->unknown cell type");
                    break;
            }
        }
    }

    public static String getStringValueFromCell(HSSFCell _cell) {
        String key = null;
        switch (_cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                key = String.valueOf(_cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_STRING:
                key = _cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                key = String.valueOf(_cell.getBooleanCellValue());
                break;
            default:
                System.err.println("ExcelUtils->getStringValueFromCell() unknown cell type");
                break;
        }
        return key;
    }


}
