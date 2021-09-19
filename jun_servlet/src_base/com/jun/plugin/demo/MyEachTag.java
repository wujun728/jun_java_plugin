package com.jun.plugin.demo;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MyEachTag extends SimpleTagSupport{
	
	/*
	 * <my:myeach start="0" end="9" step="1" var="ss">
		${ss} ** <br/>
	</my:myeach>
	 */
	
	private Integer start;
	private Integer end;
	private Integer step;
	private String var;

	public void setStart(Integer start) {
		this.start = start;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void doTag() throws JspException, IOException {
		//完成功能：将获得的变量信息，输出到浏览器
		
		for(int i = start ; i <= end ; i +=step){
			//从父类中，获得pagecontext对象
			this.getJspContext().setAttribute(var, i);
			//将el表达式的结果发送到浏览器
			// * el表达式的位置
			// * 如何发送
			this.getJspBody().invoke(null);
			//this.getJspBody().invoke(this.getJspContext().getOut());
		}
		
	}
	
	
	

}
