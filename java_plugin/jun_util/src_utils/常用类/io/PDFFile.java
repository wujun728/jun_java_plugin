package book.io;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Section;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 很多应用程序要求动态生成 PDF 文档。这类应用程序包括银行生成用于电子邮件投递的客户报表，到读者购买特定图书章节并以 PDF 格式接收这些文档。例子罗列下去是很多的。在本文中，将使用 iText Java 库生成 PDF 文档，并引导您完成一个示例应用程序，以使您能够更好地理解和使用 iText。 
　　iText 是 Lowagie.com 站点（请参阅 参考资料）免费提供的 Java 库。iText 库的功能很强大，支持 HTML、RTF 和 XML 文档的生成，此外还能够生成 PDF 文档。可以从多种字体中选择文档中所使用的字体。同时，iText 的结构允许使用相同的代码生成以上任意类型的文档。
 * http://www.lowagie.com/iText/
 * iText API：近距离观察
　　com.lowagie.text.Document 是生成 PDF 的主要的类。它是需要使用的第一个类。一旦开始创建文档，将需要一个写入器向文档中写入内容。com.lowagie.text.pdf.PdfWriter 就是一个 PDF 写入器。下面列出了通常需要使用的类：
　　com.lowagie.text.Paragraph —— 这个类表示一个缩进的段落。 
　　com.lowagie.text.Chapter —— 这个类表示 PDF 文档中的章节。使用 Paragraph 作为题目并使用 int 作为章节号码来创建它。
　　com.lowagie.text.Font —— 这个类包含了全部的字体规范，例如字体、大小、样式和颜色。各种字体都在这个类中声明为静态常数。 
　　com.lowagie.text.List —— 这个类表示一个列表，按顺序包含许多 ListItems。
　　com.lowagie.text.Table —— 这个类表示包含单元格的表，单元格有序地排列在矩阵中。
 */
public class PDFFile {

	/**
	 * 写PDF文件，展示了PDF文档、章节、小节、字体、段落、表格、列表的使用
	 * 最后展示如何使用写入中文。
	 * @param fileName
	 */
	public void writePDF(String fileName) {
		File file = new File(fileName);
		FileOutputStream out = null;

		try {
			//（1）实例化文档对象
			//第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);

			//（2）创建写入器
			//第一个参数是对文档对象的引用，第二个参数是输出的文件，将out和document连接起来
			out = new FileOutputStream(file);
			PdfWriter writer = PdfWriter.getInstance(document, out);
			//打开文档准备写入内容
			document.open();
			
			//（3）下面创建章节对象
			//首先创建段落对象，作为章节的标题。FontFactory用于指定段落的字体。
			Font font = FontFactory.getFont(FontFactory.HELVETICA, 
					18, Font.BOLDITALIC, new Color(0, 0, 255));
			Paragraph chapter1_title = new Paragraph("Chapter 1",font);
			//创建了一个章节对象，标题为"Chapter 1"
			Chapter chapter1 = new Chapter(chapter1_title, 1);
			//将编号级别设为 0 就不会在页面上显示章节编号
			chapter1.setNumberDepth(0);
			//（4）创建小节对象
			//创建小节对象的标题
			font = FontFactory.getFont(FontFactory.HELVETICA, 16, 
					Font.BOLD, new Color(255, 0, 0));
			Paragraph section1_title1 = new Paragraph("Section 1 of Chapter 1", font);
			//创建一个小节对象，标题为"This is Section 1 in Chapter 1"，属于chapter1。
			Section section1 = chapter1.addSection(section1_title1);
			//（5）往小节中写文本内容
			Paragraph text = new Paragraph("This is the first text in section 1 of chapter 1.");
			section1.add(text);
			text = new Paragraph("Following is a 5×5 table:");
			section1.add(text);
			
			//（6）往小节中写表格
			//创建表格对象
			Table table = new Table(5, 5);
			//设置表格边框颜色
			table.setBorderColor(new Color(220, 255, 100));
			//设置单元格的边距间隔等
			table.setPadding(1);
			table.setSpacing(1);
			table.setBorderWidth(1);
			//单元格对象
			Cell cell = null;
			//添加表头信息
			for (int colNum=0; colNum<5; colNum++){
				cell = new Cell("header-" + colNum);
				cell.setHeader(true);
				table.addCell(cell);
			}
			table.endHeaders();
			//添加表的内容
			for (int rowNum=1; rowNum<5; rowNum++){
				for (int colNum=0; colNum<5; colNum++){
					cell= new Cell("value-" + rowNum + "-" + colNum);
					table.addCell(cell);
				}
			}
			//将表格对象添加到小节对象中
			section1.add(table); 
			
			//（7）添加列表
			// 列表包含一定数量的 ListItem。可以对列表进行编号，也可以不编号。
			// 将第一个参数设置为 true 表明想创建一个进行编号的列表；
			// 第二个参数设置为true表示列表采用字母进行编号，为false则用数字进行编号；
			// 第三个参数为列表内容与编号之间的距离。
			List list = new List(true, false, 20);
			ListItem item = new ListItem("First item of list;");
			list.add(item);
			item = new ListItem("Second item of list;");
			list.add(item);
			item = new ListItem("Third item of list.");
			list.add(item);
			//将列表对象添加到小节对象中
			section1.add(list);
			
			//（8）添加中文
			//允许在PDF中写入中文，将字体文件放在classPath中。
			//simfang.ttf是仿宋的字体文件
			BaseFont bfChinese = BaseFont.createFont("simfang.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			//中文大小为20，加粗
			font = new Font(bfChinese, 20, Font.BOLD);
			text = new Paragraph("PDF中文测试", font);
			section1.add(text);
			
			//（9）将章节对象加入到文档中
			document.add(chapter1);
			
			//（10）关闭文档
			document.close();
			System.out.println("PDF文件生成成功，PDF文件名：" + file.getAbsolutePath());
		} catch (DocumentException e) {
			System.out.println("PDF文件"+ file.getAbsolutePath() + "生成失败！" + e);
			e.printStackTrace();
		} catch (IOException ee) {
			System.out.println("PDF文件"+ file.getAbsolutePath() + "生成失败！" + ee);
			ee.printStackTrace();
		} finally {
			if (out != null){
				try {
					//关闭输出文件流
					out.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	/**
	 * 读PDF文件，使用了pdfbox开源项目，新的版本已经支持中文了。
	 * 上www.pdfbox.org下载读PDF的jar包
	 * @param fileName
	 */
	public void readPDF(String fileName) {
		File file = new File(fileName);
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
			//新建一个PDF解析器对象
			PDFParser parser = new PDFParser(in);
			//对PDF文件进行解析
			parser.parse();
			//获取解析后得到的PDF文档对象
			PDDocument pdfdocument = parser.getPDDocument();
			//新建一个PDF文本剥离器
			PDFTextStripper stripper = new PDFTextStripper();
			//从PDF文档对象中剥离文本
			String result = stripper.getText(pdfdocument);
			System.out.println("PDF文件" + file.getAbsolutePath() + "的文本内容如下：");
			System.out.println(result);
			
		} catch (Exception e) {
			System.out.println("读取PDF文件"+ file.getAbsolutePath() + "生失败！" + e);
			e.printStackTrace();
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	public static void main(String[] args) {
		PDFFile pdf = new PDFFile();
		String fileName = "C:/temp/tempPDF.pdf";
		pdf.writePDF(fileName);
		pdf.readPDF(fileName);
	}
}
