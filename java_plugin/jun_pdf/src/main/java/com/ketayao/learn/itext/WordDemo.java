package com.ketayao.learn.itext;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;

/**
 * 创建word文档 步骤: 1,建立文档 2,创建一个书写器 3,打开文档 4,向文档中写入数据 5,关闭文档
 */
public class WordDemo {

	public WordDemo() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 创建word文档,并设置纸张的大小
		Document document = new Document(PageSize.A4);
		try {
			RtfWriter2.getInstance(document,
					new FileOutputStream("E:/word.doc"));

			document.open();

			// 设置合同头

			Paragraph ph = new Paragraph();
			Font f = new Font();

			Paragraph p = new Paragraph("出口合同", new Font(Font.NORMAL, 18,
					Font.BOLDITALIC, new Color(0, 0, 0)));
			p.setAlignment(1);
			document.add(p);
			ph.setFont(f);

			// 设置中文字体
			// BaseFont bfFont =
			// BaseFont.createFont("STSongStd-Light",
			// "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
			// Font chinaFont = new Font();
			/*
			 * 创建有三列的表格
			 */
			Table table = new Table(4);
			document.add(new Paragraph("生成表格"));
			table.setBorderWidth(1);
			table.setBorderColor(Color.BLACK);
			table.setPadding(0);
			table.setSpacing(0);

			/*
			 * 添加表头的元素
			 */
			Cell cell = new Cell("表头");// 单元格
			cell.setHeader(true);
			cell.setColspan(3);// 设置表格为三列
			cell.setRowspan(3);// 设置表格为三行
			table.addCell(cell);
			table.endHeaders();// 表头结束

			// 表格的主体
			cell = new Cell("Example cell 2");
			cell.setRowspan(2);// 当前单元格占两行,纵向跨度
			table.addCell(cell);
			table.addCell("1,1");
			table.addCell("1,2");
			table.addCell("1,3");
			table.addCell("1,4");
			table.addCell("1,5");
			table.addCell(new Paragraph("用java生成的表格1"));
			table.addCell(new Paragraph("用java生成的表格2"));
			table.addCell(new Paragraph("用java生成的表格3"));
			table.addCell(new Paragraph("用java生成的表格4"));
			document.add(new Paragraph("用java生成word文件"));
			document.add(table);
			
			System.out.println(document);
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}