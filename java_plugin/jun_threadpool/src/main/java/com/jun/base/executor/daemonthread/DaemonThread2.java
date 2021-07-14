 
package com.jun.base.executor.daemonthread;

/**
 * 类名称:DaemonThread2.java
 * 类描述:
 * 作    者:why
 * 时    间:2017年6月17日
 */
class Thread2 extends Thread {
    public void run(){ 
        for(int i = 1; i <= 15;i++){ 
            try{ 
                Thread.sleep(100);  
            } catch (InterruptedException ex){ 
                ex.printStackTrace(); 
            } 
            System.out.println("子线程 "+i); 
        } 
    } 
}
public class DaemonThread2 {
	public static void main(String[] args) {
		Thread2 t = new Thread2();
		t.setDaemon(false);//不是精灵守护线程
		t.start();
		System.out.println((t.isDaemon()==true?"子线程是精灵守护线程":"子线程不是精灵守护线程"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+"结束");
	}
}
