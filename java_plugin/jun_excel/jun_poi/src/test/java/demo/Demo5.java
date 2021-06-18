package demo;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Demo5 {

	public static void main(String[] args) throws Exception{
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
		
		FileOutputStream fileOut=new FileOutputStream("c:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
