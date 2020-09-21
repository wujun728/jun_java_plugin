/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.tags.datagrid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 封装数据表格
 * 
 * @author wangkaiping
 * @version V1.0, 2013-4-12 下午02:23:39
 */
public class DataGridTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	/**
	 * 列表类型 datagrid treegrid
	 */
	protected String dataGridType = "datagrid";

	/**
	 * 标签名称
	 */
	protected String name;

	/**
	 * 表格自适应
	 */
	protected boolean fit = true;

	/**
	 * 是否远程排序
	 */
	protected boolean remoteSort = true;

	/**
	 * 标签标题
	 */
	protected String title;

	/**
	 * 字段名
	 */
	protected String idField = "id";

	/**
	 * 树列表字段
	 */
	protected String treeField = "name";

	/**
	 * 列表表单提交的URL
	 */
	protected String actionUrl;

	/**
	 * 宽度
	 */
	protected String width;

	/**
	 * 高度
	 */
	protected String height;

	/**
	 * 定义可以排序的列
	 */
	protected String sortName = "name";

	/**
	 * 排序模式 默认倒序
	 */
	protected String sortOrder = "desc";

	/**
	 * 是否要复选框(如果singleSelect为true则不会显示复选框)
	 */
	protected boolean checkbox = true;

	/**
	 * 是否只能单选，如果是的话 则没有前面的复选框
	 */
	protected boolean singleSelect = true;

	/**
	 * 是否显示分页
	 */
	protected boolean showPageList = true;

	/**
	 * 列宽自适应
	 */
	protected boolean fitColumns = true;

	/**
	 * 所有的字段
	 */
	protected String fields = "";
	/**
	 * 是否显示行号
	 */
	protected boolean rownumbers = true;

	/**
	 * 列标识
	 */
	protected List<DataGridColumn> columnList = new ArrayList<DataGridColumn>();

	/**
	 * 列的某些值动态转换
	 */
	protected List<ColumnValue> columnValueList = new ArrayList<ColumnValue>();

	/**
	 * 冻结列集合
	 */
	protected List<DataGridColumn> frozenColumnsList = new ArrayList<DataGridColumn>();

	/**
	 * toolbar集合
	 */
	protected List<DataGridToolBar> dateGridToolBarList = new ArrayList<DataGridToolBar>();

	/**
	 * 列操作项
	 */
	protected List<DataGridOpt> opts = new ArrayList<DataGridOpt>();

	public boolean isRemoteSort() {
		return remoteSort;
	}

	public void setRemoteSort(boolean remoteSort) {
		this.remoteSort = remoteSort;
	}

	public boolean isFit() {
		return fit;
	}

	public void setFit(boolean fit) {
		this.fit = fit;
	}

	public String getDataGridType() {
		return dataGridType;
	}

	public void setDataGridType(String dataGridType) {
		this.dataGridType = dataGridType;
	}

	public String getTreeField() {
		return treeField;
	}

	public void setTreeField(String treeField) {
		this.treeField = treeField;
	}

	public boolean isSingleSelect() {
		return singleSelect;
	}

	public List<DataGridColumn> getFrozenColumnsList() {
		return frozenColumnsList;
	}

	public List<DataGridToolBar> getDateGridToolBarList() {
		return dateGridToolBarList;
	}

	public void setDateGridToolBarList(List<DataGridToolBar> dateGridToolBarList) {
		this.dateGridToolBarList = dateGridToolBarList;
	}

	public void setFrozenColumnsList(List<DataGridColumn> frozenColumnsList) {
		this.frozenColumnsList = frozenColumnsList;
	}

	public void setSingleSelect(boolean singleSelect) {
		this.singleSelect = singleSelect;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public boolean isRownumbers() {
		return rownumbers;
	}

	public void setRownumbers(boolean rownumbers) {
		this.rownumbers = rownumbers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public boolean isShowPageList() {
		return showPageList;
	}

	public void setShowPageList(boolean showPageList) {
		this.showPageList = showPageList;
	}

	public boolean isFitColumns() {
		return fitColumns;
	}

	public void setFitColumns(boolean fitColumns) {
		this.fitColumns = fitColumns;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setColumnss(String title, String field, Integer width, String rowspan, String colspan, String align,
			boolean sortable, boolean checkbox, String formatter, boolean hidden, String replace, String treefield,
			boolean image, boolean query, String url, String funname, String arg, String frozenColumns) {
		DataGridColumn dataGridColumn = new DataGridColumn(title, field, width, rowspan, colspan, align, sortable,
				checkbox, formatter, hidden, treefield, image, query, url, funname, arg, frozenColumns);

		this.columnList.add(dataGridColumn);
		if ("opt".equals(field)) {
			this.fields = (this.fields + field + ",");
		}
		if (replace != null) {
			String[] test = replace.split(",");
			String text = "";
			String value = "";
			for (String string : test) {
				text = text + string.substring(0, string.indexOf("_")) + ",";
				value = value + string.substring(string.indexOf("_") + 1) + ",";
			}
			setColumn(field, text, value);
		}
	}

	/**
	 * 填充需要动态转换的值
	 * 
	 * @param name
	 * @param text
	 * @param value
	 */
	public void setColumn(String name, String text, String value) {
		ColumnValue columnValue = new ColumnValue(name, text, value);
		this.columnValueList.add(columnValue);
	}

	/**
	 * 填充操作项数据
	 * 
	 * @param name
	 * @param url
	 * @param jsName
	 */
	public void setDateGridOpts(String name, String url, String jsName, String filter) {
		DataGridOpt opt = new DataGridOpt(name, url, jsName, filter);
		this.opts.add(opt);
	}

	/**
	 * 填充toolbar集合
	 */
	public void setDateGridToolBar(String title, String jsName, String icon) {
		DataGridToolBar bar = new DataGridToolBar(title, jsName, icon);
		this.dateGridToolBarList.add(bar);
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		try {
			// System.out.println(this.doEng());
			out.print(this.doEng());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		this.columnList.clear();
		this.columnValueList.clear();
		this.frozenColumnsList.clear();// 清空冻结列
		this.dateGridToolBarList.clear();// 清空toolbar集合
		this.opts.clear();
		return super.doStartTag();
	}

	public String doEng() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("$(function(){\n");
		sb.append("		$('#" + this.name + "')." + this.dataGridType + "({\n");
		sb.append("			idField: '" + this.idField + "',\n");
		if (this.dataGridType.equals("treegrid")) {
			sb.append("			treeField:'" + this.treeField + "',\n");
		}
		if (null != this.title) {
			sb.append("			title : '" + this.title + "',\n");
		}
		if (null != this.width) {
			sb.append("			width : '" + this.width + "',\n");
		}
		if (null != this.height) {
			sb.append("			height : '" + this.height + "',\n");
		}
		sb.append("			url : '" + this.actionUrl + "',\n");
		sb.append("			fit : " + this.fit + ",\n");
		sb.append("			remoteSort : " + this.remoteSort + ",\n");
		if (this.showPageList) {
			sb.append("			pagination : true,\n");
		} else {
			sb.append("			pagination : false,\n");
		}
		if (this.rownumbers) {
			sb.append("			rownumbers : true,\n");
		}
		if (this.singleSelect) {
			sb.append("			singleSelect : true,\n");
		}
		sb.append("			fitColumns : " + this.fitColumns + ",\n");
		sb.append("			pageSize : 20,\n");
		sb.append("			sortName : '" + this.sortName + "',\n");
		sb.append("			sortOrder : '" + this.sortOrder + "',\n");
		sb.append("			striped : true,\n");
		sb.append("			\n");

		this.tagFrozenColumnsList();
		this.createFrozenColumns(frozenColumnsList, sb);

		sb.append("			columns:[[\n");
		this.createColumn(sb);
		sb.append("			]],\n");

		// 添加toolbar信息
		if (null != dateGridToolBarList && dateGridToolBarList.size() > 0) {
			sb.append("			toolbar:[\n");
			int toolBarIndex = 0;
			for (DataGridToolBar bar : dateGridToolBarList) {
				if (toolBarIndex == 0) {
					sb.append("			{id:'btnadd',text:'" + bar.getTitle() + "',iconCls:'" + bar.getIcon()
							+ "',handler:function (){" + bar.getJsName() + "}}\n");
				} else {
					sb.append("			,'-',{id:'btnadd',text:'" + bar.getTitle() + "',iconCls:'" + bar.getIcon()
							+ "',handler:function (){" + bar.getJsName() + "}}\n");
				}
				toolBarIndex++;
			}
			sb.append("			],\n");
		}

		sb.append("		onClickRow:function(rowIndex,rowData){\n");
		sb.append("			rowid=rowData.id;\n");
		sb.append("			gridname='" + this.name + "';\n");
		sb.append("		},\n");
		/****
		 * 
		 * 添加treegrid双击展开或折叠一个节点start
		 */
		sb.append("		onDblClickRow:function(row){");
		sb.append("if(row){\n");
		sb.append("if(row.state=='closed'){\n");
		sb.append("$(this).treegrid('expand',row.id);");
		sb.append("						}else{\n");
		sb.append("$(this).treegrid('collapse',row.id);");
		sb.append("				}\n");
		sb.append("				}\n");
		sb.append("		}\n");
		/****
		 * treegrid双击事件结束
		 */
		sb.append("		});\n");
		this.paginationOnClick(showPageList, sb);
		sb.append("});\n");

		sb.append("</script>\n");

		sb.append("<table width=\"100%\"   id=\"" + this.name + "\"></table>");
		return sb.toString();
	}

	/**
	 * 获取冻结列集合，没有size为0
	 */
	public void tagFrozenColumnsList() {
		for (int i = 0; i < columnList.size(); i++) {
			DataGridColumn co = columnList.get(i);
			if (null != co && null != co.getFrozenColumns() && co.getFrozenColumns().trim().equals("true")) {
				frozenColumnsList.add(co);
			}
		}
	}

	private void createFrozenColumns(List<DataGridColumn> list, StringBuffer sb) {
		if (null != list && list.size() > 0) {
			sb.append("			frozenColumns:[[\n");
			if (!this.singleSelect && this.checkbox) {
				sb.append("				{field:'ck', checkbox:'true'},\n");
			}
			for (int i = 0; i < list.size(); i++) {
				DataGridColumn co1 = list.get(i);
				sb.append("				{title:'" + co1.getTitle() + "',field:'" + co1.getField() + "',width:" + co1.getWidth()
						+ ",align:'" + co1.getAlign() + "',sortable:" + co1.isSortable() + "},\n");
			}
			sb.append("			]],\n");
		}
	}

	public void createColumn(StringBuffer sb) {
		// 首先移除冻结列
		for (DataGridColumn dc : frozenColumnsList) {
			this.columnList.remove(dc);
		}
		// 判断是否移除多选框
		if (!this.singleSelect && this.checkbox && this.frozenColumnsList.size() <= 0) {
			sb.append("				{field:'ck', checkbox:'true'},\n");
		}
		if (this.columnList.size() > 0) {
			int index = 0;
			for (DataGridColumn i : this.columnList) {
				index++;
				int width = i.getWidth();
				if (width == 0) {
					width = i.getTitle().length() * 15;
				}
				if ("opt".equals(i.getField())) {
					if (null != i.getFormatter() && i.getFormatter().length() > 0) {
						sb.append("				{field:'opt',title:'" + i.getTitle() + "',align:'center',width:" + i.getWidth()
								+ ",formatter:" + i.getFormatter() + "}");
					} else {
						sb.append("				{field:'opt',title:'" + i.getTitle() + "',align:'center',width:" + i.getWidth()
								+ ",formatter:function(value,rowData,index)\n");
						sb.append("					{\n");
						sb.append("						var href='';\n");
						for (DataGridOpt k : this.opts) {
							if (null != k.getFilter() && !k.getFilter().equals("")) {
								String filter = k.getFilter().trim();
								String[] filterArr = filter.split(",");
								for (String filterItem : filterArr) {
									String[] item = filterItem.split("_");
									if (null != item && item.length == 2) {
										sb.append("						var temp = rowData." + item[0] + "+\"\"\n");
										sb.append("						if(\"" + item[1] + "\".indexOf(temp) > -1){\n");
										sb.append("							href += \"[<a href='#' onclick=" + k.getJsName() + ">"
												+ k.getName() + "</a>]\";\n");
										sb.append("}\n");
									}
								}
							} else {
								sb.append("						href += \"[<a href='#' onclick=" + k.getJsName() + ">" + k.getName()
										+ "</a>]\";\n");
							}

						}
						sb.append("						return href;\n");
						sb.append("					}\n");
						sb.append("				}\n");
						sb.append("\n");
					}
				} else {
					sb.append("				{field:'" + i.getField() + "', title:'" + i.getTitle() + "',align:'" + i.getAlign()
							+ "', width:" + width + ", sortable:" + i.isSortable());
					for (ColumnValue j : this.columnValueList) {
						if (i.getField().equals(j.getName())) {
							sb.append(",formatter:function(value,rowData,index){\n");
							String[] text = j.getText().split(",");
							String[] value = j.getValue().split(",");
							for (int k = 0; k < text.length; k++) {
								sb.append("					if(value=='" + value[k] + "'){return '" + text[k] + "'}\n");
							}
							sb.append("					else{return value}\n");
							sb.append("				}");
						}
					}
					if (null != i.getFormatter() && i.getFormatter().length() > 0) {
						sb.append(",formatter:" + i.getFormatter());
					}
					if (index == this.columnList.size()) {
						sb.append("}\n");
					} else {
						sb.append("},\n");
					}
				}
			}
		}
	}

	/**
	 * 分页响应事件
	 * 
	 * @param showPageList
	 * @param sb
	 */
	public void paginationOnClick(boolean showPageList, StringBuffer sb) {
		if (showPageList == true) {
			sb.append("\n");
			sb.append("\n");
			sb.append("		$('#" + this.name + "')." + this.dataGridType + "('getPager').pagination({\n");
			sb.append("			beforePageText:'',\n");
			sb.append("			afterPageText:'/{pages}',\n");
			sb.append("			showPageList:true,\n");
			// sb.append("			pageList:[10,20,30],\n");
			sb.append("			showRefresh:true\n");
			sb.append("		});");

			sb.append("\n");
			sb.append("\n");

			sb.append("		$('#" + this.name + "')." + this.dataGridType + "('getPager').pagination({\n");
			sb.append("			onBeforeRefresh:function(pageNumber, pageSize){\n");
			sb.append("			$(this).pagination('loading');\n");
			sb.append("			$(this).pagination('loaded');\n");
			sb.append("			}\n");
			sb.append("		});\n");

		}
	}

	public void optsJSMethod(StringBuffer sb, List<DataGridOpt> opts) {
		if (null != opts && opts.size() > 0) {
			for (DataGridOpt i : opts) {
				String jsName = i.getJsName();
				if (null != jsName && jsName.trim().length() > 0) {
					sb.append("\n");
					sb.append("function " + jsName + this.name + "(url){\n");
					sb.append("		$('#" + this.name + "')." + this.dataGridType + "({\n");
					sb.append("			url:url\n");
					sb.append("		});\n");
					sb.append("}\n");
					sb.append("\n");
				}
			}
		}
	}

}
