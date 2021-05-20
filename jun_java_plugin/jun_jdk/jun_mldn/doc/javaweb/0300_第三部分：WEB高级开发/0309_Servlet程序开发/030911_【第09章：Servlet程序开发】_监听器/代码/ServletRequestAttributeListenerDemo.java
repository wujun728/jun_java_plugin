package org.lxh.listenerdemo ;
import javax.servlet.* ;
public class ServletRequestAttributeListenerDemo implements ServletRequestAttributeListener {
	public void attributeAdded(ServletRequestAttributeEvent srae){
		System.out.println("** 增加request属性 --> 属性名称：" + srae.getName() + "，属性内容：" + srae.getValue()) ;
	}
	public void attributeRemoved(ServletRequestAttributeEvent srae){
		System.out.println("** 删除request属性 --> 属性名称：" + srae.getName() + "，属性内容：" + srae.getValue()) ;
	}
	public void attributeReplaced(ServletRequestAttributeEvent srae){
		System.out.println("** 替换request属性 --> 属性名称：" + srae.getName() + "，属性内容：" + srae.getValue()) ;
	}
}
/*
	<listener>
		<listener-class>
			org.lxh.listenerdemo.ServletRequestAttributeListenerDemo
		</listener-class>
	</listener>
*/