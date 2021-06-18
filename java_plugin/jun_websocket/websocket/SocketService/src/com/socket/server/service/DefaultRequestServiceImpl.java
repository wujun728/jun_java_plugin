package com.socket.server.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.socket.client.Request;
import com.socket.server.Response;
import com.socket.server.config.ServerConfig;
import com.socket.server.request.ServerRequest;
import com.socket.util.Constants;

/**
 * working service
 * @author luoweiyi
 *
 */
public class DefaultRequestServiceImpl extends AbstractServerRequestService{
	

	@Override
	public void doWork(ServerRequest serverRequest,ServerConfig serverConfig) {
		doWork(serverRequest.getSocket(),serverRequest.getResponse(),serverConfig);
	}
	
	public void doWork(Socket socket,Response response,ServerConfig serverConfig){
		System.out.println(Thread.currentThread().getName()+" Get client request:");
		
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			Request request = getClientRequest(ois);
			
			System.out.println(Thread.currentThread().getName()+" "+request);
			doService(request,response,serverConfig);
			oos = new ObjectOutputStream(socket.getOutputStream());
			sendResponse(response,oos);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(ois);
			close(oos);
			close(socket);
			System.out.println(Thread.currentThread().getName()+" close resource!!!");
		}
	}
	
	private void doService(Request request,Response response,ServerConfig serverConfig) throws ClassNotFoundException, IOException{
		
		//get the service params from clientRequest
		String serviceApi = request.getService();
		String serviceImpl = serverConfig.getServiceMap().get(serviceApi);
		if(null == serviceImpl){
			response.setStatusAndValue(Constants.FAILURE,null,"Can not find service implements!");
			return;
		}
		
		String methodStr = request.getMethodName();
		Object[] args = request.getParams();
		
		//start a thread to work service
		ServiceThread service = new ServiceThread(serviceImpl,methodStr,args,response);
		service.start();
	}
}
