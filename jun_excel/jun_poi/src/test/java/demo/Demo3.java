package demo;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Demo3 {

	public static void main(String[] args) throws Exception{
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		Sheet sheet=wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		Row row=sheet.createRow(0); // ����һ����
		Cell cell=row.createCell(0); // ����һ����Ԫ��  ��1��
		cell.setCellValue(1);  // ����Ԫ������ֵ
		
		row.createCell(1).setCellValue(1.2);   // ����һ����Ԫ�� ��2�� ֵ��1.2
		
		row.createCell(2).setCellValue("����һ���ַ�������"); // ����һ����Ԫ�� ��3�� ֵΪһ���ַ���
		
		row.createCell(3).setCellValue(false);  // ����һ����Ԫ�� ��4�� ֵΪ��������
		
		FileOutputStream fileOut=new FileOutputStream("c:\\��Poi�������Cell.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
