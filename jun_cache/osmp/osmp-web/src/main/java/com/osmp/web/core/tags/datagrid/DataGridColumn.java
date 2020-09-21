/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.tags.datagrid;

/**
 * 
 * @author wangkaiping
 * @version V1.0, 2013-4-12 下午04:08:28
 */
public class DataGridColumn {

	protected String title;
	protected String field;
	protected Integer width;
	protected String rowspan;
	protected String colspan;
	protected String align;
	protected boolean sortable;
	protected boolean checkbox;
	protected String formatter;
	protected boolean hidden;
	protected String treefield;
	protected boolean image;
	protected boolean query;
	protected String url;
	protected String funname = "openwindow";
	protected String arg;
	protected String frozenColumns;

	public String getFrozenColumns() {
		return frozenColumns;
	}

	public void setFrozenColumns(String frozenColumns) {
		this.frozenColumns = frozenColumns;
	}

	public DataGridColumn(String title, String field, Integer width, String rowspan, String colspan, String align,
			boolean sortable, boolean checkbox, String formatter, boolean hidden, String treefield, boolean image,
			boolean query, String url, String funname, String arg, String frozenColumns) {
		super();
		this.title = title;
		this.field = field;
		this.width = width;
		this.rowspan = rowspan;
		this.colspan = colspan;
		this.align = align;
		this.sortable = sortable;
		this.checkbox = checkbox;
		this.formatter = formatter;
		this.hidden = hidden;
		this.treefield = treefield;
		this.image = image;
		this.query = query;
		this.url = url;
		this.funname = funname;
		this.arg = arg;
		this.frozenColumns = frozenColumns;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getRowspan() {
		return rowspan;
	}

	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}

	public String getColspan() {
		return colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getTreefield() {
		return treefield;
	}

	public void setTreefield(String treefield) {
		this.treefield = treefield;
	}

	public boolean isImage() {
		return image;
	}

	public void setImage(boolean image) {
		this.image = image;
	}

	public boolean isQuery() {
		return query;
	}

	public void setQuery(boolean query) {
		this.query = query;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFunname() {
		return funname;
	}

	public void setFunname(String funname) {
		this.funname = funname;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

}
