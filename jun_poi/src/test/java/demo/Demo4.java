package demo;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Demo4 {

	public static void main(String[] args) throws Exception{
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
		
		FileOutputStream fileOut=new FileOutputStream("c:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
