package com.jun.plugin.poi.test.other;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class MyTest {
	@Test
	public void testme() throws IOException {
		
		  Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();

	        Sheet sheet = wb.createSheet();
	        Row row = sheet.createRow((short) 2);
	        row.setHeightInPoints(30);

	        createCell(wb, row, (short) 0, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_BOTTOM);
	        createCell(wb, row, (short) 1, CellStyle.ALIGN_CENTER_SELECTION, CellStyle.VERTICAL_BOTTOM);
	        createCell(wb, row, (short) 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER);
	        createCell(wb, row, (short) 3, CellStyle.ALIGN_GENERAL, CellStyle.VERTICAL_CENTER);
	        createCell(wb, row, (short) 4, CellStyle.ALIGN_JUSTIFY, CellStyle.VERTICAL_JUSTIFY);
	        createCell(wb, row, (short) 5, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_TOP);
	        createCell(wb, row, (short) 6, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_TOP);
	        // Write the output to a file
	        
	        FileOutputStream fileOut = new FileOutputStream("xssf-align.xlsx");
	        wb.write(fileOut);
	        
	        fileOut.close();
	}
	/**
     * Creates a cell and aligns it a certain way.
     *
     * @param wb     the workbook
     * @param row    the row to create the cell in
     * @param column the column number to create the cell in
     * @param halign the horizontal alignment for the cell.
     */
    private  void createCell(Workbook wb, Row row, short column, short halign, short valign) {
        Cell cell = row.createCell(column);
        cell.setCellValue("Align It");
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }
    
    @Test
    public void test2() throws IOException{
    	 Workbook wb = new HSSFWorkbook();
    	    Sheet sheet = wb.createSheet("new sheet");

    	    // Create a row and put some cells in it. Rows are 0 based.
    	    Row row = sheet.createRow(1);

    	    // Create a cell and put a value in it.
    	    

    	    // Style the cell with borders all around.
    	    CellStyle style = wb.createCellStyle();
    	    
    	    //     style.setFillBackgroundColor(IndexedColors.AUTOMATIC.getIndex());
    	   style.setFillPattern(CellStyle.SOLID_FOREGROUND);
    	   style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
    	   
    	    Font font = wb.createFont();
    	    font.setFontHeightInPoints((short)24);
    	    font.setFontName("Courier New");
    	    font.setItalic(true);
    	    font.setStrikeout(true);
    	    style.setFont(font);
    	    CellUtil.createCell(row, 1, "nihao",style);
    	    //style.setFont(font);
    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
    	    wb.write(fileOut);
    	    fileOut.close();
    }
    private Map<String, String> map=new HashMap<String, String>();
    @Test
    public void before(){
    	map.put("a", "aa");
    }
    
    @Test
    public void testLong() throws EncryptedDocumentException, InvalidFormatException, IOException{
    	 Workbook wb = new HSSFWorkbook();
    	    Sheet sheet = wb.createSheet("new sheet");

    	    Row row = sheet.createRow((short) 1);
    	    Cell cell = row.createCell((short) 1);
    	    cell.setCellValue("This is a test of merging");

    	    sheet.addMergedRegion(new CellRangeAddress(
    	            1, //first row (0-based)
    	            1, //last row  (0-based)
    	            1, //first column (0-based)
    	            2  //last column  (0-based)
    	    ));

    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream(new File("E:/test/gzb.xls"));
    	    wb.write(fileOut);
    	    fileOut.close();
    	 
    }
}
