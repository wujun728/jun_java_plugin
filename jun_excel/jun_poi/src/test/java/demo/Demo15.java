package demo;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Demo15 {

	public static void main(String[] args) throws Exception{
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
		
		FileOutputStream fileOut=new FileOutputStream("c:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
