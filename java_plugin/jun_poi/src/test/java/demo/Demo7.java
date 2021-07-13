package demo;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Demo7 {

	public static void main(String[] args) throws Exception{
		InputStream is=new FileInputStream("c:\\��������.xls");
		POIFSFileSystem fs=new POIFSFileSystem(is);
		HSSFWorkbook wb=new HSSFWorkbook(fs);
		
		ExcelExtractor excelExtractor=new ExcelExtractor(wb);
		excelExtractor.setIncludeSheetNames(false);// ���ǲ���ҪSheetҳ������
		System.out.println(excelExtractor.getText());
	}
	

}
