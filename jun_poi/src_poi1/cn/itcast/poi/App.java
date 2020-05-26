package cn.itcast.poi;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class App {

	public static void main(String[] args) throws Exception {
		//内存中
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("first sheet");
		wb.createSheet("second sheet");
		//创建行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(false);
		row.createCell(1).setCellValue(Calendar.getInstance());
		row.createCell(2).setCellValue(new Date());
		row.createCell(3).setCellValue(1234567890.9870654f);
		String desc = "dddddddddddddddddddddddddddddddddddddddddddddddddddddddd";
		row.createCell(4).setCellValue(new HSSFRichTextString(desc));
		
		//格式化数据
		HSSFDataFormat format = wb.createDataFormat();//创建格式对象
		HSSFCellStyle style = wb.createCellStyle();//创建样式对象
		
		//设置格式
		style.setDataFormat(format.getFormat("yyyy-MM-dd hh:mm:ss"));
		cell = row.getCell(1);
		cell.setCellStyle(style);//对cell应用样式
		row.getCell(2).setCellStyle(style);
		
		//设置列宽
		sheet.setColumnWidth(1, 5000);//单位:1/20
		sheet.autoSizeColumn(2);
		
		//数字格式化???
		style = wb.createCellStyle();
		style.setDataFormat(format.getFormat("#,###.0000"));
		row.getCell(3).setCellStyle(style);
		
		//文本自动换行
		sheet.setColumnWidth(4, 5000);
		style = wb.createCellStyle();
		style.setWrapText(true);//回绕文本
		row.getCell(4).setCellStyle(style);
		
		//设置文本对齐方式
		sheet.setColumnWidth(0, 5000);
		row = sheet.createRow(1);
		row.createCell(0).setCellValue("左上");
		row.createCell(1).setCellValue("中中");
		row.createCell(2).setCellValue("右下");
		
		//对齐方式--左上
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);//左对齐
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//上对齐
		row.getCell(0).setCellStyle(style);
		
		//对齐方式--中中
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左对齐
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上对齐
		row.getCell(1).setCellStyle(style);
		
		//对齐方式--右下
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//左对齐
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);//上对齐
		row.getCell(2).setCellStyle(style);
		//设置行高
		row.setHeightInPoints(50);
		
		//设置字体
		style = row.getCell(1).getCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)18);
		font.setColor(HSSFColor.RED.index);
		style.setFont(font);
		
		//文本旋转
		style.setRotation((short)-30);
		
		//设置边框
		row = sheet.createRow(2);
		cell = row.createCell(0);
		style = wb.createCellStyle();
		style.setBorderTop(HSSFCellStyle.BORDER_DASH_DOT_DOT);
		style.setTopBorderColor(HSSFColor.BLUE.index);
		cell.setCellStyle(style);
		
		//计算列
		row = sheet.createRow(3);
		row.createCell(0).setCellValue(20);
		row.createCell(1).setCellValue(34.78);
		row.createCell(2).setCellValue(45.98);
		row.createCell(3).setCellFormula("sum(A4:C4)");
		
		//整体移动行
		sheet.shiftRows(1, 3, 2);
		
		//拆分窗格
		//1000:左侧窗格的宽度
		//2000:上侧窗格的高度
		//3:右侧窗格开始显示的列的索引
		//4:下侧窗格开始显示的行的索引
		//1:激活的哪个面板区
		sheet.createSplitPane(1000, 2000, 3, 4, 1);
		
		//冻结窗口
		sheet.createFreezePane(1, 2, 3, 4);
		wb.write(new FileOutputStream("f:/poi.xls"));
	}
}
