package com.socket.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author luoweiyi
 *
 */
public class ClientThread extends Thread{
	
	private String ip;
	
	private int port;
	
	private Request request;
	
	private	Future future;
	
	private Socket socket;
	
	private ObjectInputStream dis;
	
	private ObjectOutputStream dos;
	
	public ClientThread(String ip,int port,Request request,Future future){
		this.ip = ip;
		this.port = port;
		this.request = request;
		this.future = future;
	}
	
	public void run(){
		Object value = null;
		try{
			
			System.out.println(Thread.currentThread().getName()+"  create socket ip="+ip+"   port="+port);
			socket = new Socket(ip,port);
			
			//send request to server
			this.dos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println(Thread.currentThread().getName()+"  send request to server！");
			//send status
			dos.writeChar('s');
			//send request
			dos.writeObject(request);
			
				
			//read server info
			this.dis = new ObjectInputStream(socket.getInputStream());
			System.out.println(Thread.currentThread().getName()+"  start read server info!!!");
			
			//read server status
			char status = dis.readChar();
			System.out.println(Thread.currentThread().getName()+"  read server status!!! status:"+status);
			switch(status){
				case 's' : 
					value = dis.readObject();
					break;
				case 't' :
					value = null;//time out;
			}
			future.setObject(value);
		}catch(IOException e){
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(null == dis){
				try {
					dis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null == dos){
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null == socket){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

