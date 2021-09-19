package com.jun.plugin.demo;


public class Deadlock {
	public static void main(String[] args) {
		// �����߳̾���������ٽ���Դ
		final Object resource1 = "resource1";
		final Object resource2 = "resource2";
		// ��һ���̣߳��Ⱦ���resource1������resource2
		Thread t1 = new Thread() {
		public void run() {
			// ��resource1
			synchronized(resource1) {
			System.out.println("Thread 1: locked resource 1");
			// �߳�����һ��ʱ��
			try { Thread.sleep(50); }
			catch (InterruptedException e) {}
			
			// ����resource2
			synchronized(resource2) {
				System.out.println("Thread 1: locked resource 2");
			}
			}
		}
		};
        
		// �ڶ����̣߳��Ⱦ���resource2������resource1
		Thread t2 = new Thread() {
		public void run() {
			// �߳���resource1
			synchronized(resource2) {
			System.out.println("Thread 2: locked resource 2");
			
			// ����Ƭ��
			try { Thread.sleep(50); }
			catch (InterruptedException e) {}
			
			// ����resource2
			synchronized(resource1) {
				System.out.println("Thread 2: locked resource 1");
			}
			}
		}
		};
        
        // ���������߳�
		t1.start(); 
		t2.start();
	}
}
