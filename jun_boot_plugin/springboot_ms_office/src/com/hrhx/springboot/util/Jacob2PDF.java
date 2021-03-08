package com.hrhx.springboot.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * http://www.cnblogs.com/qiuyuedong/p/5138398.html
 * 代码转化完后会打开一个进程，但是结束时却不会主动开闭，导致内存占用最终崩溃。
 * 所以要在 每个方法前   加上 
 * ComThread.InitSTA();
 * 结束加上
 * ComThread.Release(); //释放进程
 * @author Wujun
 * 
 */
public class Jacob2PDF {

	private static final int wdFormatPDF = 17;
	private static final int xlTypePDF = 0;
	private static final int ppSaveAsPDF = 32;

	// 直接调用这个方法即可
	public static boolean convert2PDF(String inputFile, String pdfFile) {
		String suffix = getFileSufix(inputFile);
		if (suffix.equals("pdf")) {
			System.out.println("PDF not need to convert!");
			return false;
		}
		if (suffix.equals("doc") || suffix.equals("docx")
				|| suffix.equals("txt")) {
			return word2PDF(inputFile, pdfFile);
		} else if (suffix.equals("ppt") || suffix.equals("pptx")) {
			return ppt2PDF(inputFile, pdfFile);
		} else if (suffix.equals("xls") || suffix.equals("xlsx")) {
			return excel2PDF(inputFile, pdfFile);
		} else {
			System.out.println("文件格式不支持转换!");
			return false;
		}
	}

	public static String getFileSufix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(splitIndex + 1);
	}

	public static boolean word2PDF(String inputFile, String pdfFile) {
		try {
			ComThread.InitSTA();
			// 打开word应用程序
			ActiveXComponent app = new ActiveXComponent("Word.Application");
			// 设置word不可见
			app.setProperty("Visible", false);
			// 获得word中所有打开的文档,返回Documents对象
			Dispatch docs = app.getProperty("Documents").toDispatch();
			// 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
			Dispatch doc = Dispatch.call(docs, "Open", inputFile, false, true)
					.toDispatch();
			// 调用Document对象的SaveAs方法，将文档保存为pdf格式
			/*
			 * Dispatch.call(doc, "SaveAs", pdfFile, wdFormatPDF
			 * //word保存为pdf格式宏，值为17 );
			 */
			Dispatch.call(doc, "ExportAsFixedFormat", pdfFile, wdFormatPDF // word保存为pdf格式宏，值为17
			);
			// 关闭文档
			Dispatch.call(doc, "Close", false);
			// 关闭word应用程序
			app.invoke("Quit", 0);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			ComThread.Release();
		}
	}

	public static boolean excel2PDF(String inputFile, String pdfFile) {
		try {
			ComThread.InitSTA();
			ActiveXComponent app = new ActiveXComponent("Excel.Application");
			app.setProperty("DisplayAlerts", "False");
			app.setProperty("Visible", false);
			Dispatch excels = app.getProperty("Workbooks").toDispatch();
			Dispatch excel = Dispatch.call(excels, "Open", inputFile, false,
					true).toDispatch();
			Dispatch.call(excel, "ExportAsFixedFormat", xlTypePDF, pdfFile);
			//Dispatch.call(excel, "SaveAs", pdfFile, new Variant(57));
			Dispatch.call(excel, "Close", false);
			app.invoke("Quit");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			ComThread.Release();
		}

	}

	public static boolean ppt2PDF(String inputFile, String pdfFile) {
		try {
			ComThread.InitSTA();
			ActiveXComponent app = new ActiveXComponent(
					"PowerPoint.Application");
			// app.setProperty("Visible", msofalse);
			Dispatch ppts = app.getProperty("Presentations").toDispatch();

			Dispatch ppt = Dispatch.call(ppts, "Open", inputFile, true,// ReadOnly
					true,// Untitled指定文件是否有标题
					false// WithWindow指定文件是否可见
					).toDispatch();

			Dispatch.call(ppt, "SaveAs", pdfFile, ppSaveAsPDF);

			Dispatch.call(ppt, "Close");

			app.invoke("Quit");
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			ComThread.Release();
		}

	}
	
	/**
    * 文档设置水印
    * 
    * @param waterMarkStr 水印字符串
    */
   public static void setWaterMark(String waterMarkStr,ActiveXComponent office)
   {
	  // 取得活动窗体对象
	  Dispatch activeWindow = office.getProperty("ActiveWindow").toDispatch();
	  //打开一个已存在的文档
	  Dispatch docSelection = Dispatch.get(office, "Selection").toDispatch();
      // 取得活动窗格对象
      Dispatch activePan = Dispatch.get(activeWindow, "ActivePane")
            .toDispatch();
      // 取得视窗对象
      Dispatch view = Dispatch.get(activePan, "View").toDispatch();
      //输入页眉内容
      Dispatch.put(view, "SeekView", new Variant(9));
      Dispatch headfooter = Dispatch.get(docSelection, "HeaderFooter")
            .toDispatch();
      //取得图形对象
      Dispatch shapes = Dispatch.get(headfooter, "Shapes").toDispatch();
      //给文档全部加上水印
      Dispatch selection = Dispatch.call(shapes, "AddTextEffect",
            new Variant(9), waterMarkStr, "宋体", new Variant(1),
            new Variant(false), new Variant(false), new Variant(0),
            new Variant(0)).toDispatch();
      Dispatch.call(selection, "Select");
      //设置水印参数
      Dispatch shapeRange = Dispatch.get(docSelection, "ShapeRange")
            .toDispatch();
      Dispatch.put(shapeRange, "Name", "PowerPlusWaterMarkObject1");
      Dispatch textEffect = Dispatch.get(shapeRange,"TextEffect").toDispatch();
      Dispatch.put(textEffect, "NormalizedHeight", new Boolean(false));
      Dispatch line = Dispatch.get(shapeRange, "Line").toDispatch();
      Dispatch.put(line, "Visible", new Boolean(false));
      Dispatch fill = Dispatch.get(shapeRange, "Fill").toDispatch();
      Dispatch.put(fill, "Visible", new Boolean(true));
      //设置水印透明度
      Dispatch.put(fill, "Transparency", new Variant(0.5));
      Dispatch foreColor = Dispatch.get(fill,"ForeColor").toDispatch();
      //设置水印颜色
      Dispatch.put(foreColor, "RGB", new Variant(16711680));
      Dispatch.call(fill, "Solid");
      //设置水印旋转
      Dispatch.put(shapeRange, "Rotation", new Variant(315));
      Dispatch.put(shapeRange, "LockAspectRatio", new Boolean(true));
      Dispatch.put(shapeRange, "Height", new Variant(117.0709));
      Dispatch.put(shapeRange, "Width", new Variant(468.2835));
      Dispatch.put(shapeRange, "Left", new Variant(-999995));
      Dispatch.put(shapeRange, "Top", new Variant(-999995));
      Dispatch wrapFormat = Dispatch.get(shapeRange, "WrapFormat").toDispatch();
      //是否允许交叠
      Dispatch.put(wrapFormat, "AllowOverlap", new Variant(true));
      Dispatch.put(wrapFormat, "Side", new Variant(3));
      Dispatch.put(wrapFormat, "Type", new Variant(3));
      Dispatch.put(shapeRange, "RelativeHorizontalPosition", new Variant(0));
      Dispatch.put(shapeRange, "RelativeVerticalPosition", new Variant(0));
      Dispatch.put(view, "SeekView", new Variant(0));
   }
}
