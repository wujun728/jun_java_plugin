package com.jun.plugin.poi;
/* 
 * 文 件 名:  MSWordPoi4.java 
 * 版    权:  Sunny Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved 
 * 描    述:  <描述> 
 * 修 改 人:  L.Hao 
 * 修改时间:  2014-8-8 
 * 跟踪单号:  <跟踪单号> 
 * 修改单号:  <修改单号> 
 * 修改内容:  <修改内容> 
 */

/** 
 * <一句话功能简述> 
 * <功能详细描述> 
 *  
 * @author Wujun
 * @version  [版本号, 2014-8-8] 
 * @see  [相关类/方法] 
 * @since  [产品/模块版本] 
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

public class MSWordPoi4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		String s="<table><tbody><trclass=\"firstRow\"><tdstyle=\"word-break:break-all;\"valign=\"top\"width=\"402\">testf11<br/></td><tdstyle=\"word-break:break-all;\"valign=\"top\"width=\"402\">testf11</td></tr><tr><tdstyle=\"word-break:break-all;\"valign=\"top\"width=\"402\">testf11</td><tdstyle=\"word-break:break-all;\"valign=\"top\"width=\"402\">testf11</td></tr><tr><tdstyle=\"word-break:break-all;\"valign=\"top\"width=\"402\">testf11</td><tdstyle=\"word-break:break-all;\"valign=\"top\"width=\"402\">testf11</td></tr></tbody></table><p><br/></p>";
		map.put("${sub}", "湖南大学");
		map.put("${content}", s);
		map.put("${item2.school}", "湖南大学");
		map.put("${item2.address}", "湖南");
		// map.put("${item1.name}", "王五1");
		// map.put("${item1.numberStudent}", "编号002");
		// map.put("${item1.sex}", "男2");
		// map.put("${item1.age}", "19");
		String srcPath = "D:\\test.doc";
		readwriteWord(srcPath, map);
	}

	/**
	 * 实现对word读取和修改操作
	 * 
	 * @param filePath
	 *            word模板路径和名称
	 * @param map
	 *            待填充的数据，从数据库读取
	 */
	public static void readwriteWord(String filePath, Map<String, String> map) {
		// 读取word模板
		// String fileDir = new
		// File(base.getFile(),"http://www.cnblogs.com/http://www.cnblogs.com/../doc/").getCanonicalPath();
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		HWPFDocument hdt = null;
		try {
			hdt = new HWPFDocument(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Fields fields = hdt.getFields();
		Iterator<Field> it = fields.getFields(FieldsDocumentPart.MAIN).iterator();
		while (it.hasNext()) {
			System.out.println(it.next().getType());
		}

		// 读取word文本内容
		Range range = hdt.getRange();
		TableIterator tableIt = new TableIterator(range);
		// 迭代文档中的表格
		int ii = 0;
		while (tableIt.hasNext()) {
			Table tb = (Table) tableIt.next();
			ii++;
			System.out.println("第" + ii + "个表格数据...................");
			// 迭代行，默认从0开始
			for (int i = 0; i < tb.numRows(); i++) {
				TableRow tr = tb.getRow(i);
				// 只读前8行，标题部分
				if (i >= 8)
					break;
				// 迭代列，默认从0开始
				for (int j = 0; j < tr.numCells(); j++) {
					TableCell td = tr.getCell(j);// 取得单元格
					// 取得单元格的内容
					for (int k = 0; k < td.numParagraphs(); k++) {
						Paragraph para = td.getParagraph(k);
						String s = para.text();
						System.out.println(s);
					} // end for
				} // end for
			} // end for
		} // end while
			// System.out.println(range.text());

		// 替换文本内容
		for (Map.Entry<String, String> entry : map.entrySet()) {
			range.replaceText(entry.getKey(), entry.getValue());
		}
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		String fileName = "" + System.currentTimeMillis();
		fileName += ".doc";
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("D:/" + fileName, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			hdt.write(ostream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 输出字节流
		try {
			out.write(ostream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ostream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}