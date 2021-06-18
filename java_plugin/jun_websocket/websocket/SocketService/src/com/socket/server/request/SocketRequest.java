package com.socket.server.request;

import java.net.Socket;

import com.socket.server.Response;
import com.socket.server.config.ServerConfig;
import com.socket.server.service.ServerRequestService;

/**
 * Use for socket client request.
 * @author luoweiyi
 *
 */
public class SocketRequest implements ServerRequest {
	
	private final Socket socket;
	
	private final ServerRequestService serverRequestService;
	
	private final Response response;
	
	private ServerConfig serverConfig;
	
	public SocketRequest(Socket socket,ServerRequestService serverRequestService,Response response,ServerConfig serverConfig){
		this.socket = socket;
		this.serverRequestService = serverRequestService;
		this.response = response;
		this.serverConfig = serverConfig;
	}

	@Override
	public void run() {
		if(null == socket)
			return;

		serverRequestService.doWork(this,serverConfig);
	}

	@Override
	public Socket getSocket() {
		return this.socket;
	}

	@Override
	public Response getResponse() {
		return this.response;
	}
	
}
