package com.flying.cattle.wfs.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import com.flying.cattle.wfs.annotations.WebSocketMappingHandlerMapping;
import com.flying.cattle.wfs.entity.WebSocketSender;

@Configuration
public class WebSocketConfiguration {

	@Bean
	public HandlerMapping webSocketMapping() {
		return new WebSocketMappingHandlerMapping();
	}

	@Bean
	public ConcurrentHashMap<String, WebSocketSender> senderMap() {
		return new ConcurrentHashMap<String, WebSocketSender>();
	}

	@Bean
	public WebSocketHandlerAdapter handlerAdapter() {
		return new WebSocketHandlerAdapter();
	}
}
