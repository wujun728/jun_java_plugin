package book.thread.pool;

/**
 * 测试线程池
*/
public class PoolTest {

    public static void main(String[] args) {

    	//线程池中的线程数
    	int numThreads = 3;
        // 生成线程池
        MyThreadPool threadPool = new MyThreadPool(numThreads);

        // 任务数
        int numTasks = 10;
        // 运行任务
        for (int i=0; i<numTasks; i++) {
            threadPool.performTask(new MyTask(i));
        }

        // 关闭线程池并等待所有任务完成
        threadPool.join();
    }
}
