package com.hrhx.springboot.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Jacob2Html {
	
	// 直接调用这个方法即可
	public static boolean convert2Html(String inputFile, String htmlFile) {
		String suffix = getFileSufix(inputFile);
		if (suffix.equals("pdf")) {
			System.out.println("PDF not need to convert!");
			return false;
		}
		if (suffix.equals("doc") || suffix.equals("docx")
				|| suffix.equals("txt")) {
			return wordToHtml(inputFile, htmlFile);
		} else if (suffix.equals("ppt") || suffix.equals("pptx")) {
			return pptToHtml(inputFile, htmlFile);
		} else if (suffix.equals("xls") || suffix.equals("xlsx")) {
			return excelToHtml(inputFile, htmlFile);
		} else {
			System.out.println("文件格式不支持转换!");
			return false;
		}
	}
	
	private static String getFileSufix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(splitIndex + 1);
	}
	
	/**
	 * PowerPoint转成HTML
	 * 
	 * @param pptPath
	 *            PowerPoint文件全路径
	 * @param htmlfile
	 *            转换后HTML存放路径
	 */
	private static boolean pptToHtml(String pptPath, String htmlPath) {
		ComThread.InitSTA();
		ActiveXComponent offCom = new ActiveXComponent("PowerPoint.Application");
		try {
			offCom.setProperty("Visible", new Variant(true));
			Dispatch excels = offCom.getProperty("Presentations").toDispatch();
			Dispatch excel = Dispatch.invoke(
											excels,
											"Open",
											Dispatch.Method,
											new Object[] { pptPath, new Variant(false),
											new Variant(false) }, new int[1]
											).toDispatch();
			Dispatch.invoke(excel, "SaveAs", Dispatch.Method, new Object[] {htmlPath, new Variant(12) }, new int[1]);
			Dispatch.call(excel, "Close");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			offCom.invoke("Quit", new Variant[] {});
			ComThread.Release();//正确释放线程
		}
	}

	/**
	 * WORD转成HTML
	 * 
	 * @param wordPath
	 *            WORD文件全路径
	 * @param htmlPath
	 *            生成的HTML存放路径
	 */
	private static boolean wordToHtml(String wordPath, String htmlPath) {
		ComThread.InitSTA();
		ActiveXComponent offCom = new ActiveXComponent("Word.Application");
		try {
			offCom.setProperty("Visible", new Variant(false));
			Dispatch wordDis = offCom.getProperty("Documents").toDispatch();
			Dispatch doc = Dispatch.invoke(
											wordDis,
											"Open",
											Dispatch.Method,
											new Object[] { wordPath, new Variant(false),
											new Variant(true) }, new int[1]
										  ).toDispatch();
			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {htmlPath, new Variant(8) }, new int[1]);
			Variant f = new Variant(false);
			Dispatch.call(doc, "Close", f);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			offCom.invoke("Quit", new Variant[] {});
			ComThread.Release();//正确释放线程
		}
	}

	/**
	 * EXCEL转成HTML
	 * 
	 * @param xlsfile
	 *            EXCEL文件全路径
	 * @param htmlfile
	 *            转换后HTML存放路径 
	 */
	private static boolean excelToHtml(String excelPath, String htmlPath) {
		ComThread.InitSTA();
		ActiveXComponent offCom = new ActiveXComponent("Excel.Application");
		try {
			offCom.setProperty("Visible", new Variant(false));
			Dispatch excels = offCom.getProperty("Workbooks").toDispatch();
			Dispatch excel = Dispatch.invoke(
											excels,
											"Open",
											Dispatch.Method,
											new Object[] { excelPath, new Variant(false),
											new Variant(true) }, new int[1]
											).toDispatch();
			Dispatch.invoke(excel, "SaveAs", Dispatch.Method, new Object[] {
					htmlPath, new Variant(44) }, new int[1]);
			Variant f = new Variant(false);
			Dispatch.call(excel, "Close", f);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			offCom.invoke("Quit", new Variant[] {});
			ComThread.Release();//正确释放线程
		}
	}
}