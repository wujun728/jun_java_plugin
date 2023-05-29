package com.sanri.excel.poi;

import com.sanri.excel.poi.converter.ExcelConverter;
import com.sanri.excel.poi.enums.CellType;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * 
 * 作者:sanri <br/>
 * 时间:2017-8-12下午2:47:48<br/>
 * 功能:列配置,用于排序 <br/>
 */
public class ColumnConfig implements Comparable<ColumnConfig>{
	private boolean hidden;
	private boolean chineseWidth;
	private boolean trim;

	private String pattern;
	private String chinese;
	private int width;
	private int charWidth;
	private int pxWidth;
	private int order;
	private int precision;

	//属性名,必须不为空
	private String propertyName;
	private Method readMethod;
	private Method writeMethod;
	private Class<?> dataType;
	private CellType cellType;
	private ExcelConverter excelConverter;
	
	public ColumnConfig(String propertyName, Method readMethod, Method writeMethod) {
		super();
		this.propertyName = propertyName;
		this.readMethod = readMethod;
		this.writeMethod = writeMethod;
	}
	
	/**
	 * 
	 * 作者:sanri <br/>
	 * 时间:2017-8-12下午2:56:02<br/>
	 * 功能:配置所有的配置,如果传入值为非默认值,则配置 <br/>
	 * @param chinese
	 * @param width
	 * @param index
	 * @param hidden
	 * @param pattern
	 */
	public void config(String chinese,int width,int charWidth,int pxWidth,int index,boolean hidden,String pattern,boolean chineseWidth,boolean trim,CellType cellType,int precision){
		if(StringUtils.isNotBlank(chinese)){
			this.chinese = chinese;
		}
		this.width = width;
		this.charWidth = charWidth;
		this.pxWidth = pxWidth;
		if(index != -1){
			this.order = index;
		}
		this.hidden = hidden;
		if(StringUtils.isNotBlank(pattern)){
			this.pattern = pattern;
		}
		this.precision = precision;
		this.chineseWidth = chineseWidth;
		this.cellType = cellType;
		this.trim = trim;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Method getReadMethod() {
		return readMethod;
	}

	public void setReadMethod(Method readMethod) {
		this.readMethod = readMethod;
	}

	public Method getWriteMethod() {
		return writeMethod;
	}

	public void setWriteMethod(Method writeMethod) {
		this.writeMethod = writeMethod;
	}
	
	public int compareTo(ColumnConfig o) {
		if(this.order != -1 && o.order != -1){
			return this.order - o.order;
		}
		return this.propertyName.compareTo(o.propertyName);
	}

	public Class<?> getDataType() {
		return dataType;
	}

	public void setDataType(Class<?> dataType) {
		this.dataType = dataType;
	}

	public boolean isChineseWidth() {
		return chineseWidth;
	}

	public void setChineseWidth(boolean chineseWidth) {
		this.chineseWidth = chineseWidth;
	}

	public int getCharWidth() {
		return charWidth;
	}

	public void setCharWidth(int charWidth) {
		this.charWidth = charWidth;
	}

	public int getPxWidth() {
		return pxWidth;
	}

	public void setPxWidth(int pxWidth) {
		this.pxWidth = pxWidth;
	}

	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

	public boolean isTrim() {
		return trim;
	}

	public void setTrim(boolean trim) {
		this.trim = trim;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public ExcelConverter getExcelConverter() {
		return excelConverter;
	}

	public void setExcelConverter(ExcelConverter excelConverter) {
		this.excelConverter = excelConverter;
	}
}
