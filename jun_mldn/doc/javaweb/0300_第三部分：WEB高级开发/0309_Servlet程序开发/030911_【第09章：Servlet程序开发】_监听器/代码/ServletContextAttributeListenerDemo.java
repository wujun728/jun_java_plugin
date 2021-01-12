package org.lxh.listenerdemo ;
import javax.servlet.* ;
public class ServletContextAttributeListenerDemo implements ServletContextAttributeListener {
	public void attributeAdded(ServletContextAttributeEvent scab){
		System.out.println("** 增加属性 --> 属性名称：" + scab.getName() + "，属性内容：" + scab.getValue()) ;
	}
	public void attributeRemoved(ServletContextAttributeEvent scab){
		System.out.println("** 删除属性 --> 属性名称：" + scab.getName() + "，属性内容：" + scab.getValue()) ;
	}
	public void attributeReplaced(ServletContextAttributeEvent scab){
		System.out.println("** 替换属性 --> 属性名称：" + scab.getName() + "，属性内容：" + scab.getValue()) ;
	}
}
/*
	<listener>
		<listener-class>
			org.lxh.listenerdemo.ServletContextAttributeListenerDemo
		</listener-class>
	</listener>
*/