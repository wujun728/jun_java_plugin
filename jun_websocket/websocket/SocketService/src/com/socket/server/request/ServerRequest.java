package com.socket.server.request;

import java.net.Socket;

import com.socket.server.Response;

/**
 * The class is to work with client request.
 * @author luoweiyi
 *
 */
public interface ServerRequest extends Runnable{
	
	public Socket getSocket();
	
	public Response getResponse();

}
