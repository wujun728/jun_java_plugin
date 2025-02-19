package io.github.wujun728.db.orm.utils;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工具
 */
@Slf4j
public class ThreadUtils {


    private static Executor executor = null;

    //线程池缓冲队列
    private static BlockingQueue<Runnable> workQueue = null;

    //当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中
    private static int QUEUESIZE = 10;

    private static int COREPOOLSIZE = 1;

    private static int MAXPOOLSIZE = 1;

    static {
        log.info("初始化线程池对象");
        if (workQueue == null) {
            workQueue = new ArrayBlockingQueue<>(QUEUESIZE);
        }
        if (executor == null) {
            executor = new ThreadPoolExecutor(COREPOOLSIZE, MAXPOOLSIZE, 60, TimeUnit.SECONDS, workQueue, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    AtomicInteger atomic = new AtomicInteger();
                    return new Thread(r, "X-ORM-Thread-" + atomic.getAndIncrement());

                }
            });
        }
    }

    public static void init(int coreSize, int maxPoolSize, int queueSize) {
        if(0 != coreSize ){
            COREPOOLSIZE = coreSize;
        }
        if(0 != maxPoolSize) {
            MAXPOOLSIZE = maxPoolSize;
        }
        if(0 != queueSize) {
            QUEUESIZE = queueSize;
        }
    }

    public static void execute(Runnable runnable) {
        if (QUEUESIZE - workQueue.size() < 100) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.execute(runnable);
    }
}
