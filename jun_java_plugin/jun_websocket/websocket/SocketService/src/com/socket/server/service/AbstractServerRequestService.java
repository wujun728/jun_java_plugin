package com.socket.server.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.socket.client.Request;
import com.socket.server.Response;
import com.socket.util.Constants;

public abstract class AbstractServerRequestService implements ServerRequestService{
	
	protected Request getClientRequest(ObjectInputStream dis) throws IOException, ClassNotFoundException{
		Request request = null;
		
		//read client status
		char status = dis.readChar();
		switch(status){
		case 's': 
			request = (Request)dis.readObject();
			break;
		case 'f':
		}
		
		return request;
	}
	
	protected  synchronized void sendResponse(Response response,ObjectOutputStream dos) throws InterruptedException, IOException{
		System.out.println("Send response to client!!!");
		
		//get the service call back value
		char status = response.getStatus();
		
		Object res = response.getValue();
		
		//send the server status to the client.
		dos.writeChar(status);
		System.out.println(Thread.currentThread().getName()+" Server status = "+status);
		
		//send the service call back value to the client.
		dos.writeObject(res);
		System.out.println(Thread.currentThread().getName()+" Server response..."+res);
	}
	
	protected void close(Socket socket){
		if(null != socket)
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	protected void close(ObjectOutputStream oos){
		if(null != oos)
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	protected void close(ObjectInputStream ois){
		if(null != ois)
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void doTimeOut(final Future<?> future,final Response response,final Long timeout){
		if(timeout>0)
			try {
				future.get(timeout, TimeUnit.MILLISECONDS);
			} catch (TimeoutException | InterruptedException | ExecutionException e) {
				response.setStatusAndValue(Constants.TIMEOUT,null,"server time out!!!");
				System.out.println("server time out!!!");
			}
	}
}
