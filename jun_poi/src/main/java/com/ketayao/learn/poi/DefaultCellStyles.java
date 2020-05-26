package com.ketayao.learn.poi;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/** 
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年9月29日 上午11:45:03 
 */
public class DefaultCellStyles implements CellStyles {
	
	protected Workbook wb;
	
	private Map<ExcelStyle, CellStyle> styles;
	
	public enum ExcelStyle {
		HEADER_CELL("headerCell"), 
		STRING_CELL("stringCell"), 
		DEATE_CELL("dateCell"), 
		NUMBER_CELL("numberCell"), 
		TOTAL_CELL("totalCell"), 
		BOLD_TITLE_CELL("boldTitleCell");
		
		private final String style;

		ExcelStyle(String style) {
			this.style = style;
		}

		public String getStyle() {
			return this.style;
		}
	}

	public enum ExcelFormat {
		DATE("yyyy-MM-dd"), 
		DATETIME("yyyy-MM-dd HH:mm:ss"),
		NUMBER("0.00"), 
		CURRENCY("#,##0.00"), 
		PERCENT("0.00%");
		private final String pattern;

		ExcelFormat(String pattern) {
			this.pattern = pattern;
		}

		public String getPattern() {
			return this.pattern;
		}
	}
	
	public DefaultCellStyles(Workbook workbook) {
		this.wb = workbook;
		this.styles = createStyles();
	}

	/* (non-Javadoc)
	 * @see com.ketayao.learn.poi.CellStyles#setWorkBook(org.apache.poi.ss.usermodel.Workbook)
	 */
	@Override
	public void setWorkBook(Workbook workbook) {
		this.wb = workbook;
	}

	/* (non-Javadoc)
	 * @see com.ketayao.learn.poi.CellStyles#getHeaderStyle()
	 */
	@Override
	public CellStyle getHeaderStyle() {
		return styles.get(ExcelStyle.HEADER_CELL);
	}

	/* (non-Javadoc)
	 * @see com.ketayao.learn.poi.CellStyles#getStringStyle()
	 */
	@Override
	public CellStyle getStringStyle() {
		return styles.get(ExcelStyle.STRING_CELL);
	}

	/* (non-Javadoc)
	 * @see com.ketayao.learn.poi.CellStyles#getDateStyle()
	 */
	@Override
	public CellStyle getDateStyle() {
		return styles.get(ExcelStyle.DEATE_CELL);
	}

	/* (non-Javadoc)
	 * @see com.ketayao.learn.poi.CellStyles#getNumberStyle()
	 */
	@Override
	public CellStyle getNumberStyle() {
		return styles.get(ExcelStyle.NUMBER_CELL);
	}

	/* (non-Javadoc)
	 * @see com.ketayao.learn.poi.CellStyles#getFormulaStyle()
	 */
	@Override
	public CellStyle getFormulaStyle() {
		return styles.get(ExcelStyle.TOTAL_CELL);
	}

	protected Map<ExcelStyle, CellStyle> createStyles() {
		Map<ExcelStyle, CellStyle> styles = new HashMap<ExcelStyle, CellStyle>();
		DataFormat df = wb.createDataFormat();

		// --字体设定 --//

		//普通字体
		Font normalFont = wb.createFont();
		normalFont.setFontHeightInPoints((short) 10);

		//加粗字体
		Font boldFont = wb.createFont();
		boldFont.setFontHeightInPoints((short) 10);
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		//加粗标题字体
		Font boldTitleFont = wb.createFont();
		boldTitleFont.setFontHeightInPoints((short) 14);
		boldTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		//蓝色加粗字体
		Font blueBoldFont = wb.createFont();
		blueBoldFont.setFontHeightInPoints((short) 10);
		blueBoldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		blueBoldFont.setColor(IndexedColors.BLUE.getIndex());

		// --Cell Style设定-- //
		
		//加粗标题格式
		CellStyle boldTitleStyle = wb.createCellStyle();
		boldTitleStyle.setFont(boldTitleFont);
		boldTitleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		boldTitleStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		//setBorder(boldTitleStyle);
		styles.put(ExcelStyle.BOLD_TITLE_CELL, boldTitleStyle);

		//标题格式
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFont(boldFont);
		headerStyle.setFont(normalFont);
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		setBorder(headerStyle);
		styles.put(ExcelStyle.HEADER_CELL, headerStyle);
		
		//String格式
		CellStyle stringStyle = wb.createCellStyle();
		stringStyle.setFont(normalFont);
		setBorder(stringStyle);
		styles.put(ExcelStyle.STRING_CELL, stringStyle);

		//日期格式
		CellStyle dateCellStyle = wb.createCellStyle();
		dateCellStyle.setFont(normalFont);
		dateCellStyle.setDataFormat(df.getFormat(ExcelFormat.DATE.getPattern()));
		setBorder(dateCellStyle);
		styles.put(ExcelStyle.DEATE_CELL, dateCellStyle);

		//数字格式
		CellStyle numberCellStyle = wb.createCellStyle();
		numberCellStyle.setFont(normalFont);
		numberCellStyle.setDataFormat(df.getFormat(ExcelFormat.NUMBER.getPattern()));
		setBorder(numberCellStyle);
		styles.put(ExcelStyle.NUMBER_CELL, numberCellStyle);

		//合计列格式
		CellStyle totalStyle = wb.createCellStyle();
		totalStyle.setFont(blueBoldFont);
		totalStyle.setWrapText(true);
		totalStyle.setAlignment(CellStyle.ALIGN_CENTER);
		setBorder(totalStyle);
		styles.put(ExcelStyle.TOTAL_CELL, totalStyle);

		return styles;
	}

	protected void setBorder(CellStyle style) {
		//设置边框
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	}
}
