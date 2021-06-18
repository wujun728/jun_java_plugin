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

public class Demo10 {

	public static void main(String[] args) throws Exception{
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
		
		FileOutputStream fileOut=new FileOutputStream("c:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
