package demo;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Demo12 {

	public static void main(String[] args) throws Exception{
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
		
		FileOutputStream fileOut=new FileOutputStream("c:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
