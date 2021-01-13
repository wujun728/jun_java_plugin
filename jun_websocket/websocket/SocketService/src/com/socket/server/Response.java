package com.socket.server;


/**
 * 服务器响应
 * @author luoweiyi
 *
 */
public class Response {
	
	private boolean answer = false;
	
	private char status;
	
	private String msg;
	
	private Object value;
	
	private Long startTime;
	
	public Response(){
		System.out.println("开始计时");
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * @return
	 * @throws InterruptedException
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 获取调用接口状态
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized char getStatus() throws InterruptedException {
		while(!answer){
			wait();
		}
		System.out.println("############status="+status);
		return status;
	}

	public synchronized void setStatusAndValue(char status,Object obj,String msg) {
		this.status = status;
		this.value = obj;
		this.answer = true;
		System.out.println(Thread.currentThread().getName()+" call method cost time :"+(System.currentTimeMillis() - startTime));
		notifyAll();
	}
	
	public synchronized void setStatusAndValue(char status,Object obj) {
		setStatusAndValue(status,obj,null);
	}

	public String getMsg() {
		return msg;
	}
}
