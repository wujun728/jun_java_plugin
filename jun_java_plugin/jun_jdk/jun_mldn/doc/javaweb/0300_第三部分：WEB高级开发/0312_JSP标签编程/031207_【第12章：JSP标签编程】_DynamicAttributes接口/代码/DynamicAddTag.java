package org.lxh.tagdemo ;
import java.io.* ;
import java.util.* ;
import javax.servlet.jsp.* ;
import javax.servlet.jsp.tagext.* ;
public class DynamicAddTag extends SimpleTagSupport implements DynamicAttributes {
		private Map<String,Float> num = new HashMap<String,Float>() ;
		public void doTag()
           throws JspException,
                  IOException{
			Float sum = 0.0f ;
			Iterator<Map.Entry<String,Float>> iter = this.num.entrySet().iterator() ;
			while(iter.hasNext()){
				Map.Entry<String,Float> value = iter.next() ;
				sum += value.getValue() ;	// 取出每一个内容
			}
			super.getJspContext().getOut().write(sum + "") ;
		}
		public void setDynamicAttribute(String uri,
                         String localName,
                         Object value)
                         throws JspException{
			// 取出设置的每一个动态属性，都保存在Map集合里
			num.put(localName,Float.parseFloat(value.toString())) ;
		}
}