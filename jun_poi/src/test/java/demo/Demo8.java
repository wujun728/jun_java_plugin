package demo;

import java.io.FileOutputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Demo8 {

	public static void main(String[] args) throws Exception{
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		Sheet sheet=wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		Row row=sheet.createRow(2); // ����һ����
		row.setHeightInPoints(30);
		
		createCell(wb, row, (short)0, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_BOTTOM);
		createCell(wb, row, (short)1, HSSFCellStyle.ALIGN_FILL, HSSFCellStyle.VERTICAL_CENTER);
		createCell(wb, row, (short)2, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP);
		createCell(wb, row, (short)3, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP);
		
		FileOutputStream fileOut=new FileOutputStream("c:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	/**
	 * ����һ����Ԫ��Ϊ���趨ָ���Ķ��䷽ʽ
	 * @param wb ������
	 * @param row ��
	 * @param column  ��
	 * @param halign  ˮƽ������䷽ʽ
	 * @param valign  ��ֱ������䷽ʽ
	 */
	private static void createCell(Workbook wb,Row row,short column,short halign,short valign){
		Cell cell=row.createCell(column);  // ������Ԫ��
		cell.setCellValue(new HSSFRichTextString("Align It"));  // ����ֵ
		CellStyle cellStyle=wb.createCellStyle(); // ������Ԫ����ʽ
		cellStyle.setAlignment(halign);  // ���õ�Ԫ��ˮƽ������䷽ʽ
		cellStyle.setVerticalAlignment(valign); // ���õ�Ԫ��ֱ������䷽ʽ
		cell.setCellStyle(cellStyle); // ���õ�Ԫ����ʽ
	}
	

}
