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

import com.osmp.web.user.entity.SysPrivilege;

/**
 * 左侧菜单自定义标签
 * 
 * @author wangkaiping
 * @version V1.0, 2013-4-7 下午04:07:03
 */
public class MenuTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	protected String style = "default";

	protected List<SysPrivilege> list;

	public List<SysPrivilege> getList() {
		return list;
	}

	public void setList(List<SysPrivilege> list) {
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
		if (this.style.equals("default")) {
			sb.append("<div id=\"nav\" class=\"easyui-accordion\" fit=\"true\" border=\"false\">\n");
			sb.append(this.createMenuXml(list));
			sb.append("</div>\n");
		}
		return sb.toString();
	}

	private String createMenuXml(List<SysPrivilege> list) {
		StringBuffer sb = new StringBuffer();
		String path = pageContext.getServletContext().getContextPath() + "/";
		if ("//".equals(path)) {
			path = "/";
		}
		for (SysPrivilege i : list) {
			if (i.getPid() == -1) {
				sb.append("		<div class=\"navDiv\"  title=\"" + i.getName() + "\">\n");
				sb.append("			<ul>\n");
				for (SysPrivilege j : list) {
					if (j.getPid() == i.getId()) {
						sb.append("				<li class=\"navItem\"><div class=\"navItemDiv\" onclick=\"addTab(\'"
								+ j.getName()
								+ "\',\'"
								+ path
								+ j.getUri()
								+ "\',\'\')\"  title=\""
								+ j.getName()
								+ "\" url=\""
								+ path
								+ j.getUri()
								+ "\" iconCls=\""
								+ j.getIcon()
								+ "\"><a href=\"#\" ><span class=\"icon-navItem\" >&nbsp;</span>&nbsp;<span class=\"navName\" >"
								+ j.getName() + "</span></a></div></li>\n");
					}
				}
				sb.append("			</ul>\n");
				sb.append("		</div>\n");
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

}
