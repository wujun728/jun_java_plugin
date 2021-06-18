package com.socket.server.container;

import java.util.Map;

import com.socket.server.ServerService;
import com.socket.server.exception.ConfigException;

public abstract class AbstractContainer implements Container{
	
	private Map<String,ServerService> socketServerMap;
	
	@Override
	public void start(){
		try {
			socketServerMap = initConfig();
		} catch (ConfigException e) {
			e.printStackTrace();
			socketServerMap = null;
		}
		
		if(null == socketServerMap)
			return;
		
		//start every ServerService in Container.
		for(final ServerService serverService : socketServerMap.values()){
			new Thread(){
				public void run(){
					serverService.start();
				}
			}.start();
		}
	}
	
	protected abstract Map<String,ServerService> initConfig() throws ConfigException;
	
	@Override
	public void stop() {
		for(ServerService serverService : socketServerMap.values()){
			serverService.stop();
		}
	}

}
