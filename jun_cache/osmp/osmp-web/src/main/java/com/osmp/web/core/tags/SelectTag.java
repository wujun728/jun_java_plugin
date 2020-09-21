/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Description:选择控件
 * 
 * @author: wangkaiping
 * @date: 2015年5月4日 上午10:22:37
 */
public class SelectTag extends TagSupport {
	
	private static final long serialVersionUID = 5598491130158158479L;
	private static final String TYPE_SELECT = "SELECT";
	private static final String TYPE_CHECKBOX = "CHECKBOX";
	private static final String TYPE_REDIO = "REDIO";
	private static final String TYPE_TABLE = "TABLE";

	/**
	 * 控件ID
	 */
	private String id;
	
	/**
	 * 控件的name属性
	 */
	private String name;

	/**
	 * 控件类型
	 */
	private String type = "SELECT";

	/**
	 * 字典编码
	 */
	private String dictCode;

	/**
	 * 已选择
	 */
	private List<String> selected;

	/**
	 * 是否多选
	 */
	private boolean multiselect = false;

	/**
	 * 控件样式
	 */
	private String style;

	/**
	 * 选择事件JS
	 */
	private String onSelectJs;

	/**
	 * 级联下级
	 */
	private String child;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public List<String> getSelected() {
		return selected;
	}

	public void setSelected(List<String> selected) {
		this.selected = selected;
	}

	public boolean isMultiselect() {
		return multiselect;
	}

	public void setMultiselect(boolean multiselect) {
		this.multiselect = multiselect;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getOnSelectJs() {
		return onSelectJs;
	}

	public void setOnSelectJs(String onSelectJs) {
		this.onSelectJs = onSelectJs;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		try {
			out.print(this.createTagXml());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	public String createTagXml() {
		StringBuffer sb = new StringBuffer();
		if (this.type.equals(SelectTag.TYPE_SELECT)) {// 下拉列表
			this.createSelect(sb);
		} else if (this.type.equals(SelectTag.TYPE_REDIO)) {// 单选按钮
			this.createRedio(sb);
		} else if (this.type.equals(SelectTag.TYPE_CHECKBOX)) {// 多选按钮
			this.createCheckBox(sb);
		} else if (this.type.equals(SelectTag.TYPE_TABLE)) {// 表格选择

		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 下拉列表
	 * 
	 * @param sb
	 */
	private void createSelect(StringBuffer sb) {
		String path = pageContext.getServletContext().getContextPath() + "/";
		if ("//".equals(path)) {
			path = "/";
		}
		String nameStr ="";
		if (null != this.name && this.name.trim().length() > 0) {
			nameStr = "name=\"" + this.name + "\"";
		}
		
		sb.append("<input id=\"" + this.id + "\" "+nameStr+" class=\"easyui-combobox\" data-options=\"\n");
		sb.append("	valueField: 'code',\n");
		sb.append("	textField: 'name',\n");
		if (this.multiselect) {
			sb.append("	multiple:true,\n");
		}
		if (null != this.dictCode && this.dictCode.trim().length() > 0) {
			sb.append("	url:'" + path + "dict/dictJson.do?code=" + this.dictCode + "',\n");
		}

		if (null != this.onSelectJs && this.onSelectJs.trim().length() > 0) {
			sb.append("	onSelect: " + this.onSelectJs + "\n");
		}
		if (null != this.child && this.child.trim().length() > 0) {
			sb.append("	onSelect: function(rec){\n");
			sb.append("		var url = '" + path + "dict/dictJson.do?code=' + rec.code;\n");
			sb.append("		$('#" + this.child + "').combobox('reload', url);\n");
			sb.append("}\n");
		}
		sb.append("\"/>");
	}
	
	/**
	 * 单选按钮
	 * @param sb
	 */
	private void createRedio(StringBuffer sb) {
		String path = pageContext.getServletContext().getContextPath() + "/";
		if ("//".equals(path)) {
			path = "/";
		}
		sb.append("<script type=\"text/javascript\">\r\n");
		sb.append("$.getJSON(\""+path+"dict/dictJson.do?code=" + this.dictCode + "\",function(o){\r\n");
		sb.append("		var html = '';\r\n");
		sb.append("		$.each(o,function(i,k){\r\n");
		String nameStr ="";
		if (null != this.name && this.name.trim().length() > 0) {
			nameStr = "name=\"" + this.name + "\"";
		}
		sb.append("			html += k.name + ':<input "+nameStr+" type=\"radio\" value=\"'+k.code+'\"/>';\r\n");
		sb.append("		});\r\n");
		sb.append("		$('#"+this.id+"').html(html);\r\n");
		sb.append("});\r\n");
		sb.append("</script>\r\n");
		sb.append("<div id=\"" + this.id + "\"></div>\r\n");
	}
	
	/**
	 * 复选框
	 * @param sb
	 */
	private void createCheckBox(StringBuffer sb) {
		String path = pageContext.getServletContext().getContextPath() + "/";
		if ("//".equals(path)) {
			path = "/";
		}
		sb.append("<script type=\"text/javascript\">\r\n");
		sb.append("$.getJSON(\""+path+"dict/dictJson.do?code=" + this.dictCode + "\",function(o){\r\n");
		sb.append("		var html = '';\r\n");
		sb.append("		$.each(o,function(i,k){\r\n");
		String nameStr ="";
		if (null != this.name && this.name.trim().length() > 0) {
			nameStr = "name=\"" + this.name + "\"";
		}
		sb.append("			html += k.name + ':<input "+nameStr+" type=\"checkbox\" value=\"'+k.code+'\"/>';\r\n");
		sb.append("		});\r\n");
		sb.append("		$('#" + this.id + "').html(html);\r\n");
		sb.append("});\r\n");
		sb.append("</script>\r\n");
		sb.append("<div id=\"" + this.id + "\"></div>\r\n");
	}

}
