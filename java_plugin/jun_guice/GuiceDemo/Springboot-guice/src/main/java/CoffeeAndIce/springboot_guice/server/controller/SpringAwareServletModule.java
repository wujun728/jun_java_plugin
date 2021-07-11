package CoffeeAndIce.springboot_guice.server.controller;


import org.springframework.context.ApplicationContext;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.servlet.ServletModule;

import CoffeeAndIce.springboot_guice.server.persitence.SimpleDao;

public class SpringAwareServletModule extends AbstractModule {
	private final ApplicationContext context;

	public SpringAwareServletModule(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public void configure() {
		install(new ServletModule());
		
		bind(ApplicationContext.class).toInstance(context);
	}

	@Provides SimpleDao getSimpleDao(ApplicationContext context){
		return context.getBean(SimpleDao.class);
	}
}
