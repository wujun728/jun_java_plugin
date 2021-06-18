package org.lxh.tagdemo ;
import java.io.* ;
import javax.servlet.jsp.* ;
import javax.servlet.jsp.tagext.* ;

public class AttributeTag extends TagSupport {
	private String name ;	// 接收属性的名称
	private String scope ;	// 接收属性的范围
	
	public int doStartTag()
               throws JspException{	// 是判断属性是否存在
		Object value = null ;
		if("page".equals(this.scope)){	// 是否是page范围
			value = super.pageContext.getAttribute(this.name,PageContext.PAGE_SCOPE) ;
		}
		if("request".equals(this.scope)){	// 是否是request范围
			value = super.pageContext.getAttribute(this.name,PageContext.REQUEST_SCOPE) ;
		}
		if("session".equals(this.scope)){	// 是否是session范围
			value = super.pageContext.getAttribute(this.name,PageContext.SESSION_SCOPE) ;
		}
		if("application".equals(this.scope)){	// 是否是request范围
			value = super.pageContext.getAttribute(this.name,PageContext.APPLICATION_SCOPE) ;
		}
		if(value == null){	// 表示现在根本就没有此属性
			return TagSupport.SKIP_BODY ;	// 没有属性不执行标签体
		} else {
			return TagSupport.EVAL_BODY_INCLUDE ;	// 执行标签体
		}
	}

	public void setName(String name){
		this.name = name ;
	}
	public void setScope(String scope){
		this.scope = scope ;
	}
	public String getName(){
		return this.name ;
	}
	public String getScope(){
		return this.scope ;
	}
}