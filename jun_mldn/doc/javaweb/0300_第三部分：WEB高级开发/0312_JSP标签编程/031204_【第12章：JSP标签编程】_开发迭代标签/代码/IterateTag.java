package org.lxh.tagdemo ;
import java.util.* ;
import javax.servlet.jsp.* ;
import javax.servlet.jsp.tagext.* ;

public class IterateTag extends TagSupport {
	private String name ;
	private String scope ;
	private String id ;	// 这个id用于保存集合中的每一个元素
	private Iterator<?> iter = null ;
	public int doStartTag()
               throws JspException{
		Object value = null ;
		if("page".equals(this.scope)){
			value = super.pageContext.getAttribute(this.name,PageContext.PAGE_SCOPE) ;
		}
		if("request".equals(this.scope)){
			value = super.pageContext.getAttribute(this.name,PageContext.REQUEST_SCOPE) ;
		}
		if("session".equals(this.scope)){
			value = super.pageContext.getAttribute(this.name,PageContext.SESSION_SCOPE) ;
		}
		if("application".equals(this.scope)){
			value = super.pageContext.getAttribute(this.name,PageContext.APPLICATION_SCOPE) ;
		}
		if(value!=null && value instanceof List<?>){
			this.iter = ((List<?>)value).iterator() ;
			if(iter.hasNext()){
				// 将属性保存在page属性范围之中
				super.pageContext.setAttribute(this.id,iter.next()) ;
				return TagSupport.EVAL_BODY_INCLUDE ;
			} else {
				return TagSupport.SKIP_BODY ;
			}
		} else {
			return TagSupport.SKIP_BODY ;
		}
	}

	public int doAfterBody()
                throws JspException{
		if(iter.hasNext()){
			// 将属性保存在page属性范围之中
			super.pageContext.setAttribute(this.id,iter.next()) ;
			return TagSupport.EVAL_BODY_AGAIN ;	// 反复执行doAfterBody()方法
		} else {
			return TagSupport.SKIP_BODY ;
		}
	}

	public void setName(String name){
		this.name = name ;
	}
	public void setScope(String scope){
		this.scope = scope ;
	}
	public void setId(String id){
		this.id = id ;
	}
	public String getName(){
		return this.name ;
	}
	public String getScope(){
		return this.scope ;
	}
	public String getId(){
		return this.id ;
	}
}