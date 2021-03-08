package com.sam.demo.jersey.config;

import org.glassfish.jersey.server.ResourceConfig;

import org.springframework.stereotype.Component;

import com.sam.demo.jersey.endpoint.UserEndpoint;

/**
 * 注册用@Component
 *
 */
@Component
public class JerseyConfig extends ResourceConfig {
	/**
	 * 注册Endpoint
	 * 可以注册多个Endpoint
	 */
	public JerseyConfig() {
		register(UserEndpoint.class);
		//register(XXX1.class);
		//register(XXX2.class);
	}

}
