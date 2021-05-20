package org.lxh.tagdemo ;
import javax.servlet.jsp.* ;
import javax.servlet.jsp.tagext.* ;
public class HelloTag extends TagSupport{
	public int doStartTag() throws JspException{
		JspWriter out = super.pageContext.getOut() ;
		try{
			out.println("<h1>Hello World!!!</h1>") ;
		}catch(Exception e){}
		return TagSupport.SKIP_BODY ;
	}
}