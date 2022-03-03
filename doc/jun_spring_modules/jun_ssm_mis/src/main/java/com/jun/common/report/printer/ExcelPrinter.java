package com.jun.common.report.printer;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import com.jun.common.report.CssEngine;
import com.jun.common.report.Report;
import com.jun.common.report.ReportBody;
import com.jun.common.report.ReportException;
import com.jun.common.report.Table;
import com.jun.common.report.TableCell;
import com.jun.common.report.TableRow;
import com.jun.common.report.cross.CrossTable;

@SuppressWarnings("unchecked")
public class ExcelPrinter implements Printer {
    public ExcelPrinter() {}
    
    public void print(Report r, OutputStream result) throws ReportException,
            IOException {
        print(r, new ExcelCss() {
            public void init(HSSFWorkbook wb) {}
        }, result);
    }

    public void print(Report r, ExcelCss css, OutputStream result)
            throws ReportException, IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        css.init(wb);
        wb.setSheetName(0,"page");
        sheet.setDefaultColumnWidth(css.getDefaultColumnWidth());
        HSSFCellStyle style=wb.createCellStyle();
        int currRow = 0;

        if (r.getHeaderTable() != null) {
            currRow = print(r.getHeaderTable(), css, sheet, currRow,  wb,style);
        }
        if (r.getBody() != null) {
            currRow = print(r.getBody(), css, sheet, currRow, wb,style);
        }
        if (r.getFooterTable() != null) {
            currRow = print(r.getFooterTable(), css, sheet, currRow,  wb,style);
        }
        HSSFFooter footer = sheet.getFooter();
        footer.setCenter(HSSFFooter.page() + " / " + HSSFFooter.numPages());

        wb.write(result);
        leftStyle=null;
        rigthStyle=null;
    }

    public void print(Report[] r, ExcelCss css, OutputStream result)
            throws ReportException, IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle style=wb.createCellStyle();
        for (int i = 0; i < r.length; i++) {
            int currRow = 0;
            HSSFSheet sheet = wb.createSheet("Page "+(i+1));
            css.init(wb);
            sheet.setDefaultColumnWidth(css.getDefaultColumnWidth());

            if (r[i].getHeaderTable() != null) {
                currRow = print(r[i].getHeaderTable(), css, sheet, currRow,wb,style);
            }
            if (r[i].getBody() != null) {
                currRow = print(r[i].getBody(), css, sheet, currRow, wb,style);
            }
            if (r[i].getFooterTable() != null) {
                currRow = print(r[i].getFooterTable(), css, sheet, currRow, wb,style);
            }
        }
        wb.write(result);
        leftStyle=null;
        rigthStyle=null;
    }

    private HSSFFont cloneFont(HSSFWorkbook wb, HSSFFont f) {
        HSSFFont result = wb.createFont();
        result.setBold(f.getBold());
        result.setColor(f.getColor());
        result.setFontHeight(f.getFontHeight());
        result.setFontHeightInPoints(f.getFontHeightInPoints());
        result.setFontName(f.getFontName());
        result.setItalic(f.getItalic());
        result.setStrikeout(f.getStrikeout());
        result.setTypeOffset(f.getTypeOffset());
        result.setUnderline(f.getUnderline());
        return result;
    }

    private HSSFCellStyle cloneStyle(HSSFWorkbook wb, HSSFCellStyle s) {
        HSSFCellStyle result = wb.createCellStyle();
        result.setAlignment(s.getAlignment());
        result.setBorderBottom(s.getBorderBottom());
        result.setBorderLeft(s.getBorderLeft());
        result.setBorderRight(s.getBorderRight());
        result.setBorderTop(s.getBorderTop());
        result.setBottomBorderColor(s.getBottomBorderColor());
        result.setDataFormat(s.getDataFormat());
        result.setFillBackgroundColor(s.getFillBackgroundColor());
        result.setFillForegroundColor(s.getFillForegroundColor());
        result.setFillPattern(s.getFillPattern());
        result.setHidden(s.getHidden());
        result.setIndention(s.getIndention());
        result.setLeftBorderColor(s.getLeftBorderColor());
        result.setLocked(s.getLocked());
        result.setRightBorderColor(s.getRightBorderColor());
        result.setRotation(s.getRotation());
        result.setTopBorderColor(s.getTopBorderColor());
        result.setVerticalAlignment(s.getVerticalAlignment());
        result.setWrapText(s.getWrapText());
        return result;
    }
    
    private void copyStyle(HSSFCellStyle result,HSSFCellStyle s) {        
        result.setAlignment(s.getAlignment());
        result.setBorderBottom(s.getBorderBottom());
        result.setBorderLeft(s.getBorderLeft());
        result.setBorderRight(s.getBorderRight());
        result.setBorderTop(s.getBorderTop());
        result.setBottomBorderColor(s.getBottomBorderColor());
        result.setDataFormat(s.getDataFormat());
        result.setFillBackgroundColor(s.getFillBackgroundColor());
        result.setFillForegroundColor(s.getFillForegroundColor());
        result.setFillPattern(s.getFillPattern());
        result.setHidden(s.getHidden());
        result.setIndention(s.getIndention());
        result.setLeftBorderColor(s.getLeftBorderColor());
        result.setLocked(s.getLocked());
        result.setRightBorderColor(s.getRightBorderColor());
        result.setRotation(s.getRotation());
        result.setTopBorderColor(s.getTopBorderColor());
        result.setVerticalAlignment(s.getVerticalAlignment());
        result.setWrapText(s.getWrapText());        
    }

    public static HSSFCellStyle leftStyle;
    public static HSSFCellStyle rigthStyle;
    private void print(TableCell tableCell, ExcelCss css,
            HSSFCell cell, int rowNum, int colNum, HSSFSheet sheet,
            boolean haveBorder, HSSFWorkbook wb,HSSFCellStyle style) throws IOException,
            ReportException {
    	if (tableCell.getAlign()==TableCell.ALIGN_LEFT){
    		if(leftStyle==null){
    			leftStyle=wb.createCellStyle(); 
    		} else{
        		copyStyle(leftStyle,style);   
    		}
        	style=leftStyle;
        }
    	if (tableCell.getAlign()==TableCell.ALIGN_RIGHT) {
        	if(rigthStyle==null){
        		rigthStyle=wb.createCellStyle();        		
        	} else{
        		copyStyle(rigthStyle,style);        		
        	}
        	style = rigthStyle;
        }
//        if (haveBorder) {
//            style.setBorderBottom((short) 1);
//            style.setBorderLeft((short) 1);
//            style.setBorderRight((short) 1);
//            style.setBorderTop((short) 1);
//        } else {
//            style.setBorderBottom((short) 0);
//            style.setBorderLeft((short) 0);
//            style.setBorderRight((short) 0);
//            style.setBorderTop((short) 0);
//        }
//        style.setAlignment(getAlign(tableCell.getAlign()));        
//        style.setVerticalAlignment(getVAlign(tableCell.getValign()));

        cell.setCellStyle(style);
//        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        if (tableCell.getCssClass().equals(Report.CROSS_HEAD_HEAD_TYPE)) {
            String blank = "    ";
            String[] strs = PrinterUtil.getCrossHeadHeadContent(tableCell);
            StringBuffer content = new StringBuffer();
            for (int i = 0; i < strs.length; i++) {
                if (i != 0)
                    content.append("\n");
                for (int j = 0; j < strs.length - i - 1; j++) {
                    content.append(blank);
                }
                if (strs[i] != null)
                    content.append(strs[i]);
            }
            style.setWrapText(true);
            cell.setCellValue(content.toString());
        } else {
        	if(tableCell.getContent() instanceof Integer){
        		 Integer it =(Integer)tableCell.getContent();
//        		 cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC); 
        		 cell.setCellValue(it.intValue());
        	}else{
//        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            	cell.setCellValue( tableCell.getContent()!=null?tableCell.getContent().toString():"");   
        	}
        }
        if (!tableCell.isHidden()) {
            if (tableCell.getRowSpan() > 1 || tableCell.getColSpan() > 1) {
//                sheet.addMergedRegion(new Region(rowNum, (short) colNum, rowNum
//                        + tableCell.getRowSpan() - 1, (short) (colNum
//                        + tableCell.getColSpan() - 1)));
            }
        }
    }

    private short getAlign(int i) {    	
        switch (i) {
        case com.jun.common.report.Rectangle.ALIGN_LEFT:
            return 0;//CellStyle.ALIGN_LEFT;
        case com.jun.common.report.Rectangle.ALIGN_CENTER:
            return 0;//HSSFCellStyle.ALIGN_CENTER;
        case com.jun.common.report.Rectangle.ALIGN_RIGHT:
            return 0;//HSSFCellStyle.ALIGN_RIGHT;
        default:
            throw new RuntimeException("Exception at getAlign");
        }
    }

    private short getVAlign(int i) {
        switch (i) {
        case com.jun.common.report.Rectangle.VALIGN_TOP:
            return 0;//HSSFCellStyle.VERTICAL_TOP;
        case com.jun.common.report.Rectangle.VALIGN_MIDDLE:
            return 0;//HSSFCellStyle.VERTICAL_CENTER;
        case com.jun.common.report.Rectangle.VALIGN_BOTTOM:
            return 0;//HSSFCellStyle.VERTICAL_BOTTOM;
        default:
            throw new RuntimeException("Exception at getVAlign");
        }
    }

    private void print(TableRow tableRow, ExcelCss css,
            HSSFRow row, int rowNum, HSSFSheet sheet, boolean haveBorder,
            HSSFWorkbook wb,HSSFCellStyle style) throws ReportException, IOException {
    	HSSFCell cell = null;
        if (tableRow.getCell(0).getCssClass().equals(
                Report.CROSS_HEAD_HEAD_TYPE)) {
            CrossTable crossTab = (CrossTable) tableRow.getCell(0).getContent();
            row.setHeight((short) (row.getHeight() * (crossTab
                            .getColHeader().length
                            + crossTab.getRowHeader().length + 1)));
        }
        for (int j = 0; j < tableRow.getCellCount(); j++) {
            cell = row.createCell((short) j);
            print(tableRow.getCell(j), css, cell, rowNum, j, sheet, haveBorder,
                    wb,style);
        }
    }

    
    private int print(Table t, ExcelCss css,
            HSSFSheet sheet, int currRow, HSSFWorkbook wb,HSSFCellStyle style)
            throws ReportException, IOException {
        t = CssEngine.applyCss(t);

        HSSFRow row = null;
        int result = currRow;
        for (int i = 0; i < t.getRowCount(); i++) {
            row = sheet.createRow(currRow + i);
            print(t.getRow(i), css, row, currRow + i, sheet, t.getBorder()>0?true:false, wb,style);
            result++;
        }
        return result;
    }
    
    private int print(ReportBody body, ExcelCss css,
            HSSFSheet sheet, int currRow, HSSFWorkbook wb,HSSFCellStyle style)
            throws ReportException, IOException {
        Table data = body.getData().deepClone();
        Table header = body.getTableColHeader();
        header = CssEngine.applyCss(header);
        if (header != null) {
            for (int i = header.getRowCount() - 1; i >= 0; i--) {
                data.insertRow(0, header.getRow(i));
            }
        }
        return print(data, css, sheet, currRow, wb,style);
    }
}