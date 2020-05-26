/**
 * ExcelExportHelper.java类：本类位于insurance项目中的
 * com.magonchina.util包路径下，
 * 类具体的作用请参看代码中的文档注释。
 * 
 * Created By 赵海龙
 */
package com.zhlong.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**.Excel导出工具类
 * @author 赵海龙
 * @version 1.0
 * @since 2016年5月13日 下午2:40:51
 */
public class ExcelWriteHelper {
    private FileOutputStream fileOut = null;
    private Workbook workBook = null;
    
    public ExcelWriteHelper(String filePath) throws IOException {
        File file = new File(filePath);
        file.createNewFile();
        fileOut = new FileOutputStream(file);
        workBook = new HSSFWorkbook();
    }
    
    /**
     * . 方法：导出excel
     * 
     * @param sheetName
     * @param titles
     * @param dataSet
     * @param rowMapper
     * @throws IOException
     */
    public void exportExcel(String sheetName, String[] titles, List<? extends Object> dataSet,
            WriteRowMapper rowMapper) throws IOException {
        Sheet sheet = workBook.createSheet(sheetName);
        sheet.setVerticallyCenter(true);
        Row row = sheet.createRow(0);
        for (int i = 0; i < titles.length; i++) {
            sheet.setColumnWidth(i, titles[i].length() * 255 * 5);
            Cell cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(this.initTitleCellStyle());
        }
        for (int i = 0; i < dataSet.size(); i++) {
            row = sheet.createRow(i + 1);
            List<String> values = rowMapper.handleData(dataSet.get(i));
            if (values.size() != titles.length) {
                throw new IOException("转换后的列表长度与表头数组长度不一致");
            }
            for (int j = 0; j < values.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(String.valueOf(values.get(j)));
                cell.setCellStyle(this.initContentCellStyle());
            }
        }
        if (fileOut != null) {
            workBook.write(fileOut);
            fileOut.close();
        }
    }

    /**
     * . 方法：初始化excel内容表格样式
     * 
     * @return
     */
    private CellStyle initContentCellStyle() {
        CellStyle cell_Style = workBook.createCellStyle();// 设置字体样式
        cell_Style.setAlignment(CellStyle.ALIGN_CENTER);
        cell_Style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直对齐居中
        cell_Style.setWrapText(true); // 设置为自动换行
        Font cell_Font = workBook.createFont();
        cell_Font.setFontName("宋体");
        cell_Font.setFontHeightInPoints((short) 15);
        cell_Style.setFont(cell_Font);
        cell_Style.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        cell_Style.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
        cell_Style.setBorderTop(CellStyle.BORDER_THIN);// 上边框
        cell_Style.setBorderRight(CellStyle.BORDER_THIN);// 右边框
        return cell_Style;
    }

    /**
     * . 方法：初始化标题样式
     * 
     * @return
     */
    private CellStyle initTitleCellStyle() {
        CellStyle headerStyle = workBook.createCellStyle();// 创建标题样式
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 设置垂直居中
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER); // 设置水平居中
        Font headerFont = workBook.createFont(); // 创建字体样式
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD); // 字体加粗
        headerFont.setFontName("Times New Roman"); // 设置字体类型
        headerFont.setFontHeightInPoints((short) 15); // 设置字体大小
        headerStyle.setFont(headerFont); // 为标题样式设置字体样式
        return headerStyle;
    }

}
