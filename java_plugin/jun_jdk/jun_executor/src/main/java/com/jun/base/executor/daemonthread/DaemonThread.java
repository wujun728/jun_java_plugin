 
package com.jun.base.executor.daemonthread;

/**
 * 类名称:DaemonThread.java   精灵线程（Daemon）或守护线程
 * 类描述:Daemon英文意思是希腊神话中半人半神的精灵，守护神。在java中，"精灵守护Daemon线程"就是运行在程序后台的线程，
 * 一般被用于在后台为其它线程提供服务。既然它在后台运行，当前台线程运行完，主体程序就结束了，
 * 理所当然该后台线程也应该随之结束了。相对来讲，前面几节我们讲的线程是"用户线程"，这两种线程技术上来讲有什么分别呢？
 * java官方文档中大致这样描述：
 * The Java Virtual Machine continues to execute threads until All threads that are not daemon threads have died。 
 * 这句话的含义就是：用户线程不完，jvm系统就不完，要是想只运行"精灵Daemon线程",
 * 对不起jvm不给面子，不伺候，就关闭了，不给"精灵Daemon线程"们单独运行的机会。这句话比较难理解，我换一句话来说这件事。当一个应用程序的所有非精灵线程停止运行时，即使仍有精灵线程还在运行，该应用程序也将终止，反过来，只要还有非精灵线程在运行，应用程序就不会停止。我们可以通过setDaemon(boolean on)来设置某线程为精灵线程。用isDaemon（）来判断某线程是否为精灵线程或守护线程。注意：要想设置一个线程为精灵守护线程，setDaemon必须在start前调用。
 * 作    者:why
 * 时    间:2017年6月17日
 */
class Thread1 extends Thread {
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
public class DaemonThread {
	public static void main(String[] args) {
		Thread1 t = new Thread1();
		t.setDaemon(true);//是精灵守护线程，此设置必须在start之前设置
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
