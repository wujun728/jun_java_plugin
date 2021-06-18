package demo;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class Demo1 {

	public static void main(String[] args) throws Exception {
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		FileOutputStream fileOut=new FileOutputStream("D:\\��Poi������Ĺ�����.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
