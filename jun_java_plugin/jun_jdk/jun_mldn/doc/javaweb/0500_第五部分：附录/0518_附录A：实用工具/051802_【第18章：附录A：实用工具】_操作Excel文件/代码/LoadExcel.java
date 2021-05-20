package org.lxh.exceldemo;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;

public class LoadExcel {
	public static void main(String[] args) throws Exception {
		File inFile = new File("D:" + File.separator + "mldn.xls");
		Workbook workbook = Workbook.getWorkbook(inFile);
		Sheet sheet[] = workbook.getSheets();
		for (int x = 0; x < sheet.length; x++) {
			for (int y = 0; y < sheet[x].getRows(); y++) {
				for (int z = 0; z < sheet[x].getColumns(); z++) {
					String content = sheet[x].getCell(z, y).getContents();
					System.out.print(content + "\t\t") ;
				}
				System.out.println() ;
			}
		}
	}
}
