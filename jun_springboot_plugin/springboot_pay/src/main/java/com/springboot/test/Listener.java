package com.springboot.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class Listener implements ApplicationListener<ContextRefreshedEvent> {
	private static Logger log = LoggerFactory.getLogger(Listener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw e;
		}
	}

}
