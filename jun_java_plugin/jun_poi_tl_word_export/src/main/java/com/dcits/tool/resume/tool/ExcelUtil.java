package com.dcits.tool.resume.tool;

import com.dcits.tool.resume.annotation.ExcelField;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public static List<Map<String, Object>> readExcel(String filePath, Integer sheetIndex,Map<String,String> mapping) {
        Workbook wb = ExcelUtil.createWorkBook(filePath);
        return getWorkbook(wb,sheetIndex,mapping);
    }


    public static List<Map<String, Object>> readExcel(String filePath, Integer sheetIndex,Class mappingType) {
        Workbook wb = ExcelUtil.createWorkBook(filePath);
        return getWorkbook(wb,sheetIndex,mappingType);
    }

    public static List<Map<String, String>> readExcelMapping(String filePath, Integer sheetIndex) {
        Workbook wb = ExcelUtil.createWorkBook(filePath);
        return getWorkbook(wb,sheetIndex);
    }

    private static List<Map<String,String>> getWorkbook(Workbook workbook,Integer sheetIndex){
        List<Map<String, String>> dataList = new ArrayList<>();
        if (workbook != null) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int maxRownum = sheet.getPhysicalNumberOfRows();
            Row firstRow = sheet.getRow(0);
            int maxColnum = firstRow.getPhysicalNumberOfCells();
            String columns[] = new String[maxColnum];
            for (int i = 0; i < maxRownum; i++) {
                Map<String, String> map = null;
                if (i > 0) {
                    map = new LinkedHashMap<>();
                    firstRow = sheet.getRow(i);
                }
                if (firstRow != null) {
                    String cellData = null;
                    for (int j = 0; j < maxColnum; j++) {
                        cellData = (String) ExcelUtil.getCellFormatValue(firstRow.getCell(j));
                        if (i == 0) {
                            columns[j] = cellData;
                        } else {
                            map.put(columns[j], cellData);
                        }
                    }
                } else {
                    break;
                }
                if (i > 0) {
                    dataList.add(map);
                }
            }
        }
        return dataList;
    }

    private static Workbook createWorkBook(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    private static List<Map<String,Object>> getWorkbook(Workbook workbook,Integer sheetIndex,Class mappingType){
        List<Map<String, Object>> dataList = new ArrayList<>();
        if (workbook != null) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int maxRownum = sheet.getPhysicalNumberOfRows();
            Row firstRow = sheet.getRow(0);
            int maxColnum = firstRow.getPhysicalNumberOfCells();
            String columns[] = new String[maxColnum];
            for (int i = 0; i < maxRownum; i++) {
                Map<String, Object> map = null;
                if (i > 0) {
                    map = new LinkedHashMap<>();
                    firstRow = sheet.getRow(i);
                }
                if (firstRow != null) {
                    String cellData = null;
                    for (int j = 0; j < maxColnum; j++) {
                        cellData = (String) ExcelUtil.getCellFormatValue(firstRow.getCell(j));
                        if (i == 0) {
                            columns[j] = cellData;
                        } else {
                            String key=getColumnName(columns[j],mappingType);
                            map.put(key, cellData);
                        }
                    }
                } else {
                    break;
                }
                if (i > 0) {
                    dataList.add(map);
                }
            }
        }
        return dataList;
    }

    private static List<Map<String,Object>> getWorkbook(Workbook workbook,Integer sheetIndex,Map<String,String> mapping){
        List<Map<String, Object>> dataList = new ArrayList<>();
        if (workbook != null) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int maxRownum = sheet.getPhysicalNumberOfRows();
            Row firstRow = sheet.getRow(0);
            int maxColnum = firstRow.getPhysicalNumberOfCells();
            String columns[] = new String[maxColnum];
            for (int i = 0; i < maxRownum; i++) {
                Map<String, Object> map = null;
                if (i > 0) {
                    map = new LinkedHashMap<>();
                    firstRow = sheet.getRow(i);
                }
                if (firstRow != null) {
                    String cellData = null;
                    for (int j = 0; j < maxColnum; j++) {
                        cellData = (String) ExcelUtil.getCellFormatValue(firstRow.getCell(j));
                        if (i == 0) {
                            columns[j] = cellData;
                        } else {
                            String key=getColumnName(columns[j],mapping);
                            map.put(key, cellData);
                        }
                    }
                } else {
                    break;
                }
                if (i > 0) {
                    dataList.add(map);
                }
            }
        }
        return dataList;
    }

    /**
     * 将字段转为相应的格式
     * @param cell
     * @return
     */
    private static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case NUMERIC: {
                    if(DateUtil.isCellDateFormatted(cell)){
                        cellValue = cell.getDateCellValue();
                        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
                        cellValue=format.format(cellValue);
                        break;
                    }
                    DecimalFormat bigDecimal=new DecimalFormat("########");
                    cellValue = String.valueOf(bigDecimal.format(cell.getNumericCellValue()));
                    break;
                }
                case FORMULA: {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = cell.getDateCellValue();//转换为日期格式YYYY-mm-dd
                    } else {
                        cellValue = String.valueOf(cell.getNumericCellValue()); //数字
                    }
                    break;
                }
                case STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    /**
     * 根据中文列明获得对应的实体类属性名
     * @param fieldName
     * @param clazz
     * @return
     */
    public static String getColumnName(String fieldName,Class clazz){
        Field[] fields=clazz.getDeclaredFields();
        for (Field field : fields) {
            if(field.getAnnotation(ExcelField.class)==null)
                continue;

            ExcelField annotation=field.getAnnotation(ExcelField.class);
            String name=annotation.name();
            if(name.equals(fieldName)){
                return field.getName();
            }
        }
        System.out.println("未匹配到字段名:"+fieldName);
        return null;

    }

    public static String getColumnName(String fieldName,Map<String,String> mapping){
       return mapping.get(fieldName);
    }














}
