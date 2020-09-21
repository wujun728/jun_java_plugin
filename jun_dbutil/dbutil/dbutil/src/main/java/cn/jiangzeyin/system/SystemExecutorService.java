package cn.jiangzeyin.system;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

/**
 * 系统线程池管理
 *
 * @author jiangzeyin
 */
public class SystemExecutorService {
    private final static ThreadPoolExecutor THREAD_POOL_EXECUTOR = newCachedThreadPool();

    /**
     * 创建一个无限制线程池
     *
     * @return 线程次
     * @author jiangzeyin
     */
    private static ThreadPoolExecutor newCachedThreadPool() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        // 提交线程池失败 处理方法
        executorService.setRejectedExecutionHandler(new CallerRunsPolicy());
        // 创建线程方法
        SystemThreadFactory systemThreadFactory = new SystemThreadFactory("sql_db");
        executorService.setThreadFactory(systemThreadFactory);
        return executorService;
    }

    /**
     * 关闭所有线程池
     *
     * @author jiangzeyin
     */
    public static void shutdown() {
        SystemDbLog.getInstance().info("关闭数据库使用的线程池");
        THREAD_POOL_EXECUTOR.shutdown();
    }

    public static void execute(Runnable command) {
        THREAD_POOL_EXECUTOR.execute(command);
    }
}
