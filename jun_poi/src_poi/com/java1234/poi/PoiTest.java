package com.java1234.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

public class PoiTest {

	public static void main(String[] args) throws Exception {
//		createXls();
//		createCell();
//		cellStyle();
//		createCell2();
//		readExcel();
//		readSheet();
//		createWorkbook();
//		createWorkbook2();
//		createCellStyleBackgroundColor();
//		MergedRegion();
//		setFontName();
//		createCell1();
//		createCellStyle();
//		setDataFormat();
	}
	
	@Test
	public  void setDataFormat() throws Exception{
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		Sheet sheet=wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		CellStyle style;
		DataFormat format=wb.createDataFormat();
		Row row;
		Cell cell;
		short rowNum=0;
		short colNum=0;
		
		row=sheet.createRow(rowNum++);
		cell=row.createCell(colNum);
		cell.setCellValue(111111.25);
		
		style=wb.createCellStyle();
		style.setDataFormat(format.getFormat("0.0")); // �������ݸ�ʽ
		cell.setCellStyle(style);
		
		row=sheet.createRow(rowNum++);
		cell=row.createCell(colNum);
		cell.setCellValue(1111111.25);
		style=wb.createCellStyle();
		style.setDataFormat(format.getFormat("#,##0.000"));
		cell.setCellStyle(style);
		
		FileOutputStream fileOut=new FileOutputStream("D:\\abcd.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
	public static void createCellStyle() throws Exception{
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		Sheet sheet=wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		Row row=sheet.createRow(2); // ����һ����
		Cell cell=row.createCell(2);
		cell.setCellValue("��Ҫ���� \n �ɹ�����");
		
		CellStyle cs=wb.createCellStyle();
		// ���ÿ��Ի���
		cs.setWrapText(true);
		cell.setCellStyle(cs);
		
		// �������еĸ߶�
		row.setHeightInPoints(2*sheet.getDefaultRowHeightInPoints());
		// ������Ԫ����
		sheet.autoSizeColumn(2);
		
		FileOutputStream fileOut=new FileOutputStream("D:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
	public static void createCell1() throws Exception{
		InputStream inp=new FileInputStream("D:\\������.xls");
		POIFSFileSystem fs=new POIFSFileSystem(inp);
		Workbook wb=new HSSFWorkbook(fs);
		Sheet sheet=wb.getSheetAt(0);  // ��ȡ��һ��Sheetҳ
		Row row=sheet.getRow(0); // ��ȡ��һ��
		Cell cell=row.getCell(0); // ��ȡ��Ԫ��
		if(cell==null){
			cell=row.createCell(3);
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue("���Ե�Ԫ��");
		
		FileOutputStream fileOut=new FileOutputStream("D:\\������2.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	public static void setFontName() throws Exception{
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		Sheet sheet=wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		Row row=sheet.createRow(1); // ����һ����
		
		// ����һ�����崦����
		Font font=wb.createFont();
		font.setFontHeightInPoints((short)24);
		font.setFontName("Courier New");
		font.setItalic(true);
		font.setStrikeout(true);
		
		CellStyle style=wb.createCellStyle();
		style.setFont(font);
		
		Cell cell=row.createCell((short)1);
		cell.setCellValue("This is test of fonts");
		cell.setCellStyle(style);
		
		FileOutputStream fileOut=new FileOutputStream("D:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
	public static void MergedRegion() throws Exception{
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		Sheet sheet=wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		Row row=sheet.createRow(1); // ����һ����
		
		Cell cell=row.createCell(1);
		cell.setCellValue("��Ԫ��ϲ�����");
		
		sheet.addMergedRegion(new CellRangeAddress(
				1, // ��ʼ��
				2, // ������
				1, // ��ʵ��
				2  // ������
		));
		FileOutputStream fileOut=new FileOutputStream("D:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
	public static void createCellStyleBackgroundColor() throws Exception{
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		Sheet sheet=wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		Row row=sheet.createRow(1); // ����һ����
		
		Cell cell=row.createCell(1);
		cell.setCellValue("XX");
		CellStyle cellStyle=wb.createCellStyle();
		cellStyle.setFillBackgroundColor(IndexedColors.AQUA.getIndex()); // ����ɫ
		cellStyle.setFillPattern(CellStyle.BIG_SPOTS);  
		cell.setCellStyle(cellStyle);
		
		
		Cell cell2=row.createCell(2);
		cell2.setCellValue("YYY");
		CellStyle cellStyle2=wb.createCellStyle();
		cellStyle2.setFillForegroundColor(IndexedColors.RED.getIndex()); // ǰ��ɫ
		cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);  
		cell2.setCellStyle(cellStyle2);
		
		FileOutputStream fileOut=new FileOutputStream("D:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
	/**
	 * ���ñ߿�
	 */
	public static void createWorkbook2() throws Exception{
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		Sheet sheet=wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		Row row=sheet.createRow(1); // ����һ����
		
		Cell cell=row.createCell(1); // ����һ����Ԫ��
		cell.setCellValue(4);
		
		CellStyle cellStyle=wb.createCellStyle(); 
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // �ײ��߿�
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // �ײ��߿���ɫ
		
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);  // ��߱߿�
		cellStyle.setLeftBorderColor(IndexedColors.GREEN.getIndex()); // ��߱߿���ɫ
		
		cellStyle.setBorderRight(CellStyle.BORDER_THIN); // �ұ߱߿�
		cellStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());  // �ұ߱߿���ɫ
		
		cellStyle.setBorderTop(CellStyle.BORDER_MEDIUM_DASHED); // �ϱ߱߿�
		cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());  // �ϱ߱߿���ɫ
		
		cell.setCellStyle(cellStyle);
		FileOutputStream fileOut=new FileOutputStream("D:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
	/**
	 * ����
	 * @throws Exception
	 */
	public static void createWorkbook() throws Exception{
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		Sheet sheet=wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		Row row=sheet.createRow(2); // ����һ����
		row.setHeightInPoints(30);
		
		createCell(wb, row, (short)0, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_BOTTOM);
		createCell(wb, row, (short)1, HSSFCellStyle.ALIGN_FILL, HSSFCellStyle.VERTICAL_CENTER);
		createCell(wb, row, (short)2, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP);
		createCell(wb, row, (short)3, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP);
		
		FileOutputStream fileOut=new FileOutputStream("D:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	/**
	 * ����һ����Ԫ��Ϊ���趨ָ���Ķ��䷽ʽ
	 * @param wb ������
	 * @param row ��
	 * @param column  ��
	 * @param halign  ˮƽ������䷽ʽ
	 * @param valign  ��ֱ������䷽ʽ
	 */
	private static void createCell(Workbook wb,Row row,short column,short halign,short valign){
		Cell cell=row.createCell(column);  // ������Ԫ��
		cell.setCellValue(new HSSFRichTextString("Align It"));  // ����ֵ
		CellStyle cellStyle=wb.createCellStyle(); // ������Ԫ����ʽ
		cellStyle.setAlignment(halign);  // ���õ�Ԫ��ˮƽ������䷽ʽ
		cellStyle.setVerticalAlignment(valign); // ���õ�Ԫ��ֱ������䷽ʽ
		cell.setCellStyle(cellStyle); // ���õ�Ԫ����ʽ
	}
	
	
	
	public static void readSheet() throws Exception{
		InputStream is=new FileInputStream("D:\\������.xls");
		POIFSFileSystem fs=new POIFSFileSystem(is);
		HSSFWorkbook wb=new HSSFWorkbook(fs);
		ExcelExtractor excelExtractor=new ExcelExtractor(wb);
		excelExtractor.setIncludeSheetNames(false);// ���ǲ���ҪSheetҳ������
		System.out.println(excelExtractor.getText());
	}
	
	
	public static void readExcel() throws Exception{
		InputStream is=new FileInputStream("D:\\������.xls");
		POIFSFileSystem fs=new POIFSFileSystem(is);
		HSSFWorkbook wb=new HSSFWorkbook(fs);
		HSSFSheet hssfSheet=wb.getSheetAt(0); // ��ȡ��һ��Sheetҳ
		if(hssfSheet==null){
			return;
		}
		// ������Row
		for(int rowNum=0;rowNum<=hssfSheet.getLastRowNum();rowNum++){
			HSSFRow hssfRow=hssfSheet.getRow(rowNum);
			if(hssfRow==null){
				continue;
			}
			// ������Cell
			for(int cellNum=0;cellNum<=hssfRow.getLastCellNum();cellNum++){
				HSSFCell hssfCell=hssfRow.getCell(cellNum);
				if(hssfCell==null){
					continue;
				}
				System.out.print(" "+getValue(hssfCell));
			}
			System.out.println();
		}
	}
	
	private static String getValue(HSSFCell hssfCell){
		if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
			return String.valueOf(hssfCell.getBooleanCellValue());
		}else if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
			return String.valueOf(hssfCell.getNumericCellValue());
		}else{
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
	
	
	public static void createCell2() throws Exception{
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		Sheet sheet=wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		Row row=sheet.createRow(0); // ����һ����
		Cell cell=row.createCell(0); // ����һ����Ԫ��  ��1��
		cell.setCellValue(new Date());  // ����Ԫ������ֵ
		
		row.createCell(1).setCellValue(1);
		row.createCell(2).setCellValue("һ���ַ���");
		row.createCell(3).setCellValue(true);
		row.createCell(4).setCellValue(HSSFCell.CELL_TYPE_NUMERIC);
		row.createCell(5).setCellValue(false);
		
		FileOutputStream fileOut=new FileOutputStream("D:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
	
	public static void cellStyle() throws Exception{
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		Sheet sheet=wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		Row row=sheet.createRow(0); // ����һ����
		Cell cell=row.createCell(0); // ����һ����Ԫ��  ��1��
		cell.setCellValue(new Date());  // ����Ԫ������ֵ
		
		CreationHelper createHelper=wb.getCreationHelper();
		CellStyle cellStyle=wb.createCellStyle(); //��Ԫ����ʽ��
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyy-mm-dd hh:mm:ss"));
		cell=row.createCell(1); // �ڶ���
		cell.setCellValue(new Date());
		cell.setCellStyle(cellStyle);
		
		cell=row.createCell(2);  // ������
		cell.setCellValue(Calendar.getInstance());
		cell.setCellStyle(cellStyle);
		
		FileOutputStream fileOut=new FileOutputStream("D:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
	public static void  createXls() throws IOException {
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		wb.createSheet("�ڶ���Sheetҳ");  // �����ڶ���Sheetҳ
		FileOutputStream fileOut=new FileOutputStream("D:\\��Poi�������Sheetҳ.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
	public static void createCell() throws Exception{
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		Sheet sheet=wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		Row row=sheet.createRow(0); // ����һ����
		Cell cell=row.createCell(0); // ����һ����Ԫ��  ��1��
		cell.setCellValue(1);  // ����Ԫ������ֵ
		
		row.createCell(1).setCellValue(1.2);   // ����һ����Ԫ�� ��2�� ֵ��1.2
		
		row.createCell(2).setCellValue("����һ���ַ�������"); // ����һ����Ԫ�� ��3�� ֵΪһ���ַ���
		
		row.createCell(3).setCellValue(false);  // ����һ����Ԫ�� ��4�� ֵΪ��������
		
		FileOutputStream fileOut=new FileOutputStream("D:\\��Poi�������Cell.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
