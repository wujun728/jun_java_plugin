package com.socket.server;

import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * A queue for put client request.
 * @author luoweiyi
 *
 */
public class RequestQueue {
	
	private static final RequestQueue INSTANCE = new RequestQueue();
	
	private Queue<Request> priorityQueue =  new PriorityQueue<Request>(100);
	
	private RequestQueue(){}
	
	public static RequestQueue getInstance(){
		return INSTANCE;
	}
	
	/**
	 * put request
	 * @param request
	 */
	public synchronized void setRequest(Socket socket,String service,String methodName,Object[] args,String returnType){
		priorityQueue.add(new Request(socket,service,methodName,args,returnType));
		System.out.println("请求进入客户端队列！！！");
		notifyAll();
	}
	
	/**
	 * get request
	 * @return
	 */
	public synchronized Request getRequest(){
		while(priorityQueue.size() <= 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return priorityQueue.poll();
	}
	
	public class Request{
	
		/**
		 * 
		 */
		private String server;
		
		/**
		 *
		 */
		private String methodName;
		
		/**
		 * 
		 */
		private Object[] params;
		
		/**
		 * 
		 */
		private String returnType;
		
		/**
		 * 
		 */
		private Socket socket;
		
		public Request(Socket socket,String server,String methodName,Object[] params,String returnType){
			this.socket = socket;
			this.server = server;
			this.methodName = methodName;
			this.params = params;
			this.returnType = returnType;
		}
		
		public Socket getSocket() {
			return socket;
		}

		public void setSocket(Socket socket) {
			this.socket = socket;
		}

		public String getServer() {
			return server;
		}

		public void setServer(String server) {
			this.server = server;
		}

		public String getMethodName() {
			return methodName;
		}

		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}

		public Object[] getParams() {
			return params;
		}

		public void setParams(Object[] params) {
			this.params = params;
		}

		public String getReturnType() {
			return returnType;
		}

		public void setReturnType(String returnType) {
			this.returnType = returnType;
		}
	}
}
