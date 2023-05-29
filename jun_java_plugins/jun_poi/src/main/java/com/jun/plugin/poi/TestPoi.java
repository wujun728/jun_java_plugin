package com.jun.plugin.poi;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
//import org.junit.Test;

import com.jun.plugin.datasource.DataSourceUtil;

public class TestPoi {

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
	
//	@Test
	public void createXls() throws Exception{
		//声明一个工作薄
		HSSFWorkbook wb = new HSSFWorkbook();
	   //声明表
		HSSFSheet sheet = wb.createSheet("第一个表");
		//声明行
		HSSFRow row = sheet.createRow(7);
		//声明列
		HSSFCell cel = row.createCell(3);
		//写入数据
		cel.setCellValue("你也好");
		
		
		FileOutputStream fileOut = new FileOutputStream("d:/b.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}
	
//	@Test
	public void export() throws Exception{
		//声明需要导出的数据库
		String dbName = "focus";
		//声明book
		HSSFWorkbook book = new HSSFWorkbook();
		//获取Connection,获取db的元数据
		Connection con = DataSourceUtil.getConn();
		//声明statemen
		Statement st = con.createStatement();
		//st.execute("use "+dbName);
		DatabaseMetaData dmd = con.getMetaData();
		//获取数据库有多少表
		ResultSet rs = dmd.getTables(dbName,dbName,null,new String[]{"TABLE"});
		//获取所有表名　－　就是一个sheet
		List<String> tables = new ArrayList<String>();
		while(rs.next()){
			String tableName = rs.getString("TABLE_NAME");
			tables.add(tableName);
		}
		for(String tableName:tables){
			HSSFSheet sheet = book.createSheet(tableName);
			//声明sql
			String sql = "select * from "+dbName+"."+tableName;
			//查询数据
			rs = st.executeQuery(sql);
			//根据查询的结果，分析结果集的元数据
			ResultSetMetaData rsmd = rs.getMetaData();
			//获取这个查询有多少行
			int cols = rsmd.getColumnCount();
			//获取所有列名
			//创建第一行
			HSSFRow row = sheet.createRow(0);
			for(int i=0;i<cols;i++){
				String colName = rsmd.getColumnName(i+1);
				//创建一个新的列
				HSSFCell cell = row.createCell(i);
				//写入列名
				cell.setCellValue(colName);
			}
			//遍历数据
			int index = 1;
			while(rs.next()){
				row = sheet.createRow(index++);
				//声明列
				for(int i=0;i<cols;i++){
					String val = rs.getString(i+1);
					//声明列
					HSSFCell cel = row.createCell(i);
					//放数据
					cel.setCellValue(val);
				}
			}
		}
		con.close();
		book.write(new FileOutputStream("d:/"+dbName+".xls"));
	}
}
