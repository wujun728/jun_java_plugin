package book.j2se5.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Lockers
 * 在多线程编程里面一个重要的概念是锁定，如果一个资源是多个线程共享的，为了保证数据的完整性，
 * 在进行事务性操作时需要将共享资源锁定，这样可以保证在做事务性操作时只有一个线程能对资源进行操作，
 * 从而保证数据的完整性。在5.0以前，锁定的功能是由Synchronized关键字来实现的。
 */
public class Lockers {
	
	/**
	 * 测试Lock的使用。在方法中使用Lock，可以避免使用Synchronized关键字。
	 */
	public static class LockTest {

		Lock lock = new ReentrantLock();// 锁
		double value = 0d; // 值
		int addtimes = 0;

		/**
		 * 增加value的值，该方法的操作分为2步，而且相互依赖，必须实现在一个事务中
		 * 所以该方法必须同步，以前的做法是在方法声明中使用Synchronized关键字。
		 */
		public void addValue(double v) {
			lock.lock();// 取得锁
			System.out.println("LockTest to addValue: " + v + "   "
					+ System.currentTimeMillis());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			this.value += v;
			this.addtimes++;
			lock.unlock();// 释放锁
		}

		public double getValue() {
			return this.value;
		}
	}
	public static void testLockTest() throws Exception{
		final LockTest lockTest = new LockTest();
		// 新建任务1，调用lockTest的addValue方法
		Runnable task1 = new Runnable(){
			public void run(){
				lockTest.addValue(55.55);
			}
		};
		// 新建任务2，调用lockTest的getValue方法
		Runnable task2 = new Runnable(){
			public void run(){
				System.out.println("value: " + lockTest.getValue());
			}
		};
		// 新建任务执行服务
		ExecutorService cachedService = Executors.newCachedThreadPool();
		Future future = null;
		// 同时执行任务1三次，由于addValue方法使用了锁机制，所以，实质上会顺序执行
		for (int i=0; i<3; i++){
			future = cachedService.submit(task1);
		}
		// 等待最后一个任务1被执行完
		future.get();
		// 再执行任务2，输出结果
		future = cachedService.submit(task2);
		// 等待任务2执行完后，关闭任务执行服务
		future.get();
		cachedService.shutdownNow();
	}
	
	/**
	 * ReadWriteLock内置两个Lock，一个是读的Lock，一个是写的Lock。
	 * 多个线程可同时得到读的Lock，但只有一个线程能得到写的Lock，
	 * 而且写的Lock被锁定后，任何线程都不能得到Lock。ReadWriteLock提供的方法有：
	 * readLock(): 返回一个读的lock 
	 * writeLock(): 返回一个写的lock, 此lock是排他的。
	 * ReadWriteLockTest很适合处理类似文件的读写操作。
	 * 读的时候可以同时读，但不能写；写的时候既不能同时写也不能读。
	 */
	public static class ReadWriteLockTest{
		// 锁
		ReadWriteLock lock = new ReentrantReadWriteLock();
		// 值
		double value = 0d;
		int addtimes = 0;
		
		/**
		 * 增加value的值，不允许多个线程同时进入该方法
		 */
		public void addValue(double v) {
			// 得到writeLock并锁定
			Lock writeLock = lock.writeLock();
			writeLock.lock();
			System.out.println("ReadWriteLockTest to addValue: " + v + "   "
					+ System.currentTimeMillis());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			try {
				// 做写的工作
				this.value += v;
				this.addtimes++;
			} finally {
				// 释放writeLock锁
				writeLock.unlock();
			}
		}
		/**
		 * 获得信息。当有线程在调用addValue方法时，getInfo得到的信息可能是不正确的。
		 * 所以，也必须保证该方法在被调用时，没有方法在调用addValue方法。
		 */
		public String getInfo() {
			// 得到readLock并锁定
			Lock readLock = lock.readLock();
			readLock.lock();
			System.out.println("ReadWriteLockTest to getInfo   "
					+ System.currentTimeMillis());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			try {
				// 做读的工作
				return this.value + " : " + this.addtimes;
			} finally {
				// 释放readLock
				readLock.unlock();
			}
		}
	}
	
	public static void testReadWriteLockTest() throws Exception{
		final ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
		// 新建任务1，调用lockTest的addValue方法
		Runnable task_1 = new Runnable(){
			public void run(){
				readWriteLockTest.addValue(55.55);
			}
		};
		// 新建任务2，调用lockTest的getValue方法
		Runnable task_2 = new Runnable(){
			public void run(){
				System.out.println("info: " + readWriteLockTest.getInfo());
			}
		};
		// 新建任务执行服务
		ExecutorService cachedService_1 = Executors.newCachedThreadPool();
		Future future_1 = null;
		// 同时执行5个任务，其中前2个任务是task_1，后两个任务是task_2
		for (int i=0; i<2; i++){
			future_1 = cachedService_1.submit(task_1);
		}
		for (int i=0; i<2; i++){
			future_1 = cachedService_1.submit(task_2);
		}
		// 最后一个任务是task_1
		future_1 = cachedService_1.submit(task_1);
		// 这5个任务的执行顺序应该是：
		// 第一个task_1先执行，第二个task_1再执行；这是因为不能同时写，所以必须等。
		// 然后2个task_2同时执行；这是因为在写的时候，就不能读，所以都等待写结束，
		// 又因为可以同时读，所以它们同时执行
		// 最后一个task_1再执行。这是因为在读的时候，也不能写，所以必须等待读结束后，才能写。
		
		// 等待最后一个task_2被执行完
		future_1.get();
		cachedService_1.shutdownNow();
	}

	public static void main(String[] args) throws Exception{
		Lockers.testLockTest();
		System.out.println("---------------------");
		Lockers.testReadWriteLockTest();
	}
}
