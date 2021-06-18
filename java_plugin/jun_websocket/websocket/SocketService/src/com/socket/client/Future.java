package com.socket.client;

public class Future<T>{
	
	private T object;
	
	private boolean answer;

	public synchronized T getObject() {
		while(!answer){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return object;
	}

	public synchronized void setObject(T object) {
		setAnswer(true);
		this.object = object;
		notifyAll();
	}

	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
}
