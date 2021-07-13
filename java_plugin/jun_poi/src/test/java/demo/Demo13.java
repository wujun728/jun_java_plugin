package demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Demo13 {

	public static void main(String[] args) throws Exception{
		InputStream inp=new FileInputStream("c:\\������.xls");
		POIFSFileSystem fs=new POIFSFileSystem(inp);
		Workbook wb=new HSSFWorkbook(fs);
		Sheet sheet=wb.getSheetAt(0);  // ��ȡ��һ��Sheetҳ
		Row row=sheet.getRow(0); // ��ȡ��һ��
		Cell cell=row.getCell(0); // ��ȡ��Ԫ��
		if(cell==null){
			cell=row.createCell(3);
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue("���Ե�Ԫ��");
		
		FileOutputStream fileOut=new FileOutputStream("c:\\������.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
