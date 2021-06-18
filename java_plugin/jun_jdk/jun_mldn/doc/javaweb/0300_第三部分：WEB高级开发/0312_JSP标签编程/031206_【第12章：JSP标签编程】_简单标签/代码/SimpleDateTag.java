package org.lxh.tagdemo ;
import java.io.* ;
import java.util.* ;
import java.text.* ;
import javax.servlet.jsp.* ;
import javax.servlet.jsp.tagext.* ;
public class SimpleDateTag extends SimpleTagSupport {
	private String format ;	// 接收格式化
	public void doTag()
           throws JspException,
                  IOException{
		SimpleDateFormat sdf = new SimpleDateFormat(this.format) ;
		try{
			super.getJspContext().getOut().write(sdf.format(new Date())) ;
		}catch(Exception e){}
	}
	public void setFormat(String format){
		this.format = format ;
	}
	public String getFormat(){
		return this.format ;
	}
}