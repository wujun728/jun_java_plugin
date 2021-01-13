package com.jun.plugin.demo;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ForEachTag extends SimpleTagSupport {

	/*
	<my:myList items="${listObj}" var="yyyyy">
		${yyyyy}  ## <br/>
	</my:myList>
	 */
	
	private Iterator<Object> items;
	private String var;
	public void setItems(Object items) {
		//list
		if(items instanceof List){
			this.items = ((List)items).iterator();
		}
		//map
		if(items instanceof Map){
			this.items = ((Map)items).entrySet().iterator();
		}
		//object[]
		if(items instanceof Object[]){
			this.items = Arrays.asList((Object[])items).iterator();
		}
		
	}

	public void setVar(String var) {
		this.var = var;
	}

	@Override
	public void doTag() throws JspException, IOException {
		//完成功能：将获得的list信息遍历输出到浏览器

		while(items.hasNext()){
			Object str = items.next();
			//将结果放置到作用域
			this.getJspContext().setAttribute(var, str);
			//将el表达式的结果数据到浏览器
			this.getJspBody().invoke(null);
		}
	}
	
	

}
