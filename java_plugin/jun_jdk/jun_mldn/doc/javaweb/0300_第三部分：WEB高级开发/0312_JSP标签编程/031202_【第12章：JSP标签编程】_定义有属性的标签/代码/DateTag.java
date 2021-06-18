package org.lxh.tagdemo ;
import java.text.* ;
import java.util.* ;
import javax.servlet.jsp.* ;
import javax.servlet.jsp.tagext.* ;
public class DateTag extends TagSupport {
	private String format ;	// 当设置属性的时候可以通过setter完成
	public int doStartTag()
               throws JspException{
		SimpleDateFormat sdf = new SimpleDateFormat(this.format) ;
		// 表示进行格式化的日期显示操作
		try{
			super.pageContext.getOut().write(sdf.format(new Date())) ;
		}catch(Exception e){
			e.printStackTrace() ;	// 异常处理操作
		}
		return TagSupport.SKIP_BODY ;
	}
	public void setFormat(String format){
		this.format = format ;
	}
	public String getFormat(){
		return this.format ;
	}
}