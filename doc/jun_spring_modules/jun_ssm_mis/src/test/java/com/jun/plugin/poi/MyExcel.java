package com.jun.plugin.poi;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;

// code run against the jakarta-poi-1.5.0-FINAL-20020506.jar.
public class MyExcel {

	static public void main(String[] args) throws Exception {
		// －－－－－－－－－－－－在xls中写入数据
		FileOutputStream fos = new FileOutputStream("e:\\text.xls");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "first sheet");
		HSSFRow row = s.createRow(0);
		HSSFCell cell = row.createCell((short) 0);
		HSSFRichTextString hts = new HSSFRichTextString("nihao");
		cell.setCellValue(hts);
		wb.write(fos);
		fos.flush();
		fos.close();
		// －－－－－－－－－－－－从xls读出数据
		wb = new HSSFWorkbook(new FileInputStream("e:\\text.xls"));
		s = wb.getSheetAt(0);
		HSSFRow r = s.getRow(0);
		cell = r.getCell((short) 0);
		if (cell.getCellType() == CellType.STRING) {
			System.out.println(cell.getRichStringCellValue());
		}
		// －－－－－－－－－－－－从doc读出数据
		FileInputStream in = new FileInputStream("e:\\text.doc");
		WordExtractor extractor = new WordExtractor(in);
		String text = extractor.getText();
		// 对DOC文件进行提取
		System.out.println(text);
		in.close();
		// ------------------在doc中写入

		byte[] a = new String("看见了！").getBytes();
		ByteArrayInputStream bs = new ByteArrayInputStream(a);
		POIFSFileSystem fs = new POIFSFileSystem();
		///////////////////////////////////
		DirectoryEntry directory = fs.getRoot();
		DocumentEntry de = directory.createDocument("WordDocument", bs);
		// 以上两句代码不能省略，否则输出的是乱码
		fos = new FileOutputStream("e:\\text.doc");
		fs.writeFilesystem(fos);
		bs.close();
		fos.flush();
		fos.close();
	}
}
