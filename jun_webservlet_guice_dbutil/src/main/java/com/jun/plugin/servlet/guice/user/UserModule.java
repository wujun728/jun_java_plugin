package com.jun.plugin.servlet.guice.user;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;
import com.jun.plugin.servlet.guice.user.action.UserServlet;
import com.jun.plugin.servlet.guice.user.service.UserService;
import com.jun.plugin.servlet.guice.user.service.impl.UserServiceImpl;

public class UserModule extends AbstractModule {


	@Override
	protected void configure() {
		install(new ServletModule(){
			@Override
			protected void configureServlets() {
				System.out.println("配置访问的servlet");
				serve("/UserServlet").with(UserServlet.class);
			}

			
		});
		
		
	}

	
}
