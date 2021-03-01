package book.thread;

public class ListAllRunningThread {
	/**
	 * 列出所有线程的信息
	 */
	public static void list(){
		//获取当前线程所属线程组的父线程组
	    ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
	    //不断循环，直到找到根线程组
	    while (root.getParent() != null) {
	        root = root.getParent();
	    }
	    //访问根线程组下的线程
	    visit(root, 0);
	}
    /**
     * 递归的显示线程组中的线程
     * @param group
     * @param level
     */
    private static void visit(ThreadGroup group, int level) {
        // 获取group线程组中活动线程的估计数
        int numThreads = group.activeCount();
        Thread[] threads = new Thread[numThreads];
        // 把此线程组中的所有活动线程复制到指定数组中。
        // false表示不包括作为此线程组的子组的线程组中的线程。
        numThreads = group.enumerate(threads, false);
    
        // 遍历活动线程数组，打印它们的名字
        for (int i=0; i<numThreads; i++) {
            // Get thread
            Thread thread = threads[i];
            for (int j=0; j<level; j++){
                System.out.print("  ");
            }
            System.out.println("" + thread.getName());
        }
    
        // 获取group线程组中活动子线程组的估计数
        int numGroups = group.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[numGroups];
        // 把对此线程组中的所有活动子组的引用复制到指定数组中。
        numGroups = group.enumerate(groups, false);
    
        // 递归的访问子线程组中的线程
        for (int i=0; i<numGroups; i++) {
            visit(groups[i], level+1);
        }
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//建立一个线程组
		ThreadGroup group1 = new ThreadGroup("ThreadGroup-1");
		//建立3个线程并启动
		Thread[] threads1 = new Thread[3];
		for (int i=1; i<4; i++){
			//新的线程属于group1线程组，以ThreadA为运行对象，名字为"group1-ThreadA-i"
			threads1[i-1] = new Thread(group1, new ThreadA(i*2000), "group1-ThreadA-" + i);
			threads1[i-1].start();
		}
		//建立另一个线程组，属于group1线程组
		ThreadGroup group2 = new ThreadGroup(group1, "ThreadGroup-2");
		//建立3个线程并启动
		Thread[] threads2 = new Thread[3];
		for (int i=1; i<4; i++){
			//新的线程属于group2线程组，以ThreadA为运行对象，名字为"group2-ThreadA-i"
			threads2[i-1] = new Thread(group2, new ThreadA(i*1000), "group2-ThreadA-" + i);
			threads2[i-1].start();
		}
		//列出所有活动的线程的名字
	    System.out.println("当前虚拟机中所有正在运行的线程：");
		ListAllRunningThread.list();
	}
	
	static class ThreadA extends Thread{
		private long sleepTime = 100;
		public ThreadA(long time){
			this.sleepTime = time;
		}
		public void run(){
			try {
				Thread.sleep(this.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
