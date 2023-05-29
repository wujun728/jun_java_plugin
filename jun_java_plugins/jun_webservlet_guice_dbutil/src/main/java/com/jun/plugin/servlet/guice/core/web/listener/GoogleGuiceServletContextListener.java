package com.jun.plugin.servlet.guice.core.web.listener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.jun.plugin.servlet.guice.user.UserModule;

public class GoogleGuiceServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		
		return Guice.createInjector(new UserModule());
	}

}
