package com.jun.plugin.poi;

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
		//�ڴ���
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("first sheet");
		wb.createSheet("second sheet");
		//������
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(false);
		row.createCell(1).setCellValue(Calendar.getInstance());
		row.createCell(2).setCellValue(new Date());
		row.createCell(3).setCellValue(1234567890.9870654f);
		String desc = "dddddddddddddddddddddddddddddddddddddddddddddddddddddddd";
		row.createCell(4).setCellValue(new HSSFRichTextString(desc));
		
		//��ʽ�����
		HSSFDataFormat format = wb.createDataFormat();//������ʽ����
		HSSFCellStyle style = wb.createCellStyle();//������ʽ����
		
		//���ø�ʽ
		style.setDataFormat(format.getFormat("yyyy-MM-dd hh:mm:ss"));
		cell = row.getCell(1);
		cell.setCellStyle(style);//��cellӦ����ʽ
		row.getCell(2).setCellStyle(style);
		
		//�����п�
		sheet.setColumnWidth(1, 5000);//��λ:1/20
		sheet.autoSizeColumn(2);
		
		//���ָ�ʽ��???
		style = wb.createCellStyle();
		style.setDataFormat(format.getFormat("#,###.0000"));
		row.getCell(3).setCellStyle(style);
		
		//�ı��Զ�����
		sheet.setColumnWidth(4, 5000);
		style = wb.createCellStyle();
		style.setWrapText(true);//�����ı�
		row.getCell(4).setCellStyle(style);
		
		//�����ı����뷽ʽ
		sheet.setColumnWidth(0, 5000);
		row = sheet.createRow(1);
		row.createCell(0).setCellValue("����");
		row.createCell(1).setCellValue("����");
		row.createCell(2).setCellValue("����");
		
		//���뷽ʽ--����
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);//�����
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//�϶���
		row.getCell(0).setCellStyle(style);
		
		//���뷽ʽ--����
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//�����
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//�϶���
		row.getCell(1).setCellStyle(style);
		
		//���뷽ʽ--����
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//�����
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);//�϶���
		row.getCell(2).setCellStyle(style);
		//�����и�
		row.setHeightInPoints(50);
		
		//��������
		style = row.getCell(1).getCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontName("����");
		font.setFontHeightInPoints((short)18);
		font.setColor(HSSFColor.RED.index);
		style.setFont(font);
		
		//�ı���ת
		style.setRotation((short)-30);
		
		//���ñ߿�
		row = sheet.createRow(2);
		cell = row.createCell(0);
		style = wb.createCellStyle();
		style.setBorderTop(HSSFCellStyle.BORDER_DASH_DOT_DOT);
		style.setTopBorderColor(HSSFColor.BLUE.index);
		cell.setCellStyle(style);
		
		//������
		row = sheet.createRow(3);
		row.createCell(0).setCellValue(20);
		row.createCell(1).setCellValue(34.78);
		row.createCell(2).setCellValue(45.98);
		row.createCell(3).setCellFormula("sum(A4:C4)");
		
		//�����ƶ���
		sheet.shiftRows(1, 3, 2);
		
		//��ִ���
		//1000:��ര��Ŀ��
		//2000:�ϲര��ĸ߶�
		//3:�Ҳര��ʼ��ʾ���е�����
		//4:�²ര��ʼ��ʾ���е�����
		//1:������ĸ������
		sheet.createSplitPane(1000, 2000, 3, 4, 1);
		
		//���ᴰ��
		sheet.createFreezePane(1, 2, 3, 4);
		wb.write(new FileOutputStream("D:/poi.xls"));
	}
}
