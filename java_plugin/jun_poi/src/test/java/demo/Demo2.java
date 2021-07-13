package demo;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class Demo2 {

	public static void main(String[] args) throws Exception {
		
		Workbook wb=new HSSFWorkbook(); // ����һ���µĹ�����
		wb.createSheet("��һ��Sheetҳ");  // ������һ��Sheetҳ
		wb.createSheet("�ڶ���Sheetҳ");  // �����ڶ���Sheetҳ
		FileOutputStream fileOut=new FileOutputStream("c:\\��Poi�������Sheetҳ.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
