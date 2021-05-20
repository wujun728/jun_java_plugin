package com.tanzhou.aduio;//创始一个包
import java.io.File;//引入类
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

public class TextOperatorExcel {// 操作Excel文件的类
	/**
	 * 生成一个Excel文件
	 */
	public static void writeExcel(String fileName) {
		WritableWorkbook wwb = null;
		try {
			//创建一个可写入的工作薄(Workbook)对象
			wwb = Workbook.createWorkbook(new File(fileName));
		} catch (IOException e) {// 捕获流异常
			e.printStackTrace();
		}
		if (wwb != null) {
			// 创建一个可写入的工作表
			// Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
			WritableSheet ws = wwb.createSheet("sheet1", 0);
			for (int i = 0; i < 10; i++) {// 循环添加单元格
				for (int j = 0; j < 5; j++) {
					Label labelC = new Label(j, i, "这是第" + (i + 1) + "行，第"
							+ (j + 1) + "列");
					try {
						ws.addCell(labelC);// 将生成的单元格添加到工作表中
					} catch (Exception e) {// 捕获异常
						e.printStackTrace();
					}

				}
			}
			try {
				wwb.write();// 从内存中写入文件中
				wwb.close();// 从内存中写入文件中
			} catch (Exception e) {// 捕获异常
				e.printStackTrace();
			}
		}
		System.out.println("生成一个Excel文件："+fileName+"成功!");
	}
	
	/**
	 * 将内容写入
	 * @param fileName
	 * @throws Exception
	 */
	public static void writeContentToExcel(String fileName) throws Exception{
		File tempFile=new File(fileName);
		WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
		WritableSheet sheet = workbook.createSheet("TestCreateExcel", 0); 
		//一些临时变量，用于写到excel中
		Label l=null;
		jxl.write.Number n=null;
		jxl.write.DateTime d=null;

		//预定义的一些字体和格式，同一个Excel中最好不要有太多格式  字形  大小  加粗 倾斜  下划线 颜色
		WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE); 
		WritableCellFormat headerFormat = new WritableCellFormat (headerFont); 

		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
		WritableCellFormat titleFormat = new WritableCellFormat (titleFont); 

		WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); 
		WritableCellFormat detFormat = new WritableCellFormat (detFont); 

		NumberFormat nf=new NumberFormat("0.00000");  //用于Number的格式
		WritableCellFormat priceFormat = new WritableCellFormat (detFont, nf); 

		DateFormat df=new DateFormat("yyyy-MM-dd");//用于日期的
		WritableCellFormat dateFormat = new WritableCellFormat (detFont, df); 

		l=new Label(0, 0, "用于测试的Excel文件", headerFormat);//创建一些单元格，再加到sheet中
		sheet.addCell(l);
		//添加标题
		int column=0;
		l=new Label(column++, 2, "标题", titleFormat);
		sheet.addCell(l);
		l=new Label(column++, 2, "日期", titleFormat);
		sheet.addCell(l);
		l=new Label(column++, 2, "货币", titleFormat);
		sheet.addCell(l);
		l=new Label(column++, 2, "价格", titleFormat);
		sheet.addCell(l);
		//添加内容
		int i=0;
		column=0;
		l=new Label(column++, i+3, "标题 "+i, detFormat);
		sheet.addCell(l);
		d=new DateTime(column++, i+3, new java.util.Date(), dateFormat);
		sheet.addCell(d);
		l=new Label(column++, i+3, "CNY", detFormat);
		sheet.addCell(l);
		n=new jxl.write.Number(column++, i+3, 5.678, priceFormat);
		sheet.addCell(n);
		i++;
		column=0;
		l=new Label(column++, i+3, "标题 "+i, detFormat);
		sheet.addCell(l);
		d=new DateTime(column++, i+3, new java.util.Date(), dateFormat);
		sheet.addCell(d);
		l=new Label(column++, i+3, "SGD", detFormat);
		sheet.addCell(l);
		n=new jxl.write.Number(column++, i+3, 98832, priceFormat);
		sheet.addCell(n);
		//设置列的宽度
		column=0;
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 10);
		sheet.setColumnView(column++, 20);
		workbook.write();
		workbook.close();
		System.out.println("内容写入"+fileName+"成功");
	}

	public static void readExcelInfo(String fileName) 
		throws Exception {// 获得Excel文件多少行多少列
		Workbook book = Workbook.getWorkbook(new File(fileName));// 构造Workbook（工作薄）对象
		Sheet sheet = book.getSheet(0);
		// 得到第一列第一行的单元格// 获得第一个工作表对象
		int columnum = sheet.getColumns(); // 得到列数
		int rownum = sheet.getRows(); // 得到行数
		System.out.println(columnum);
		System.out.println(rownum);
		for (int i = 0; i < rownum; i++) // 循环进行读写
		{
			for (int j = 0; j < columnum; j++) {
				Cell cell1 = sheet.getCell(j, i);
				String result = cell1.getContents();
				System.out.print(result);
				System.out.print(" \t ");
			}
			System.out.println();
		}
		book.close();//关闭（工作薄）对象
	}
	
	public static void main(String[] args) {// java程序主入口处
		try {
			System.out.println("1.创建Excel文件，输入Excel文件名称（包括路径和后缀）");
			Scanner scan = new Scanner(System.in);
			final String fileName = scan.next();//获得键盘值
			writeExcel(fileName);//调用生成Excel方法
			
			System.out.println("2.将内容写入Excel文件");
			writeContentToExcel(fileName);//调用将内容写入Excel方法
			
			System.out.println("3.读取Excel文件");
			readExcelInfo(fileName);
		} catch (Exception e) {//捕获异常
			e.printStackTrace();
		}
	}
}
