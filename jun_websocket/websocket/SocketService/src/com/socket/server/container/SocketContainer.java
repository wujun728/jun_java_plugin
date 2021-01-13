package com.socket.server.container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.socket.server.ServerService;
import com.socket.server.SocketServer;
import com.socket.server.config.ContainerConfig;
import com.socket.server.config.ConfigInfo;
import com.socket.server.config.Configuration;
import com.socket.server.exception.ConfigException;

public class SocketContainer extends AbstractContainer{
	
	private String configFile;
	
	public SocketContainer(){}
	
	public SocketContainer(String configFile){
		this.configFile = configFile;
	}

	@Override
	public Map<String, ServerService> initConfig() throws ConfigException{
		ContainerConfig containerConfig = new Configuration(configFile);
		List<ConfigInfo> configInfoList =  containerConfig.getConfigInfos();
		Map<String, ServerService> socketServerMap = new HashMap<String,ServerService>();
		for(ConfigInfo configInfo : configInfoList){
			String key = configInfo.getIp()+"_"+configInfo.getPort();
			if(socketServerMap.containsKey(key))
				throw new ConfigException("The config file have repeat ip and port");
			socketServerMap.put(key,new SocketServer(configInfo));
		}
		
		return socketServerMap;
	}
}
