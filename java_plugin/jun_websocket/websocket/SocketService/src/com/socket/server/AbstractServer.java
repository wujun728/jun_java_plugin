package com.socket.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.socket.server.config.ConfigInfo;
import com.socket.server.config.ServerConfig;
import com.socket.server.request.ServerRequest;
import com.socket.server.service.ServerRequestService;
import com.socket.server.threadpool.ServerThreadPool;

public abstract class AbstractServer implements ServerService, ServerConfig {

	protected ConfigInfo configInfo;

	protected ExecutorService threadPool;

	@Override
	public Map<String, String> getServiceMap() {
		if (null == configInfo)
			return null;

		return configInfo.getServiceMap();
	}

	@Override
	public void start() {
		if(!checkConfig()){
			return;
		}
		System.out.println("start server!!!");
		System.out.println(configInfo);
		this.threadPool = ServerThreadPool.fixedThreadPool(configInfo
				.getMaxThreads());

		try {
			ServerSocket ss = new ServerSocket(configInfo.getPort(),
					configInfo.getMaxConnection(),
					InetAddress.getByName(configInfo.getIp()));
			doWork(ss);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		if (null == threadPool)
			return;
		threadPool.shutdown();
	}
	
	protected boolean checkConfig(){
		if(null == configInfo || configInfo.getPort() < 0 || configInfo.getMaxConnection() <= 0
				|| configInfo.getMaxThreads() < 0 || null == configInfo.getServiceMap()
				|| configInfo.getServiceMap().size() == 0)
			return false;
		else
			return true;
	}

	protected abstract void doWork(ServerSocket ss);
	
	protected void doWork(ServerRequest serverRequest,ServerRequestService service){
		System.out.println("put the request to threadPool!!!");
		Future<?> future = threadPool.submit(serverRequest);
		service.doTimeOut(future,serverRequest.getResponse(),configInfo.getTimeout());
	}
}
