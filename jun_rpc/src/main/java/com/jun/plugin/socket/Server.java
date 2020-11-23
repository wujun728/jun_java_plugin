/**
 * 
 */
package com.jun.plugin.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Wujun
 * @date  2013-4-12
 * 多线程通讯服务端
 */
public class Server {
	private static int PORT = 1255;
	private ServerSocket server = null;
	private Vector<Socket> vector = new Vector<Socket>();
	private ConcurrentHashMap<String, Socket> map = new ConcurrentHashMap<String, Socket>();
	private int POOL_SIZE = 10;
	private ExecutorService threadPools = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
	public Server() {
		try {
			 server = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start(){
		System.out.println("Server Started===============");
		while(true){
			try {
				Socket socket = server.accept();
				vector.add(socket);
				threadPools.execute(new ServerHandler(socket, vector,map));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new Server().start();
	}
	
}
