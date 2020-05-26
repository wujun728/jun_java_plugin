package excel;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseSheel {
	protected static Logger log = LoggerFactory.getLogger(BaseSheel.class);
	protected Sheet sheet;
	protected SXSSFWorkbook wb;
	protected Map<String, CellStyle> styles;
	protected int rownum=0;
	
	protected BaseSheel(SXSSFWorkbook wb,String name,Map<String, CellStyle> styles) {
		this.sheet = wb.createSheet(name);
		this.styles = styles;
		this.wb = wb;
	}
	
	
	protected void initialize(String title, List<String> headerList,int[] width) {
		// Create title
		if (StringUtils.isNotBlank(title)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), titleRow.getRowNum(), headerList.size()-1));
		}
		// Create header
		if (headerList == null){
			throw new RuntimeException("headerList not null!");
		}
		Row headerRow = sheet.createRow(rownum++);
		headerRow.setHeightInPoints(16);
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);//创建�?��单元�?
			cell.setCellStyle(styles.get("header"));//设置单元格样�?
			
			String[] ss = StringUtils.split(headerList.get(i), "**", 2);
			
			if (ss.length==2){
				//如果有注释，则加上注�?
				cell.setCellValue(ss[0]);
				Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
						new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			}else{
				
				cell.setCellValue(headerList.get(i));
			}
			sheet.autoSizeColumn(i);
		}
		if(width!=null){
			for (int i = 0; i < width.length; i++) {  
			        sheet.setColumnWidth(i, width[i] < 3000 ? 3000 : width[i]);  
				}
		}
		
		log.debug("Initialize success.");
	}
	
	/**
	 * 添加�?��
	 * @return 行对�?
	 */
	public Row addRow(){
		return sheet.createRow(rownum++);
	}
	
	/**
	 * 添加�?��单元�?
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加�?
	 * @return 单元格对�?
	 */
	public Cell addCell(Row row, int column, Object val){
		return this.addCell(row, column, val, 1, Class.class);
	}
	
	/**
	 * 添加�?��单元�?
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加�?
	 * @param align 对齐方式�?：靠左；2：居中；3：靠右）
	 * @return 单元格对�?
	 */
	public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType){
		Cell cell = row.createCell(column);
		DecimalFormat df = new DecimalFormat("#.00");
	//	CellStyle style = styles.get("data"+(align>=1&&align<=3?align:""));
		try {
			if (val == null){
				cell.setCellValue("");
			} else if (val instanceof String) {
				cell.setCellValue((String) val);
			} else if (val instanceof Integer) {
				cell.setCellValue((Integer) val);
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
			} else if (val instanceof Double) {
				
				cell.setCellValue(df.format(val));
			} else if (val instanceof Float) {
				cell.setCellValue(df.format(val));
			} else if (val instanceof Date) {
				CellStyle	style =styles.get("data"+(align>=1&&align<=3?align:""));
				DataFormat format = wb.createDataFormat();
	            style.setDataFormat(format.getFormat("yyyy-MM-dd"));
				cell.setCellValue((Date) val);
				//cell.setCellStyle(style);
			} else {
				if (fieldType != Class.class){
					cell.setCellValue((String)fieldType.getMethod("setValue", Object.class).invoke(null, val));
				}else{
					cell.setCellValue((String)Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), 
						"fieldtype."+val.getClass().getSimpleName()+"Type")).getMethod("setValue", Object.class).invoke(null, val));
				}
			}
		} catch (Exception ex) {
			log.info("设置cell�?["+row.getRowNum()+","+column+"] 错误: " + ex.toString());
			cell.setCellValue(val.toString());
		}
	//	cell.setCellStyle(style);
		return cell;
	}
	
}
