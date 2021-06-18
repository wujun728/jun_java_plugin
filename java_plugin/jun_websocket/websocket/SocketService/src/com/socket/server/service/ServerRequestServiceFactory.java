package com.socket.server.service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The factory will return opposite ServerRequestService.
 * @author luo
 *
 */
public class ServerRequestServiceFactory{
	
	private final static ServerRequestServiceFactory FACTORY = new ServerRequestServiceFactory();
	
	/**
	 * The service class name is the key,service bean is the value.
	 */
	private ConcurrentHashMap<String,ServerRequestService> map = new ConcurrentHashMap<String,ServerRequestService>();
	
	private ServerRequestServiceFactory(){
		initMap();
	}
	
	private void initMap(){
		DefaultRequestServiceImpl service = new DefaultRequestServiceImpl();
		map.put(service.getClass().getSimpleName(), service);
	}

	public static ServerRequestService getService(String name){
		return FACTORY.map.get(name);
	}
	
	public static ServerRequestService getService(){
		return FACTORY.map.get("DefaultRequestServiceImpl");
	}
	
}
