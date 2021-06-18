package com.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.socket.server.config.ConfigInfo;
import com.socket.server.request.ServerRequest;
import com.socket.server.request.SocketRequest;
import com.socket.server.service.ServerRequestService;
import com.socket.server.service.ServerRequestServiceFactory;

public class SocketServer extends AbstractServer{
	
	private final ServerRequestService service = ServerRequestServiceFactory.getService();
	
	public SocketServer(ConfigInfo configInfo){
		this.configInfo = configInfo;
	}
	
	@Override
	protected void doWork(ServerSocket ss) {
		try {
			while(true){
				Socket socket = ss.accept();
				
				//read the socket input stream and convert a ServerRequest
				ServerRequest request = new SocketRequest(socket,service,new Response(),this);
				doWork(request,service);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
