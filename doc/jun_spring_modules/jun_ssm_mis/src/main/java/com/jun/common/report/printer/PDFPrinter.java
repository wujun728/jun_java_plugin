package com.jun.common.report.printer;
//package com.oa.common.report.printer;
//
//import java.awt.Color;
//import java.io.IOException;
//import java.io.OutputStream;
//
//
//
//import com.lowagie.text.BadElementException;
//import com.lowagie.text.Cell;
//import com.lowagie.text.Chunk;
//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.Element;
//import com.lowagie.text.Font;
//import com.lowagie.text.HeaderFooter;
//import com.lowagie.text.Image;
//import com.lowagie.text.Paragraph;
//import com.lowagie.text.Phrase;
//import com.lowagie.text.Rectangle;
//import com.lowagie.text.pdf.BaseFont;
//import com.lowagie.text.pdf.PdfContentByte;
//import com.lowagie.text.pdf.PdfWriter;
//import com.oa.common.report.CssEngine;
//import com.oa.common.report.Report;
//import com.oa.common.report.ReportBody;
//import com.oa.common.report.ReportException;
//import com.oa.common.report.Table;
//import com.oa.common.report.TableCell;
//import com.oa.common.report.TableRow;
//import com.oa.common.report.cross.CrossTable;
//
//public class PDFPrinter implements Printer {
//
//	private Font defaultFont;
//	private String beforePhrase;
//	private String afterPhrase;
//	private String comp;
//	/**
//	 * 用于画斜线
//	 */
//	private Rectangle rect;
//	/**
//	 * 纵轴名称
//	 */
//	private String vName;
//	/**
//	 * 横轴名称
//	 */
//	private String hName;
//	
//
//	public PDFPrinter() throws DocumentException, IOException {
//		//设置中文字体，解决中文乱码问题
//		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
//		defaultFont = new Font(bfChinese, 12);
//	}
//	
//	/**
//	 * 设置页眉，页尾信息
//	 * @param beforePhrase
//	 * @param afterPhrase
//	 * @param comp
//	 * @throws DocumentException
//	 * @throws IOException
//	 */
//	public PDFPrinter(String beforePhrase,String afterPhrase,String comp) throws DocumentException, IOException {
//		this();
//		this.beforePhrase=beforePhrase;
//		this.afterPhrase=afterPhrase;
//		this.comp=comp;
//	}
//	
//	/**设置中国式报表头，歇线
//	 * @param rect
//	 * @param vName
//	 * @param hName
//	 */
//	public void setRect(Rectangle rect,String vName,String hName){
//		this.rect=rect;
//		this.vName=vName;
//		this.hName=hName;
//	}
//
//	private void print(TableCell tableCell, PDFCss css,
//			com.lowagie.text.Table table, boolean haveBorder)
//			throws BadElementException, com.lowagie.text.DocumentException,
//			IOException, ReportException {
//		if (tableCell.isHidden() == false) {
//			Cell cell = null;
//			PDFCssItem item = null;
//			if (tableCell.getCssClass().equals(Report.DATA_TYPE)) {
//				item = css.getData();
//			} else if (tableCell.getCssClass().equals(Report.GROUP_TOTAL_TYPE)) {
//				item = css.getGroupTotal();
//			} else if (tableCell.getCssClass().equals(Report.HEAD_TYPE)) {
//				item = css.getHead();
//			} else if (tableCell.getCssClass().equals(Report.TITLE_TYPE)) {
//				item = css.getTitle();
//			} else if (tableCell.getCssClass().equals(Report.TOTAL_TYPE)) {
//				item = css.getTotal();
//			} else if (tableCell.getCssClass().equals(Report.CROSS_HEAD_HEAD_TYPE)) {
//				item = css.getCrossHeadHead();
//			}else if(tableCell.getCssClass().equals(Report.CUSTOM_TYPE)){//自定义类型,加入颜色
//				item = css.getData();
//				cell = getCell(tableCell, item.getFont());
//				cell.setBackgroundColor(tableCell.getBackgroudColor());
//			}
//			if(cell==null){
//				if (item != null) {
//					cell = getCell(tableCell, tableCell.getFont()!=null?tableCell.getFont():item.getFont());
//					cell.setBackgroundColor(item.getBackgroudColor());								
//				} else {
//					cell = getCell(tableCell, tableCell.getFont()!=null?tableCell.getFont():defaultFont);
//					cell.setBackgroundColor(tableCell.getBackgroudColor());
//				}
//			}
//			cell.setHorizontalAlignment(getAlign(tableCell.getAlign()));
//
//			if (tableCell.getCssClass().equals(Report.CROSS_HEAD_HEAD_TYPE)) {
//				cell.setVerticalAlignment(com.lowagie.text.Table.ALIGN_MIDDLE);
//			} else {
//				cell.setVerticalAlignment(getVAlign(tableCell.getValign()));
//			}
//			cell.setColspan(tableCell.getColSpan());
//			cell.setRowspan(tableCell.getRowSpan());
//			if (tableCell.isNoWrap() == true 
//					&& tableCell.getCssClass().equals(Report.CROSS_HEAD_HEAD_TYPE)) {
//				cell.setMaxLines(1);
//			} else {
//				cell.setMaxLines(Integer.MAX_VALUE);
//			}
//			if (haveBorder == false) {
//				cell.setBorder(cell.NO_BORDER);
//			}
//			if (tableCell.getLeading() != 0) {
//				cell.setLeading(tableCell.getLeading());
//			}
//			
//			if (tableCell.getBorder()>0){
//				cell.setBorder(Rectangle.TOP|Rectangle.LEFT|Rectangle.RIGHT|Rectangle.BOTTOM);
//				cell.setBorderWidth(tableCell.getBorder());
//			}else{
//				cell.setBorder(cell.NO_BORDER);
//			}
//			table.addCell(cell);
//		}
//
//	}
//
//	
//	private Cell getCell(TableCell tc, Font font) throws BadElementException {
//		Phrase phrase = new Phrase();
//		Cell c = null;
//		if (tc.getCssClass().equals(Report.CROSS_HEAD_HEAD_TYPE)) {
//			String[] strs = PrinterUtil.getCrossHeadHeadContent(tc);
//			String blank = "";
//			for (int i = strs.length - 1; i >= 0; i--) {
//				blank = " ";
//				if (strs[i] != null) {
//					Chunk chunk = new Chunk(strs[i] + blank, font);
//					chunk.setTextRise((float) -1.7 * i + 4);
//					phrase.add(chunk);
//				}
//			}
//			c = new Cell(phrase);
//		} else {
//			if (tc.getContent() instanceof String) {
//				phrase = new Phrase((String) tc.getContent(), font);
//				c = new Cell(phrase);				
//			} else if (tc.getContent() instanceof Image) {
//				c = new Cell((Image) tc.getContent());
//			} else {
//				c = new Cell();//新的Cell
//			}
//		}
//		return c;
//	}
//
//	private String getCrossTabHeadHead2(TableCell tableCell) {
//		String content;
//		StringBuffer buf = new StringBuffer();
//		CrossTable crossTab = (CrossTable) tableCell.getContent();
//
//		int count = crossTab.getColHeader().length
//				+ crossTab.getRowHeader().length + 1;
//		String blank = "          ";
//		String[] strs = new String[count];
//		for (int i = 0; i < crossTab.getColHeader().length; i++) {
//			strs[i] = crossTab.getColHeader()[i].getHeaderText();
//		}
//		strs[crossTab.getColHeader().length] = crossTab.getCrossCol()
//				.getHeaderText();
//		for (int i = 0; i < crossTab.getRowHeader().length; i++) {
//			strs[i + crossTab.getColHeader().length + 1] = crossTab
//					.getRowHeader()[i].getHeaderText();
//		}
//		for (int i = 0; i < strs.length; i++) {
//			if (i != 0)
//
//				for (int j = 0; j < strs.length - i - 1; j++) {
//					buf.append(blank);
//				}
//			if (strs[i] != null)
//				buf.append(strs[i]);
//		}
//		content = buf.toString();
//		return content;
//
//	}
//
//	private void print(com.oa.common.report.TableRow tableRow, PDFCss css,
//			com.lowagie.text.Table table, boolean haveBorder)
//			throws BadElementException, DocumentException, IOException,
//			ReportException {		
//		for (int j = 0; j < tableRow.getCellCount(); j++) {
//			print(tableRow.getCell(j), css, table, haveBorder);
//		}
//	}
//
//	/**
//	 * 获取水平对齐方式常量
//	 * @param i
//	 * @return
//	 */
//	private int getAlign(int i) {
//		switch (i) {
//		case com.oa.common.report.Rectangle.ALIGN_LEFT:
//			return com.lowagie.text.Table.ALIGN_LEFT;
//		case com.oa.common.report.Rectangle.ALIGN_CENTER:
//			return com.lowagie.text.Table.ALIGN_CENTER;
//		case com.oa.common.report.Rectangle.ALIGN_RIGHT:
//			return com.lowagie.text.Table.ALIGN_RIGHT;
//		default:
//			throw new RuntimeException("Paramerter is not correct!");
//		}
//	}
//
//	/**
//	 * 获取垂直对齐方式常量
//	 * @param i
//	 * @return
//	 */
//	private int getVAlign(int i) {
//		switch (i) {
//		case com.oa.common.report.Rectangle.VALIGN_TOP:
//			return com.lowagie.text.Table.ALIGN_TOP;
//		case com.oa.common.report.Rectangle.VALIGN_MIDDLE:
//			return com.lowagie.text.Table.ALIGN_MIDDLE;
//		case com.oa.common.report.Rectangle.VALIGN_BOTTOM:
//			return com.lowagie.text.Table.ALIGN_BOTTOM;
//		default:
//			throw new RuntimeException("�޷�ʶ���VALIGN����");
//		}
//
//	}
//
//	/**
//	 * 
//	 * @param itextRect
//	 * @param reportRect
//	 */
//	private void setMultiParam(com.lowagie.text.Rectangle itextRect,
//			com.oa.common.report.Rectangle reportRect) {
//		itextRect.setBorderWidth(reportRect.getBorder() - 1);
//		itextRect.setBorderColor(reportRect.getBorderColor());
//		itextRect.setBackgroundColor(reportRect.getBackgroudColor());
//	}
//
//	
//	public void print(com.oa.common.report.Report r, OutputStream result)throws ReportException, IOException {
//		print(r, new PDFCss(), result);
//	}
//
//	
//	public void print(com.oa.common.report.Report r, PDFCss css,
//			OutputStream result) throws ReportException, IOException {
//		try {
//			Document document = new Document();
//			PdfWriter writer = PdfWriter.getInstance(document, result);
//			setHeaderFooter(document);
//			document.open();
//			if (r.getHeaderTable() != null) {
//				print(r.getHeaderTable(), css, document);
//			}
//			if (r.getBody() != null) {
//
//				print(r.getBody(), css, document);
//			}
//			if (r.getFooterTable() != null) {
//				print(r.getFooterTable(), css, document);
//			}
//			document.close();
//		} catch (DocumentException ex) {
//			ex.printStackTrace();
//			throw new ReportException(ex.getMessage());
//		}
//	}
//
//	
//	private void setHeaderFooter(Document document) {
//		if(beforePhrase!=null&&afterPhrase!=null){
//			HeaderFooter header =new HeaderFooter(new Phrase(beforePhrase,defaultFont),new Phrase(afterPhrase,defaultFont));
//			header.setAlignment(Element.ALIGN_RIGHT);
//			header.setBorder(0);			
//			document.setFooter(header);
//		}
//		if(comp!=null){
//			HeaderFooter footer = new HeaderFooter(new Phrase(comp, defaultFont),false);
//			footer.setAlignment(Element.ALIGN_CENTER);
//			footer.setBorder(com.lowagie.text.Rectangle.BOTTOM);
//			document.setHeader(footer);
//		}
//	}
//
//	private void print(com.oa.common.report.ReportBody body, PDFCss css,
//			Document document) throws ReportException, IOException,
//			DocumentException {
//		Object[] datas = body.getDatas();
//		for (int t = 0; t < datas.length; t++) {
//			if (datas[t] instanceof Table) {
//				com.oa.common.report.Table data = ((Table) datas[t]).deepClone();
//				data = CssEngine.applyCss(data);
//				com.oa.common.report.Table header = body.getTableColHeader();
//				header = CssEngine.applyCss(header);
//				if (header != null) {
//					for (int i = header.getRowCount() - 1; i >= 0; i--) {
//						data.insertRow(0, header.getRow(i));
//					}
//				}
//				print(data, css, document);
//			}else if (datas[t] instanceof com.lowagie.text.Table){
//				print((com.lowagie.text.Table)datas[t],css,document);
//			}
//		}
//	}
//
//	private void print(com.oa.common.report.Table t, PDFCss css,
//			Document document) throws ReportException, IOException,DocumentException {
//		t = CssEngine.applyCss(t);
//		com.lowagie.text.Table table = new com.lowagie.text.Table(t.getColCount());
//		if (t.getWidths() != null)
//			table.setWidths(t.getWidths());
//		table.setWidth(t.getWidth());
//		table.setSpacing(t.getCellspacing());
//		table.setPadding(t.getCellpadding());
//		table.setAlignment(getAlign(t.getAlign()));
//		setMultiParam(table, t);
//		boolean haveBorder = false;
//		if (t.getBorder() <= 0) {
//			haveBorder = false;
//		} else {
//			haveBorder = true;
//		}
//		for (int i = 0; i < t.getRowCount(); i++) {
//			print(t.getRow(i), css, table, haveBorder);
//		}
//		document.add(table);
//	}
//	
//	private void printFoot(com.oa.common.report.Table t, PDFCss css,
//			Document document) throws ReportException, IOException,
//			DocumentException {
//		String footer=(String)t.getRow(0).getCell(0).getContent();
//		BaseFont bfComic = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
//		Font font = new Font(bfComic, t.getFooterFontSize(),t.getFooterFontType());
//		com.lowagie.text.Table table = new com.lowagie.text.Table(1);
//		table.setWidth(t.getWidth());
//		table.setSpacing(0);
//		table.setPadding(1);
//		table.setBorder(0);		
//		table.setBorderColor(Color.WHITE);
//		table.setAlignment(Table.ALIGN_CENTER);	
//		Cell c=new Cell();
//		c.addElement(new Paragraph(footer,font));
//		c.setBorder(0);
//		table.addCell(c, 0,0);
//		document.add(table);		
//	}
//
//	
//	public void print(com.oa.common.report.Report[] rs, PDFCss css,
//			OutputStream result, Rectangle page) throws ReportException,
//			IOException {
//		try {
//			Document document = new Document(page);
//			PdfWriter writer = PdfWriter.getInstance(document, result);
//			setHeaderFooter(document); 
//			document.addTitle("report");
//			document.addAuthor("chenh");			
//			document.open();
//			for (int i = 0; i < rs.length; i++) {
//				if (i > 0) {
//					document.newPage();								
//				}
//				if (rs[i].getHeaderTable() != null) {
//					print(rs[i].getHeaderTable(), css, document);
//				}
//				if (rs[i].getBody() != null) {
//					print(rs[i].getBody(), css, document);
//				}				
//				if (rs[i].getFooterTable() != null) {					
//					printFoot(rs[i].getFooterTable(),css,document);
//				}
//			}
//			if(rect!=null){//画斜线
//				PdfContentByte cb = writer.getDirectContent();
//				cb.setLineWidth(1f);
//				float height=document.getPageSize().height();
//				cb.moveTo(rect.left(),height-rect.bottom());
//				cb.lineTo(rect.right(),height-rect.top());
//				cb.stroke();
//				BaseFont bFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
//				cb.setFontAndSize(bFont,10);
//				cb.beginText();
//				cb.setTextMatrix(rect.left()+5,height-rect.bottom()-rect.height()/2-5);
//				cb.showText(vName); 
//				cb.endText();
//				cb.beginText();
//				cb.setTextMatrix(rect.left()+rect.width()*2/3,height-rect.bottom()-rect.height()*2/3);
//				cb.showText(hName); 
//				cb.endText();
//			}
//			document.close();
//		} catch (DocumentException ex) {
//			ex.printStackTrace();
//			throw new ReportException(ex.getMessage());
//		}
//	}
//	
//	public void print(com.oa.common.report.Report[] rs, PDFCss css,
//			OutputStream result, Rectangle page,int x,int y,int w,int h) throws ReportException,
//			IOException {
//		try {
//			Document document = new Document(page,x,y,w,h);
//			PdfWriter writer = PdfWriter.getInstance(document, result);
//
//			setHeaderFooter(document);
//			document.addTitle("report");
//			document.addAuthor("chenh");			
//			document.open();
//			for (int i = 0; i < rs.length; i++) {
//				if (i > 0) {
//					document.newPage();								
//				}				
//				if (rs[i].getHeaderTable() != null) {
//					print(rs[i].getHeaderTable(), css, document);
//				}
//				if (rs[i].getBody() != null) {
//					print(rs[i].getBody(), css, document);
//				}				
//				if (rs[i].getFooterTable() != null) {					
//					printFoot(rs[i].getFooterTable(),css,document);
//				}
//			}								
//			document.close();
//		} catch (DocumentException ex) {
//			ex.printStackTrace();
//			throw new ReportException(ex.getMessage());
//		}
//	}
//
//	public com.lowagie.text.Table convertTable(Table t) throws Exception {
//		t = CssEngine.applyCss(t);
//		com.lowagie.text.Table table = new com.lowagie.text.Table(t.getColCount());
//		if (t.getWidths() != null){
//			table.setWidths(t.getWidths());
//		}
//		table.setWidth(t.getWidth());
//		table.setSpacing(t.getCellspacing());
//		table.setPadding(t.getCellpadding());
//		table.setAlignment(getAlign(t.getAlign()));
//		setMultiParam(table, t);
//
//		boolean haveBorder = false;
//		if (t.getBorder() <= 0) {
//			haveBorder = false;
//		} else {
//			haveBorder = true;
//		}
//
//		BaseFont bfChinese = BaseFont.createFont("MSung-Light",
//				"UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
//		BaseFont bfChineseBold = BaseFont.createFont("MSung-Light,Bold",
//				"UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
//		Font FontChinese = new Font(bfChinese, 10, Font.NORMAL);
//		Font FontChineseBold = new Font(bfChineseBold, 10, Font.NORMAL);
//		PDFCss css = new PDFCss();
//
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
//		item.setFont(new Font(bfChinese, 10, Font.NORMAL));
//		css.setData(item);
//
//		item = new PDFCssItem();
//		item.setFont(new Font(bfChinese, 7, Font.NORMAL));
//		item.setBackgroudColor(new Color(0xffdead));
//		css.setCrossHeadHead(item);
//
//		for (int i = 0; i < t.getRowCount(); i++) {
//			print(t.getRow(i), css, table, haveBorder);
//		}
//		return table;
//	}
//
//	private void print(com.lowagie.text.Table table, PDFCss css,
//			Document document) throws ReportException, IOException,
//			DocumentException  {
//		document.add(table);
//	}
//}