/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.camel.MyRouteBuilder.java
 * Class:			MyRouteBuilder
 * Date:			2012-11-26
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.jun.plugin.learn.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;

/**
 * 
 */

public class MyRouteBuilder extends RouteBuilder {

	/**
	 * @throws Exception
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
		from("activemq:test.MyQueue").to("file://test");
	}

	public static void main(String... args) throws Exception {
		Main main = new Main();

		main.enableHangupSupport();
		main.addRouteBuilder(new MyRouteBuilder());
		main.run(args);
	}
}
