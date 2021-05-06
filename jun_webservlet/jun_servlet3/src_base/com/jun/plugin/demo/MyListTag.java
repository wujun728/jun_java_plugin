package com.jun.plugin.demo;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MyListTag extends SimpleTagSupport {

	/*
	<my:myList items="${listObj}" var="yyyyy">
		${yyyyy}  ## <br/>
	</my:myList>
	 */
	
	private List items;
	private String var;
	public void setItems(List items) {
		this.items = items;
	}

	public void setVar(String var) {
		this.var = var;
	}

	@Override
	public void doTag() throws JspException, IOException {
		//完成功能：将获得的list信息遍历输出到浏览器
		Iterator it = items.iterator();
		while(it.hasNext()){
			Object str = it.next();
			//将结果放置到作用域
			this.getJspContext().setAttribute(var, str);
			//将el表达式的结果数据到浏览器
			this.getJspBody().invoke(null);
		}
	}
	
	

}
