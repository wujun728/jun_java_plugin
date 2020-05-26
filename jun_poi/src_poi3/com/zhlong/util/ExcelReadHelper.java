/**
 * ExeclImportHelper.java类：本类位于insurance项目中的
 * com.magonchina.util包路径下，
 * 类具体的作用请参看代码中的文档注释。
 * 
 * Created By 赵海龙
 */
package com.zhlong.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**.Excel导入工具类
 * @author 赵海龙
 * @version 1.0
 * @since 2016年5月13日 下午2:41:07
 */
public class ExcelReadHelper {
    private Workbook workBook = null;
    private List<String[]> excelData = null;
    private String filePath;
    
    /**.
     * 构造方法：
     * @param filePath
     * @throws IOException 
     * @throws InvalidFormatException 
     * @throws EncryptedDocumentException 
     */
    public ExcelReadHelper(String filePath) throws EncryptedDocumentException, InvalidFormatException, IOException {
        super();
        this.filePath = filePath;
        initWorkBook();
    }
    
    
    /**方法：获取workbook实例
     * @author 赵海龙
     * @version 1.0
     * @since 2016年5月16日 下午3:20:20
     * @return
     */
    public Workbook getWorkBook(){
        return this.workBook;
    }
    /**.
     * 方法：初始化workBook实例
     * @throws EncryptedDocumentException
     * @throws InvalidFormatException
     * @throws IOException
     */
    private void initWorkBook() throws EncryptedDocumentException, InvalidFormatException, IOException{
        if(filePath == null || filePath.trim().length() <= 0){
            throw new InvalidFormatException("文件路径不能为空!");
        }
        File file = new File(filePath);
        FileInputStream fs = null;
        if (file.exists() && file.length() > 0) {
            if (suffixCheck(filePath)) {
                fs = new FileInputStream(file);
                workBook = WorkbookFactory.create(fs);
            }else {
                throw new IOException("文件不是合法Excel文件!");
            }
        }else {
            throw new IOException("文件不存在!");
        }
    }
    /**
     * . 方法：获取导入Excel中的列数
     * @param sheetIndex
     * @return
     */
    public int getColumnNum(int sheetIndex, int columnIdex) {
        Sheet sheet = workBook.getSheetAt(sheetIndex);
        return sheet.getRow(columnIdex).getLastCellNum()
                - sheet.getRow(columnIdex).getFirstCellNum();
    }
    
    /**.
     * 方法：读取Excel内容
     * @param sheetIndex 需要读取的sheet索引
     * @param title Excel表头，即：表格第一行的数据内容
     * @param rowMapper 数据处理接口
     */
    public <T> List<T> readExcel(int sheetIndex, String[] title, ReadRowMapper<T> rowMapper) {
        Sheet sheet = workBook.getSheetAt(sheetIndex);
        List<T> list = new ArrayList<T>();
        for (Row row : sheet) {
            if(title == null && row.getRowNum() == 0){
                title = this.getRowContent(row);
                continue;
            }
            Map<String,Object> map = this.rowToMap(row, title);
            list.add(rowMapper.rowMap(row,map));
        }
        return list;
    }
    
    /**
     * . 方法：读取指定Excel的内容
     * 
     * @param sheetIndex
     * @return
     * @throws EncryptedDocumentException
     * @throws InvalidFormatException
     * @throws IOException
     */
    public List<String[]> readToStringList(int sheetIndex) throws EncryptedDocumentException,
            InvalidFormatException, IOException {
        excelData = new ArrayList<String[]>(100);
        Sheet sheet = workBook.getSheetAt(sheetIndex);
        for (Row row : sheet) {
            excelData.add(this.getRowContent(row));
        }
        return excelData;
    }
    
    /**.
     * 方法：将Excel的每行数据转换为map，key为第一行数据的值
     * @param row
     * @param title 指定Excel的标题
     * @return
     */
    public Map<String,Object> rowToMap(Row row,String[] title){
        if(title == null && row.getRowNum() == 0){
            title = this.getRowContent(row);
        }
        int columnNum = title.length;
        
        Map<String,Object> map = new HashMap<String, Object>(columnNum);
        for (int i = 0; i < columnNum; i++) {
            Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                map.put(title[i], "");
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                map.put(title[i], cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    map.put(title[i], cell.getDateCellValue());
                } else {
                    map.put(title[i], cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                map.put(title[i], cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                map.put(title[i], "");
                break;
            case Cell.CELL_TYPE_FORMULA:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                map.put(title[i], cell.getStringCellValue());
                break;
            default:
                map.put(title[i], "");
                break;
            }
        }
        return map;
    }
    
    /**
     * . 方法：获取每一行的内容
     * 
     * @param row
     * @param columnNum
     *            列数
     * @return
     */
    public String[] getRowContent(Row row) {
        int columnNum = row.getLastCellNum() - row.getFirstCellNum();
        String[] singleRow = new String[columnNum];
        for (int i = 0; i < columnNum; i++) {
            Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                singleRow[i] = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                singleRow[i] = Boolean.toString(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (DateUtil.isCellDateFormatted(cell)) {
                    singleRow[i] = format.format(cell.getDateCellValue());
                } else {
                    singleRow[i] = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                singleRow[i] = cell.getStringCellValue().trim();
                break;
            case Cell.CELL_TYPE_ERROR:
                singleRow[i] = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                singleRow[i] = cell.getStringCellValue();
                if (singleRow[i] != null) {
                    singleRow[i] = singleRow[i].replaceAll("#N/A", "").trim();
                }
                break;
            default:
                singleRow[i] = "";
                break;
            }
        }
        return singleRow;
    }
    
    /**
     * . 方法：验证文件结尾是否为xls或者xlsx
     * 
     * @param fileName
     * @return
     */
    private boolean suffixCheck(String fileName) {
        if (fileName.lastIndexOf(".") > -1) {
            String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
            return suffix.matches("xls|xlsx$");
        }
        return false;
    }
}