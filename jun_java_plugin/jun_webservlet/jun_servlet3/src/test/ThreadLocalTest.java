package test;

public class ThreadLocalTest {
	public static void main(String[] args) {
		Runnable accumelatora = new Accumulatort();
		Thread threada = new Thread(accumelatora, "ThreadA");
		Thread threadb = new Thread(accumelatora, "ThreadB");
		Thread threadc = new Thread(accumelatora, "Threadc");
		Thread threadd = new Thread(accumelatora, "Threadd");
		Thread threada2 = new Thread(accumelatora, "ThreadA2");
		Thread threadb2 = new Thread(accumelatora, "ThreadB2");
		Thread threadc2 = new Thread(accumelatora, "Threadc2");
		Thread threadd2 = new Thread(accumelatora, "Threadd2");
//		Thread threadd3 = new Thread(accumelatora, "Threadd3");
//		Thread threadd4 = new Thread(accumelatora, "Threadd3");
		threada.start();
		threadb.start();
		threadc.start();
		threadd.start();
		threada2.start();
		threadb2.start();
		threadc2.start();
		threadd2.start();
//		threadd3.start();
//		threadd4.start();
	}
}

class Accumulatort implements Runnable {
	// 实例变量
	private Long count = 0L;
	 public synchronized void addCount() {
	        count++;
//	        MD5Tools.getDecode2(count);
	        if(count%1000000==0) {
	        	System.out.println(Thread.currentThread().getName() + "-->" + count);
	        }
	    }
	public void run() {
		for (int i = 21000000; i <=  Integer.MAX_VALUE; i++) {
			addCount();
		}
	}
}