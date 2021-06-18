package net.oschina.j2cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 对 J2Cache 进行多线程测试
 */
public class MultiThreadTester {

    public static void main(String[] args) throws InterruptedException {
        CacheChannel cache = J2Cache.getChannel();

        String region = "Users";
        String key = "ld";

        ExecutorService threadPool = Executors.newCachedThreadPool();

        cache.evict(region, key);
        //cache.set(region, key, "Winter Lau");

        for(int i=0;i<100;i++) {
            final int seq = i;
            threadPool.execute(() -> {
                String name = "Thread-" + seq;
                for(int j=0;j<1;j++) {
                    long ct = System.currentTimeMillis();
                    System.out.printf("%s -> %s (%dms)\n", name, cache.get(region, key), (System.currentTimeMillis()-ct));
                }
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

        System.exit(0);
    }

}
