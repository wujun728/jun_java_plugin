package com.jun.common.report;
//package com.oa.common.report;
//
//import java.awt.Color;
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//import java.util.Locale;
//import java.util.ResourceBundle;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
//
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.Font;
//import com.lowagie.text.PageSize;
//import com.lowagie.text.Rectangle;
//import com.lowagie.text.pdf.BaseFont;
//import com.oa.common.report.printer.ExcelCss;
//import com.oa.common.report.printer.ExcelPrinter;
//import com.oa.common.report.printer.HTMLCss;
//import com.oa.common.report.printer.HTMLPrinter;
//import com.oa.common.report.printer.PDFCss;
//import com.oa.common.report.printer.PDFCssItem;
//import com.oa.util.ResourceBundleUtils;
//
///**
// * 报表基类
// * @author Wujun
// *
// */
//public class MyReport {
//	/**
//	 * 主标题
//	 */
//	protected String title;
//	/**
//	 * 副标题
//	 */
//	protected String titleEx;
//	/**
//	 * 报表每页记录数
//	 */
//	protected int reportSize = 25;
//	/**
//	 * 报表每页列数(只有在需要横向分页时才会用到)
//	 */
//	protected int reportColumnSize = 10;
//    /**
//     * 用于数据太长改变字体
//     */
//	protected Font changeFont;
//    /**
//     * 报表标题列
//     */
//	protected String[] fieldTitle;
//    /**
//     * 用于导出Excel报表时设置标题列宽度，PDF不用设置
//     */
//	private int titleWidth = 1;
//	/**
//	 * 资源文件包
//	 */
//	private ResourceBundle resourceBundle;
//
//	public MyReport(ResourceBundle resourceBundle) {
//		this.resourceBundle = resourceBundle;
//		try {
//			changeFont = new Font(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED), 8, Font.NORMAL);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public MyReport(ResourceBundle resourceBundle,int reportSize, int reportColumnSize) {
//		this(resourceBundle);
//		this.reportSize = reportSize;
//		this.reportColumnSize = reportColumnSize;
//	}
//    
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public String getTitleEx() {
//		return titleEx;
//	}
//
//	public void setTitleEx(String titleEx) {
//		this.titleEx = titleEx;
//	}
//
//	public int getReportSize() {
//		return reportSize;
//	}
//
//	public void setReportSize(int reportSize) {
//		this.reportSize = reportSize;
//	}
//
//	public int getReportColumnSize() {
//		return reportColumnSize;
//	}
//
//	public void setReportColumnSize(int reportColumnSize) {
//		this.reportColumnSize = reportColumnSize;
//	}
//
//	public Font getChangeFont() {
//		return changeFont;
//	}
//
//	public void setChangeFont(Font changeFont) {
//		this.changeFont = changeFont;
//	}
//
//	public String[] getFieldTitle() {
//		return fieldTitle;
//	}
//	
//	/**
//	 * 设置表头
//	 */
//	public void setFieldTitle(String[] fieldTitle) {
//		this.fieldTitle = fieldTitle;
//	}
//
//	public int getTitleWidth() {
//		return titleWidth;
//	}
//
//	public void setTitleWidth(int titleWidth) {
//		this.titleWidth = titleWidth;
//	}
//
//	public ResourceBundle getResourceBundle() {
//		return resourceBundle;
//	}
//
//	public void setResourceBundle(ResourceBundle resourceBundle) {
//		this.resourceBundle = resourceBundle;
//	}
//	
//	/*public void setRectangle(PDFPrinter printer) {
//
//	}*/
//	/**
//	 * 导出PDF报表
//	 * 
//	 * @param response
//	 * @param rotated true/纸张转90度  false/正常
//	 * @throws Exception
//	 */
//	public void exportPDF(HttpServletResponse response, boolean rotated) throws Exception {
//		Report[] reports = getReport();
//		printPDF(reports, response, PageSize.A4, 10, rotated, true);
//	}
//	/**
//	 * 导出PDF报表
//	 * 
//	 * @param list         数据集
//	 * @param fieldTitle   列标题
//	 * @param response     输出对象response
//	 * @param rotated      是否翻转90度
//	 * @throws Exception
//	 */
//	public void exportPDF(List list, String[] fieldTitle,HttpServletResponse response, boolean rotated) throws Exception {
//		Report[] reports = getReport(list, fieldTitle);
//		printPDF(reports, response, PageSize.A4, 10, rotated, true);
//	}
//	/**
//	 * 导出PDF报表
//	 * 
//	 * @param list         数据集
//	 * @param response     输出对象response
//	 * @param rotated      是否翻转90度
//	 * @throws Exception
//	 */
//	public void exportPDF(List list, HttpServletResponse response,boolean rotated) throws Exception {
//		Report[] reports = getReport(list, fieldTitle);
//		printPDF(reports, response, PageSize.A4, 10, rotated, true);
//	}
//	/**
//	 * 导出PDF报表
//	 * 
//	 * @param xList      列标题
//	 * @param yList      行标题
//	 * @param data       数据集
//	 * @param response   输出对象response
//	 * @param rotated    是否翻转90度
//	 * @throws Exception
//	 */
//	public void exportPDF(List xList, List yList, Object[][] data,HttpServletResponse response, boolean rotated) throws Exception {
//		Report[] reports = getReport(xList, yList, data);
//		printPDF(reports, response, PageSize.A4, 10, rotated, true);
//	}
//	
//	/**
//	 * 
//	 * @param reports     报表数组
//	 * @param response    输出对象response
//	 * @param page        
//	 * @param fontSize
//	 * @param rotated
//	 * @param hasFooter
//	 * @throws Exception
//	 */
//	public void printPDF(Report[] reports, HttpServletResponse response,
//			Rectangle page, int fontSize, boolean rotated, boolean hasFooter)
//			throws Exception {
//		ServletOutputStream outputStream = response.getOutputStream();
//		response.reset();
//		response.setContentType("application/pdf");
//		PDFPrinter printer = null;
//		try {
//			if (hasFooter){
//				ResourceBundleUtils.getInstance("com.lxw.oa.common.report.message", Locale.CHINA);
//				printer = new PDFPrinter(
//					ResourceBundleUtils.getString("myReport.before"), 
//					ResourceBundleUtils.getString("myReport.pageNumber")
//					+ reports.length
//					+ ResourceBundleUtils.getString("myReport.after"),
//					null);
//			} else{
//				printer = new PDFPrinter(null, null, null);
//			}
//			//setRectangle(printer);
//			printer.print(reports, getPDFCSS(fontSize), outputStream,
//					rotated ? page.rotate() : page);
//			outputStream.flush();
//			outputStream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 获取报表页数
//	 * 
//	 * @param list
//	 * @return 报表个数
//	 */
//	public int getReportRowCount(int listSize) {
//		int reportCount = listSize / reportSize;
//		if (reportCount == 0){
//			reportCount = 1;
//		} else if (reportCount * reportSize < listSize){
//			reportCount = reportCount + 1;
//		}
//		return reportCount;
//	}
//	
//	/**
//	 * 只有在需要横向分页时才有用。获取横向报表个数
//	 * 
//	 * @param list
//	 * @return 报表个数
//	 */
//	public int getReportColumnCount(int listSize) {
//		int reportCount = listSize / reportColumnSize;
//		if (reportCount == 0){
//			reportCount = 1;
//		} else if (reportCount * reportColumnSize < listSize){
//			reportCount = reportCount + 1;
//		}
//		return reportCount;
//	}
//	
//	/**
//	 * 获取每页报表
//	 */
//	public Report[] getReport() throws Exception {
//		Table[] tables = getTables();
//		Report[] reports = new Report[tables.length];
//		for (int i = 0; i < reports.length; i++) {
//			reports[i] = new Report();
//			ReportBody body = new ReportBody();
//			body.setData(tables[i]);
//			reports[i].setBody(body);
//
//			if (fieldTitle.length > 0) {
//				HeaderTable th = new HeaderTable(1, fieldTitle.length);
//				reports[i].getBody().setTableColHeader(th);
//				TableRow tr = new TableRow();
//				th.setRow(0, tr);
//				for (int j = 0; j < fieldTitle.length; j++){
//					tr.addCell(new TableCell(fieldTitle[j]));
//				}
//			}
//			//每页都加上标题
//			reports[i].setHeaderTable(getHeadTable(
//				fieldTitle.length != 0 ? 
//					fieldTitle.length : titleWidth));
//			reports[i].setFooterTable(getFootTable(
//				fieldTitle.length != 0 ?
//					fieldTitle.length : titleWidth));
//		}
//
//		if (reports.length == 0) {
//			reports = new Report[1];
//			reports[0] = new Report();
//			ReportBody body = new ReportBody();
//			body.setData(getNoDataTable(fieldTitle.length));
//			reports[0].setBody(body);
//
//			if (fieldTitle.length > 0) {
//				HeaderTable th = new HeaderTable(1, fieldTitle.length);
//				reports[0].getBody().setTableColHeader(th);
//				TableRow tr = new TableRow();
//				th.setRow(0, tr);
//				for (int j = 0; j < fieldTitle.length; j++){
//					tr.addCell(new TableCell(fieldTitle[j]));
//				}
//			}
//		}
//		return reports;
//	}
//	
//	/**
//	 * 获取报表，调用子类的获取Table方法
//	 * 
//	 * @param   list         记录集
//	 * @param   fieldTitle   列标题
//	 *            
//	 * @return  Report[]     报表对象数组
//	 * @throws Exception
//	 */
//	public Report[] getReport(List list, String[] fieldTitle) throws Exception {
//		Table[] tables = getTables(list);
//		Report[] reports = new Report[tables.length];
//		if (reports.length == 0) {
//			reports = new Report[1];
//			reports[0] = new Report();
//			ReportBody body = new ReportBody();
//			body.setData(getNoDataTable(fieldTitle.length));
//			reports[0].setBody(body);
//			HeaderTable th = new HeaderTable(1, fieldTitle.length);
//			reports[0].getBody().setTableColHeader(th);
//			TableRow tr = new TableRow();
//			th.setRow(0, tr);
//			for (int j = 0; j < fieldTitle.length; j++){
//				tr.addCell(new TableCell(fieldTitle[j]));
//			}
//
//			//每页都加上标题
//			reports[0].setHeaderTable(getHeadTable(fieldTitle.length));
//			reports[reports.length - 1].setFooterTable(getFootTable(fieldTitle.length));
//		} else {
//			for (int i = 0; i < reports.length; i++) {
//				reports[i] = new Report();
//				ReportBody body = new ReportBody();
//				body.setData(tables[i]);
//				reports[i].setBody(body);
//
//				HeaderTable th = new HeaderTable(1, fieldTitle.length);
//				reports[i].getBody().setTableColHeader(th);
//				TableRow tr = new TableRow();
//				th.setRow(0, tr);
//				for (int j = 0; j < fieldTitle.length; j++){
//					tr.addCell(new TableCell(fieldTitle[j]));
//				}
//
//				//每页都加上标题
//				reports[i].setHeaderTable(getHeadTable(fieldTitle.length));
//				reports[i].setFooterTable(getFootTable(fieldTitle.length));
//			}
//		}
//		return reports;
//	}
//	
//	/**
//	 * 获取报表，调用子类的获取Table方法
//	 * 
//	 * @param  xList     列标题
//	 * @param  yList     行标题
//	 * @param  data      数据集
//	 * @return Report[]  报表对象数组
//	 * @throws Exception
//	 */
//	public Report[] getReport(List xList, List yList, Object[][] data) throws Exception {
//		Table[] tables = getTables(xList, yList, data);
//		Report[] reports = new Report[tables.length];
//		for (int i = 0; i < reports.length; i++) {
//			reports[i] = new Report();
//			ReportBody body = new ReportBody();
//			body.setData(tables[i]);
//			reports[i].setBody(body);
//
//			// 每页都加上标题
//			reports[i].setHeaderTable(getHeadTable(xList.size()));
//			reports[i].setFooterTable(getFootTable(xList.size()));
//		}
//		
//		/*
//		 * 只有第一页加标题
//		 * reports[0].setHeaderTable(getHeadTable(xList.size()));
//		 * reports[reports.length-1].setFooterTable(getFootTable());
//		 */
//		return reports;
//	}
//	
//	/**
//	 * 获取每个报表的表格，子类需要重写此方法
//	 * @param  xList     列标题
//	 * @param  yList     行标题
//	 * @param  data      数据集
//	 * @return Table[]   表格对象数组
//	 * @throws Exception
//	 */
//	public Table[] getTables(List xList, List yList, Object[][] data) throws Exception {
//		return new Table[] { new Table() };
//	};
//
//	/**
//	 * 获取每个报表的表格，子类需要重写此方法
//	 * @return Table[]   表格对象数组
//	 * @throws Exception
//	 */
//	public Table[] getTables() throws Exception {
//		return new Table[] { new Table() };
//	};
//
//	/**
//	 * 获取每个报表的表格，子类需要重写此方法
//	 * @param  list      数据集合
//	 * @return Table[]   表格对象数组
//	 * @throws Exception
//	 */
//	public Table[] getTables(List list) throws Exception {
//		return new Table[] { new Table() };
//	};
//	
//	/**
//	 * 获取PDF样式
//	 * @param     dataFontSize   字号大小
//	 * @return    PDFCss         PDF样式对象
//	 * @throws Exception
//	 */
//	public PDFCss getPDFCSS(int dataFontSize) throws Exception {
//		/******************定义PDF中文字体*****************/
//		BaseFont bfChinese = BaseFont.createFont("STSong-Light",
//				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//		BaseFont bfChineseBold = BaseFont.createFont("STSong-Light,Bold",
//				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//		Font FontChinese = new Font(bfChinese, 10, Font.NORMAL);
//		Font FontChineseBold = new Font(bfChineseBold, 10, Font.NORMAL);
//		/******************end 定义PDF中文字体*****************/
//
//		/****************设置PDF报表的样式*********************/
//		PDFCss css = new PDFCss();
//		PDFCssItem item = new PDFCssItem();
//		item.setBackgroudColor(new Color(0xd8e4f1));
//		item.setFont(FontChinese);
//		css.setGroupTotal(item);
//		css.setTotal(item);
//
//		item = new PDFCssItem();
//		item.setBackgroudColor(new Color(0xffdead));
//		item.setFont(FontChineseBold);
//		css.setHead(item);
//
//		item = new PDFCssItem();
//		item.setFont(new Font(bfChineseBold, 15, Font.BOLD));
//		css.setTitle(item);
//
//		item = new PDFCssItem();
//		item.setFont(new Font(bfChinese, dataFontSize, Font.NORMAL));
//		css.setData(item);
//		/****************end 设置PDF报表的样式*********************/
//		return css;
//	}
//	
//	/**
//	 * 组装表头，如果想加入副标题，请在子类中重写此方法。
//	 * 
//	 * @param   colum       列数
//	 * @return  Table       表格对象
//	 * @throws Exception
//	 */
//	public Table getHeadTable(int colum) throws Exception {
//		Table headTable = new Table(2, colum);
//		TableRow tr = new TableRow();
//		TableCell tc = new TableCell((title == null) ? "" : title);
//		tc.setBorder(0);
//		tc.setValign(TableCell.VALIGN_TOP);
//		tc.setCssClass(Report.TITLE_TYPE);
//		tc.setColSpan(colum);
//		tr.addCell(tc);
//		for (int i = 0; i < colum - 1; i++) {
//			tc = new TableCell();
//			tc.setHidden(true);
//			tr.addCell(tc);
//		}
//		headTable.setRow(0, tr);
//
//		tr = new TableRow();
//		tc = new TableCell((titleEx == null) ? "" : titleEx + "");
//		tc.setBorder(0);
//		tc.setValign(TableCell.VALIGN_TOP);
//		tc.setColSpan(colum);
//		tr.addCell(tc);
//		for (int i = 0; i < colum - 1; i++) {
//			tc = new TableCell();
//			tc.setHidden(true);
//			tr.addCell(tc);
//		}
//		headTable.setRow(1, tr);
//		return headTable;
//	}
//	
//	/**
//	 * 加入尾标题
//	 * @param    col     列索引
//	 * @return
//	 * @throws Exception
//	 */
//	public Table getFootTable(int col) throws Exception {
//		return null;
//	}
//	
//	/**
//	 * 获取一个空Table，没数据时显示
//	 * 
//	 * @param   colum       列数
//	 * @return  Table       表格对象
//	 * @throws Exception
//	 */
//	private Table getNoDataTable(int colum) throws Exception {
//		Table headTable = new Table(1, colum);
//		headTable.setBorder(0);
//		TableRow tr = new TableRow();
//		TableCell tc = new TableCell(
//			getResourceBundle().getString("myReport.nodata")
//		);
//		tc.setColSpan(colum);
//		tr.addCell(tc);
//		for (int i = 0; i < colum - 1; i++) {
//			tc = new TableCell();
//			tc.setHidden(true);
//			tr.addCell(tc);
//		}
//		headTable.setRow(0, tr);
//		return headTable;
//	}
//	
//	/**
//	 * 设置Table的边框
//	 * @param  bordered  是否需要设置边框 true/需要  false/不需要
//	 */
//	private void setBorder(Table table, boolean bordered) {
//		if (table == null){
//			return;
//		}
//		int border = bordered ? 1 : 0;
//		table.setBorder(border);
//		for (int i = 0; i < table.getRowCount(); i++){
//			for (int j = 0; j < table.getRow(i).getCellCount(); j++){
//				try {
//					table.getRow(i).getCell(j).setBorder(border);
//					table.getRow(i).getCell(j).setCssClass(Report.DATA_TYPE);
//					table.getRow(i).getCell(j).setNoWrap(true);
//				} catch (ReportException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	/**
//	 * 获取Excel报表的样式
//	 * @return  ExcelCss  Excel报表样式对象
//	 */
//	private ExcelCss getExcelCss() {
//		return new ExcelCss() {
//			public void init(HSSFWorkbook workbook) {
//
//				/*****************定义EXCEL报表字体*****************/
//				// 普通字体
//				HSSFFont fontNormal = workbook.createFont();
//				fontNormal.setFontHeightInPoints((short) 10);
//				fontNormal.setFontName("宋体");
//
//				// 粗体
//				HSSFFont fontBold = workbook.createFont();
//				fontBold.setFontHeightInPoints((short) 10);
//				fontBold.setFontName("宋体");
//				fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//
//				// 大、粗字体
//				HSSFFont fontBig = workbook.createFont();
//				fontBig.setFontHeightInPoints((short) 15);
//				fontBig.setFontName("宋体");
//				fontBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//				/*****************end 定义EXCEL报表字体*****************/
//
//				/***************设置EXCEL报表的样式表******************/
//				HSSFCellStyle style = workbook.createCellStyle();
//				style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
//				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//				style.setFont(fontNormal);
//				this.setGroupTotal(style);
//
//				style = workbook.createCellStyle();
//				style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
//				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//				style.setFont(fontNormal);
//				this.setGroupTotal(style);
//
//				this.setTotal(style);
//
//				style = workbook.createCellStyle();
//				style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
//				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//				style.setFont(fontBold);
//				this.setHead(style);
//
//				style = workbook.createCellStyle();
//				style.setFont(fontBig);
//				this.setTitle(style);
//
//				style = workbook.createCellStyle();
//				style.setFont(fontNormal);
//				this.setData(style);
//
//				// this.setDefaultColumnWidth( (short) 20);
//				/***************end 设置EXCEL报表的样式******************/
//			}
//		};
//	}
//	
//	/**
//	 * 导出Excel
//	 * @param   response
//	 * @param   hasTitle    是否设置表头
//	 */
//	public void exportXLS(HttpServletResponse response, boolean hasTitle) throws Exception {
//		response.reset();
//		//response.setContentType("application/vnd.ms-excel");
//		response.setHeader("Content-Disposition", "attachment; filename=\"export.xls\"");
//		ServletOutputStream outputStream = response.getOutputStream();
//		Report[] reports = getReport();
//
//		for (int i = 0; i < reports.length; i++) {
//			Table headerTable = reports[i].getHeaderTable();
//			setBorder(headerTable, false);
//			setBorder(reports[i].getBody().getData(), true);
//			setBorder(reports[i].getHeaderTable(), true);
//			if (!hasTitle){
//				reports[i].setHeaderTable(null);
//			}
//		}
//
//		try {
//			new ExcelPrinter().print(reports, getExcelCss(), outputStream);
//			outputStream.flush();
//			outputStream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 导出Excel
//	 * @param   list        数据集合
//	 * @param   response
//	 * @param   hasTitle    是否设置表头
//	 */
//	public void exportXLS(List list, HttpServletResponse response, boolean hasTitle) throws Exception {
//		response.reset();
//		response.setHeader("Content-Disposition", "attachment; filename=\"export.xls\"");
//		ServletOutputStream outputStream = response.getOutputStream();
//		Report[] reports = getReport(list, fieldTitle);
//		for (int i = 0; i < reports.length; i++) {
//			Table headerTable = reports[i].getHeaderTable();
//			setBorder(headerTable, false);
//			setBorder(reports[i].getBody().getData(), true);
//			setBorder(reports[i].getBody().getTableColHeader(), true);
//			if (!hasTitle){
//				reports[i].setHeaderTable(null);
//			}
//		}
//
//		try {
//			new ExcelPrinter().print(reports, getExcelCss(), outputStream);
//			outputStream.flush();
//			outputStream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 压缩Zip文件时用到
//	 * @param   list        数据集合
//	 * @param   response
//	 * @param   hasTitle    是否设置表头
//	 */
//	public void exportXLS(List list, OutputStream outputStream, boolean hasTitle) throws Exception {
//		Report[] reports = getReport(list, fieldTitle);
//		for (int i = 0; i < reports.length; i++) {
//			Table headerTable = reports[i].getHeaderTable();
//			setBorder(headerTable, false);
//			setBorder(reports[i].getBody().getData(), true);
//			setBorder(reports[i].getBody().getTableColHeader(), true);
//			if (!hasTitle){
//				reports[i].setHeaderTable(null);
//			}
//		}
//
//		try {
//			new ExcelPrinter().print(reports, getExcelCss(), outputStream);
//			outputStream.flush();
//			outputStream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 获取大写的国际化数字
//	 * 
//	 * @param number  数字0-10
//	 * @return
//	 */
//	public String getNumber(int number) {
//		switch (number) {
//		case 0:
//			return getResourceBundle().getString("myReport.number0");
//		case 1:
//			return getResourceBundle().getString("myReport.number1");
//		case 2:
//			return getResourceBundle().getString("myReport.number2");
//		case 3:
//			return getResourceBundle().getString("myReport.number3");
//		case 4:
//			return getResourceBundle().getString("myReport.number4");
//		case 5:
//			return getResourceBundle().getString("myReport.number5");
//		case 6:
//			return getResourceBundle().getString("myReport.number6");
//		case 7:
//			return getResourceBundle().getString("myReport.number7");
//		case 8:
//			return getResourceBundle().getString("myReport.number8");
//		case 9:
//			return getResourceBundle().getString("myReport.number9");
//		case 10:
//			return getResourceBundle().getString("myReport.number10");
//		default:
//			return "";
//		}
//	}
//	
//	/**
//	 * 插入单元格
//	 * @param   tr      插入行
//	 * @param   txt     单元格文本内容
//	 * @param   left    是否左对齐
//	 * @param   col     列跨度
//	 * @param   maxSize 单元格文本最大长度
//	 */
//	public void insertCell(TableRow tr, String txt, boolean left, 
//			int col,int maxSize) throws Exception {
//		insertCell(tr, txt, left, col, 1, maxSize);
//	}
//	/**
//	 * 插入单元格
//	 * @param   tr      插入行
//	 * @param   txt     单元格文本内容
//	 * @param   left    是否左对齐
//	 * @param   col     列跨度
//	 * @param   row     行跨度
//	 * @param   maxSize 单元格文本最大长度
//	 */
//	public void insertCell(TableRow tr, String txt, boolean left, 
//			int col,int row, int maxSize) throws Exception {
//		if (col < 1 || row < 1){
//			throw new Exception("the param \"col\" or \" row\" must be larger than 1");
//		}
//		TableCell tc = new TableCell(txt);
//		if (left){
//			tc.setAlign(TableCell.ALIGN_LEFT);
//		}
//		
//		if (txt != null && txt.length() > maxSize){
//			tc.setFont(this.changeFont);
//		}
//		tc.setColSpan(col);
//		if(row > 1){
//			tc.setRowSpan(row);
//		}
//		tr.addCell(tc);
//		insertHiddenCell(tr, col - 1, 1);
//	}
//	
//	/**
//	 * 插入单元格(单元格最多显示10个字符)
//	 * @param   tr      插入行
//	 * @param   txt     单元格文本内容
//	 * @param   left    是否左对齐
//	 * @param   col     列跨度
//	 */
//	public void insertCell(TableRow tr, String txt, 
//			boolean left, int col) throws Exception {
//		insertCell(tr, txt, left, col,10);
//	}
//	
//	/**
//	 * 插入单元格
//	 * @param   tr      插入行
//	 * @param   txt     单元格文本内容
//	 * @param   col     列跨度
//	 * @param   row     行跨度
//	 */
//	public void insertCell(TableRow tr, String txt,
//			int col, int row) throws Exception {
//		if (col < 1 || row < 1){
//			throw new Exception("the param \"col\" or \" row\" must be larger than 1");
//		}
//		TableCell tc = new TableCell(txt);
//		tc.setColSpan(col);
//		tc.setRowSpan(row);
//		tr.addCell(tc);
//		insertHiddenCell(tr, col - 1, row);
//	}
//	/**
//	 * 插入单元格
//	 * @param   tr        插入行
//	 * @param   txt       单元格文本内容
//	 * @param   col       列跨度
//	 * @param   row       行跨度
//	 * @param   fontSize  字号
//	 */
//	public void insertCell(TableRow tr, String txt, int col, int row,
//			int fontSize) throws Exception {
//		if (col < 1 || row < 1){
//			throw new Exception("the param \"col\" or \" row\" must be larger than 1");
//		}
//		TableCell tc = new TableCell(txt);
//		tc.setColSpan(col);
//		tc.setRowSpan(row);
//		tc.setFont(new Font(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
//				BaseFont.NOT_EMBEDDED), fontSize, Font.NORMAL));
//		tr.addCell(tc);
//		insertHiddenCell(tr, col - 1, row);
//	}
//	
//	/**
//	 * 插入隐藏单元格
//	 * @param   tr        插入行
//	 * @param   col       列跨度
//	 * @param   row       行跨度    
//	 */
//	public void insertHiddenCell(TableRow tr, int col, int row) throws Exception {
//		for (int i = 0; i < col; i++) {
//			TableCell tc = new TableCell();
//			tc.setRowSpan(row);
//			tc.setHidden(true);
//			tr.addCell(tc);
//		}
//	}
//	
//	/**
//	 * 生成HTML字符串
//	 */
//	private String printHTML(Report[] reports, String sessionID) throws Exception {
//		Table table = reports[0].getBody().getData();
//		table.setBorderColor(new Color(120, 120, 120));
//		for (int i = 0; i < table.getRowCount(); i++)
//			for (int j = 0; j < table.getRow(i).getCellCount(); j++)
//				try {
//					table.getRow(i).getCell(j).setBorderColor(
//							new Color(120, 120, 120));
//				} catch (ReportException e) {
//					e.printStackTrace();
//				}
//		table = reports[0].getBody().getTableColHeader();
//		if (table != null) {
//			TableRow tr = table.getRow(0);
//			for (int i = 0; i < tr.getCellCount(); i++)
//				tr.getCell(i).setBorderColor(new Color(120, 120, 120));
//		}
//		reports[0].setHeaderTable(null);
//		reports[0].setFooterTable(null);
//		
//		File file = new File(sessionID);
//		if (!file.exists()){
//			file.createNewFile();
//		}
//		OutputStream os = new FileOutputStream(file);
//		InputStream is = new FileInputStream(sessionID);
//		BufferedInputStream bis = new BufferedInputStream(is);
//		StringBuffer sb = new StringBuffer();
//		
//		HTMLCss css = new HTMLCss();
//		css.setGroupTotal("background-color: #d8e4f1;");
//		css.setHead("background-color: #d6efff;");
//		css.setTotal("background-color: #d8e4f1;");
//		css.setTitle("font:bold 10pt;");
//		css.setData("font:10pt;");
//		css.setCustom("font:10pt;color:#ff0000;");
//		
//		try {
//			new HTMLPrinter("UTF-8").print(reports[0], css, os);
//			os.flush();
//			os.close();
//			byte[] b = new byte[1024 * 1024 * 5];
//		
//			int size = bis.read(b);
//			while (size != -1) {
//				sb.append(new String(b, 0, size, "UTF-8"));
//				size = bis.read(b);
//			}
//			bis.close();
//			is.close();
//			file.delete();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return sb.toString();
//	}
//	/**
//	 * 导出HTML
//	 */
//	public String exportHTML(List list, String sessionID) throws Exception {
//		Report[] reports = getReport(list, fieldTitle);
//		return printHTML(reports, sessionID);
//	}
//	/**
//	 * 导出HTML(重载)
//	 */
//	public String exportHTML(String sessionID) throws Exception {
//		Report[] reports = getReport();
//		return printHTML(reports, sessionID);
//	}
//	
//	
//	public static void main(String[] args) throws UnsupportedEncodingException {
//		//LocalizedTextUtil是Struts2.0中国际化中的工具类，用于非Action类中获取值
//		//String name = LocalizedTextUtil.findDefaultText("userName",new Locale("zh_CN"));
//		ResourceBundle rb = ResourceBundle.getBundle("com.lxw.oa.common.report.message",Locale.CHINA);
//		String username = rb.getString("myReport.number6");
//		System.out.println(username);
//	}
//}
