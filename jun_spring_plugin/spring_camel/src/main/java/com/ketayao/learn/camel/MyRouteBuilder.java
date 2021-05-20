/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.camel.MyRouteBuilder.java
 * Class:			MyRouteBuilder
 * Date:			2012-11-26
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.ketayao.learn.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;

/**
 * 
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a> Version 1.1.0
 * @since 2012-11-26 下午5:22:51
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
