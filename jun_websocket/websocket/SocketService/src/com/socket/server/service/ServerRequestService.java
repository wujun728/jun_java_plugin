package com.socket.server.service;

import java.util.concurrent.Future;

import com.socket.server.Response;
import com.socket.server.config.ServerConfig;
import com.socket.server.request.ServerRequest;

public interface ServerRequestService{
	
	public void doWork(ServerRequest serverRequest,ServerConfig serverConfig);
	
	public void doTimeOut(Future<?> future,Response response,Long timeout);

}
