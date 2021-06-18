package com.socket.client;

public class Client {
	
	private String ip = "127.0.0.1";
	
	private int port = 60000;
	
	private Request message;
	
	private Future<?> future;
	
	public Client(String ip,int port,Request message,Future<?> future){
		this.ip = ip;
		this.port = port;
		this.message = message;
		this.future = future;
	}
	
	public void sendRequest(){
		new ClientThread(ip,port,message,future).start();
	}
}
