package org.lxh.exceldemo;

import java.io.File;
import java.util.Date;

import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class CreateFormatExcel {

	public static void main(String[] args) throws Exception {
		File outFile = new File("D:" + File.separator + "mldn.xls");
		WritableWorkbook workbook = Workbook.createWorkbook(outFile);
		WritableSheet sheet = workbook.createSheet("MLDN资料", 0);

		WritableFont font = new WritableFont(WritableFont.TAHOMA, 20);
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		Label lab = new Label(0, 0, "魔乐科技", cellFormat);
		sheet.addCell(lab);

		jxl.write.Number num = null ;
		num = new jxl.write.Number(1, 0, 9876543210.9876);
		sheet.addCell(num);

		cellFormat = new WritableCellFormat(NumberFormats.FLOAT);
		num = new jxl.write.Number(2, 0, 9876543210.9876,cellFormat);
		sheet.addCell(num) ;
		
		NumberFormat numFormat = new NumberFormat("#,##0.00") ;
		cellFormat = new WritableCellFormat(numFormat);
		num = new jxl.write.Number(3, 0, 9676543210.9876,cellFormat);
		sheet.addCell(num) ;
		
		DateTime dateTime = new DateTime(4,0,new Date()) ;
		sheet.addCell(dateTime) ;
		
		DateFormat dateFormat = new DateFormat("yyyy-MM-dd HH:mm:ss") ;
		cellFormat = new WritableCellFormat(dateFormat);
		dateTime = new DateTime(5,0,new Date(),cellFormat) ;
		sheet.addCell(dateTime) ;
		
		workbook.write();
		workbook.close();
	}
}
