package org.lxh.listenerdemo ;
import javax.servlet.http.* ;
public class HttpSessionAttributeListenerDemo implements HttpSessionAttributeListener {
	public void attributeAdded(HttpSessionBindingEvent se){
		System.out.println(se.getSession().getId() + "，增加属性 --> 属性名称" + se.getName() + "，属性内容：" + se.getValue()) ;
	}
	public void attributeRemoved(HttpSessionBindingEvent se){
		System.out.println(se.getSession().getId() + "，删除属性 --> 属性名称" + se.getName() + "，属性内容：" + se.getValue()) ;
	}
	public void attributeReplaced(HttpSessionBindingEvent se){
		System.out.println(se.getSession().getId() + "，替换属性 --> 属性名称" + se.getName() + "，属性内容：" + se.getValue()) ;
	}
}
/*
	<listener>
		<listener-class>
			org.lxh.listenerdemo.HttpSessionAttributeListenerDemo
		</listener-class>
	</listener>
*/