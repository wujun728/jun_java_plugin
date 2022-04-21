package com.jun.plugin.jxl;
import java.io.File;//������
import java.io.IOException;
import java.util.Scanner;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
//import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
//import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
//import jxl.write.WriteException;
//import jxl.write.biff.RowsExceededException;

public class TextOperatorExcel {// ����Excel�ļ�����
	/**
	 * ����һ��Excel�ļ�
	 */
	public static void writeExcel(String fileName) {
		WritableWorkbook wwb = null;
		try {
			//����һ����д��Ĺ�����(Workbook)����
			wwb = Workbook.createWorkbook(new File(fileName));
		} catch (IOException e) {// �������쳣
			e.printStackTrace();
		}
		if (wwb != null) {
			// ����һ����д��Ĺ�����
			// Workbook��createSheet������������������һ���ǹ���������ƣ��ڶ����ǹ������ڹ������е�λ��
			WritableSheet ws = wwb.createSheet("sheet1", 0);
			for (int i = 0; i < 10; i++) {// ѭ����ӵ�Ԫ��
				for (int j = 0; j < 5; j++) {
					Label labelC = new Label(j, i, "���ǵ�" + (i + 1) + "�У���"
							+ (j + 1) + "��");
					try {
						ws.addCell(labelC);// �����ɵĵ�Ԫ����ӵ���������
					} catch (Exception e) {// �����쳣
						e.printStackTrace();
					}

				}
			}
			try {
				wwb.write();// ���ڴ���д���ļ���
				wwb.close();// ���ڴ���д���ļ���
			} catch (Exception e) {// �����쳣
				e.printStackTrace();
			}
		}
		System.out.println("����һ��Excel�ļ���"+fileName+"�ɹ�!");
	}
	
	/**
	 * ������д��
	 * @param fileName
	 * @throws Exception
	 */
	public static void writeContentToExcel(String fileName) throws Exception{
		File tempFile=new File(fileName);
		WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
		WritableSheet sheet = workbook.createSheet("TestCreateExcel", 0); 
		//һЩ��ʱ����������д��excel��
		Label l=null;
		jxl.write.Number n=null;
		jxl.write.DateTime d=null;

		//Ԥ�����һЩ����͸�ʽ��ͬһ��Excel����ò�Ҫ��̫���ʽ  ����  ��С  �Ӵ� ��б  �»��� ��ɫ
		WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE); 
		WritableCellFormat headerFormat = new WritableCellFormat (headerFont); 

		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
		WritableCellFormat titleFormat = new WritableCellFormat (titleFont); 

		WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); 
		WritableCellFormat detFormat = new WritableCellFormat (detFont); 

		NumberFormat nf=new NumberFormat("0.00000");  //����Number�ĸ�ʽ
		WritableCellFormat priceFormat = new WritableCellFormat (detFont, nf); 

		DateFormat df=new DateFormat("yyyy-MM-dd");//�������ڵ�
		WritableCellFormat dateFormat = new WritableCellFormat (detFont, df); 

		l=new Label(0, 0, "���ڲ��Ե�Excel�ļ�", headerFormat);//����һЩ��Ԫ���ټӵ�sheet��
		sheet.addCell(l);
		//��ӱ���
		int column=0;
		l=new Label(column++, 2, "����", titleFormat);
		sheet.addCell(l);
		l=new Label(column++, 2, "����", titleFormat);
		sheet.addCell(l);
		l=new Label(column++, 2, "����", titleFormat);
		sheet.addCell(l);
		l=new Label(column++, 2, "�۸�", titleFormat);
		sheet.addCell(l);
		//�������
		int i=0;
		column=0;
		l=new Label(column++, i+3, "���� "+i, detFormat);
		sheet.addCell(l);
		d=new DateTime(column++, i+3, new java.util.Date(), dateFormat);
		sheet.addCell(d);
		l=new Label(column++, i+3, "CNY", detFormat);
		sheet.addCell(l);
		n=new jxl.write.Number(column++, i+3, 5.678, priceFormat);
		sheet.addCell(n);
		i++;
		column=0;
		l=new Label(column++, i+3, "���� "+i, detFormat);
		sheet.addCell(l);
		d=new DateTime(column++, i+3, new java.util.Date(), dateFormat);
		sheet.addCell(d);
		l=new Label(column++, i+3, "SGD", detFormat);
		sheet.addCell(l);
		n=new jxl.write.Number(column++, i+3, 98832, priceFormat);
		sheet.addCell(n);
		//�����еĿ��
		column=0;
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 10);
		sheet.setColumnView(column++, 20);
		workbook.write();
		workbook.close();
		System.out.println("����д��"+fileName+"�ɹ�");
	}

	public static void readExcelInfo(String fileName) 
		throws Exception {// ���Excel�ļ������ж�����
		Workbook book = Workbook.getWorkbook(new File(fileName));// ����Workbook��������������
		Sheet sheet = book.getSheet(0);
		// �õ���һ�е�һ�еĵ�Ԫ��// ��õ�һ�����������
		int columnum = sheet.getColumns(); // �õ�����
		int rownum = sheet.getRows(); // �õ�����
		System.out.println(columnum);
		System.out.println(rownum);
		for (int i = 0; i < rownum; i++) // ѭ�����ж�д
		{
			for (int j = 0; j < columnum; j++) {
				Cell cell1 = sheet.getCell(j, i);
				String result = cell1.getContents();
				System.out.print(result);
				System.out.print(" \t ");
			}
			System.out.println();
		}
		book.close();//�رգ�������������
	}
	
	public static void main(String[] args) {// java��������ڴ�
		try {
			System.out.println("1.����Excel�ļ�������Excel�ļ����ƣ�����·���ͺ�׺��");
			Scanner scan = new Scanner(System.in);
			final String fileName = scan.next();//��ü���ֵ
			writeExcel(fileName);//��������Excel����
			
			System.out.println("2.������д��Excel�ļ�");
			writeContentToExcel(fileName);//���ý�����д��Excel����
			
			System.out.println("3.��ȡExcel�ļ�");
			readExcelInfo(fileName);
		} catch (Exception e) {//�����쳣
			e.printStackTrace();
		}
	}
}
