package book.j2se5.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 新的任务执行架构。
 * 在Java 5.0之前启动一个任务是通过调用Thread类的start()方法来实现的，
 * 任务的提于交和执行是同时进行的，如果你想对任务的执行进行调度，
 * 或是控制同时执行的线程数量就需要额外编写代码来完成。
 * 5.0里提供了一个新的任务执行架构使你可以轻松地调度和控制任务的执行，
 * 并且可以建立一个类似数据库连接池的线程池来执行任务。
 * 这个架构主要有三个接口和其相应的具体类组成。
 * 这三个接口是Executor, ExecutorService和ScheduledExecutorService。
 * （1）Executor接口：是用来执行Runnable任务的，它只定义一个方法：
 * execute(Runnable command)：执行Ruannable类型的任务
 * （2）ExecutorService：继承了Executor的方法，并提供了执行Callable任务和中止任务执行的服务，
 * 其定义的方法主要有：
 * submit(task)：可用来提交Callable或Runnable任务，并返回代表此任务的Future对象 
 * invokeAll(collection of tasks)：批处理任务集合，并返回一个代表这些任务的Future对象集合 
 * shutdown()：在完成已提交的任务后关闭服务，不再接受新任务 
 * shutdownNow()：停止所有正在执行的任务并关闭服务。 
 * isTerminated()：测试是否所有任务都执行完毕了。 
 * isShutdown()：测试是否该ExecutorService已被关闭
 * （3）ScheduledExecutorService：继承ExecutorService，提供了按时间安排执行任务的功能、
 * schedule(task, initDelay): 安排所提交的Callable或Runnable任务在initDelay指定的时间后执行。
 * scheduleAtFixedRate()：安排所提交的Runnable任务按指定的间隔重复执行 
 * scheduleWithFixedDelay()：安排所提交的Runnable任务在每次执行完后，等待delay所指定的时间后重复执行。
 * 
 * 通过Executors类来获得各种服务对象。
 * callable(Runnable task): 将Runnable的任务转化成Callable的任务 
 * newSingleThreadExecutor: 产生一个ExecutorService对象，这个对象只有一个线程可用来执行任务，若任务多于一个，任务将按先后顺序执行。 
 * newCachedThreadPool(): 产生一个ExecutorService对象，这个对象带有一个线程池，线程池的大小会根据需要调整，线程执行完任务后返回线程池，供执行下一次任务使用。
 * newFixedThreadPool(int poolSize)：产生一个ExecutorService对象，这个对象带有一个大小为poolSize的线程池，若任务数量大于poolSize，任务会被放在一个queue里顺序执行。 
 * newSingleThreadScheduledExecutor：产生一个ScheduledExecutorService对象，这个对象的线程池大小为1，若任务多于一个，任务将按先后顺序执行。 
 * newScheduledThreadPool(int poolSize): 产生一个ScheduledExecutorService对象，这个对象的线程池大小为poolSize，若任务数量大于poolSize，任务会在一个queue里等待执行 
 */
public class ExecuteArch {
	
	/**
	 * 该线程输出一行字符串
	 */
	public static class MyThread implements Runnable {
		public void run() {
			System.out.println("Task repeating. " + System.currentTimeMillis());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Task interrupted. "
						+ System.currentTimeMillis());
			}
		}
	}

	/**
	 * 该Callable结束另一个任务
	 */
	public static class MyCallable implements Callable {
		private Future future;

		public MyCallable(Future future) {
			this.future = future;
		}

		public String call() {
			System.out.println("To cancell Task..."
					+ +System.currentTimeMillis());
			this.future.cancel(true);
			return "Task cancelled!";
		}
	}

	/**
	 * @param args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		// 产生一个ExecutorService对象，这个对象带有一个线程池，线程池的大小会根据需要调整，
		// 线程执行完任务后返回线程池，供执行下一次任务使用。
		ExecutorService cachedService = Executors.newCachedThreadPool();
		Future myThreadFuture = cachedService.submit(new MyThread());
		Future myCallableFuture = cachedService.submit(new MyCallable(
				myThreadFuture));
		System.out.println(myCallableFuture.get());
		System.out.println("-----------------");

		// 将Runnable任务转换成Callable任务
		Callable myThreadCallable = Executors.callable(new MyThread());
		Future myThreadCallableFuture = cachedService.submit(myThreadCallable);
		// 对于Runnable任务，转换成Callable任务后，也没有返回值
		System.out.println(myThreadCallableFuture.get());
		cachedService.shutdownNow();
		System.out.println("-----------------");

		// 产生一个ExecutorService对象，这个对象带有一个大小为poolSize的线程池，
		// 若任务数量大于poolSize，任务会被放在一个queue里顺序执行
		ExecutorService fixedService = Executors.newFixedThreadPool(2);
		fixedService.submit(new MyThread());
		fixedService.submit(new MyThread());
		// 由于线程池大小为2，所以后面的任务必须等待前面的任务执行完后才能被执行。
		myThreadFuture = fixedService.submit(new MyThread());
		myCallableFuture = fixedService.submit(new MyCallable(myThreadFuture));
		System.out.println(myCallableFuture.get());
		fixedService.shutdownNow();
		System.out.println("-----------------");

		// 产生一个ScheduledExecutorService对象，这个对象的线程池大小为poolSize，
		// 若任务数量大于poolSize，任务会在一个queue里等待执行
		ScheduledExecutorService fixedScheduledService = Executors
				.newScheduledThreadPool(2);
		// 新建任务1
		MyThread task1 = new MyThread();
		// 使用任务执行服务立即执行任务1，而且此后每隔2秒执行一次任务1。
		myThreadFuture = fixedScheduledService.scheduleAtFixedRate(task1, 0, 2,
				TimeUnit.SECONDS);
		// 新建任务2
		MyCallable task2 = new MyCallable(myThreadFuture);
		// 使用任务执行服务等待5秒后执行任务2，执行它后会将任务1关闭。
		myCallableFuture = fixedScheduledService.schedule(task2, 5,
				TimeUnit.SECONDS);
		System.out.println(myCallableFuture.get());
		fixedScheduledService.shutdownNow();
	}
}
