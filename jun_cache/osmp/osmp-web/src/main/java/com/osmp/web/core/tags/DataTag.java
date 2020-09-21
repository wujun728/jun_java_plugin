/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.tags;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONArray;

/**
 * 左侧菜单自定义标签
 * 
 * @author wangkaiping
 * @version V1.0, 2013-4-7 下午04:07:03
 */
public class DataTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	protected List<Map<String, Object>> list;

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
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
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("		$(function() {\n");
		sb.append("			$('#property').datagrid( {\n");
		sb.append("				idField : 'id',\n");
		sb.append("				title : '测试用的',\n");
		// sb.append("				fit : true,\n");
		sb.append("				striped : true,\n");
		sb.append("				fitColumns : true,\n");
		sb.append("				columns : [ [ \n");
		Map<String, Object> map = this.getList().get(0);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sb.append("				{field : '" + entry.getKey() + "',title : '" + entry.getKey() + "',align : 'center'}, \n");
		}
		sb.append("				{field:'',title:'',width:1}\n");
		sb.append("				] ]	,\n");
		JSONArray ja = JSONArray.fromObject(this.list);
		sb.append("			}).datagrid('loadData'," + ja.toString() + ");\n");
		sb.append("		});\n");
		sb.append("</script>\n");
		sb.append("				<table width=\"100%\" id=\"property\"></table>\n");
		return sb.toString();
	}

}
