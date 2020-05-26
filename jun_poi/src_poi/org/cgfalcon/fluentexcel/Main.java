package org.cgfalcon.fluentexcel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.cgfalcon.fluentexcel.render.Render;
import org.cgfalcon.fluentexcel.render.XlsxRender;

import java.io.FileOutputStream;

/**
 * User: falcon.chu
 * Date: 13-9-10
 * Time: 下午4:42
 */
public class Main {


    public static void main(String[] args) {
        Render render = new XlsxRender();
        ExcelBuilder excelBuilder = new ExcelBuilder();

        /* 使用ExcelBuilder 创建样式 */
        CellStyle styleBlack = excelBuilder
                .createCellStyle()
                    .withRender(render)
                    .useFgColor((short) 0, (short) 0, (short) 0)
                    .createFont()
                        .boldWeight((short)8).fontName("微软雅黑").italic().size((short)14)
                    .fontOver()
                .cellStyleOver();

        CellStyle styleRed = excelBuilder
                .createCellStyle()
                    .withRender(render)
                    .useFgColor((short) 254, (short) 0, (short) 0)
                    .createFont()
                        .boldWeight((short)8).fontName("微软雅黑").italic().size((short)14)
                    .fontOver()
                .cellStyleOver();

        /* 使用ExcelBuilder 创建文档 */
        excelBuilder
                .createDoc().docName("fluentTest2").type("xlsx").withDocRender(render)
                    .createSheet().sheetName("Hello")
                        .createBlock().fromRow(2)
                            .createRow().fromCol(1)
                                .createCell().content("X").withStyle(styleBlack).cellOver()
                                .createCell().content("X").withStyle(styleRed).cellOver()
                            .rowOver()
                        .blockOver()
                    .sheetOver()
                .saveTo("d:/app/tmp/excel")
                .rendDoc();

//        excelBuilder
//                .createDoc().docName("fluentTest2").type("xlsx").withDocRender(render)
//                .createSheet().sheetName("Hello")
//                        .createBlock().fromRow(2)
//                            .createRow()
//                                .createCell().content("世界, 你好").withStyle(styleBlack).cellOver()
//                            .rowOver()
//                        .blockOver()
//                        .createBlock().fromRow(5)
//                            .createRow()
//                                .createCell().content("Excel is bad").type(Cell.CELL_TYPE_STRING).cellOver()
//                            .rowOver()
//                        .blockOver()
//                .sheetOver()
//                .rendDoc();
//        fillColorTest();
    }


    /**
     *
     * 单元格颜色填充
     */
    public static void fillColorTest(){
        String file = "d:/app/tmp/fillColortest.xls";
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow((short) 1);

        // Aqua background
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Cell cell = row.createCell((short) 1);
        cell.setCellValue("X");
        cell.setCellStyle(style);

        // Orange "foreground", foreground being the fill foreground not the font color.
        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cell = row.createCell((short) 2);
        cell.setCellValue("X");
        cell.setCellStyle(style);

        FileOutputStream fileout;
        try {
            fileout = new FileOutputStream(file);
            wb.write(fileout);
            fileout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
