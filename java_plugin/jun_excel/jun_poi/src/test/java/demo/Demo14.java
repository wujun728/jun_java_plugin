package demo;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class Demo14 {

	public static void main(String[] args) throws Exception{
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
		
		FileOutputStream fileOut=new FileOutputStream("c:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
