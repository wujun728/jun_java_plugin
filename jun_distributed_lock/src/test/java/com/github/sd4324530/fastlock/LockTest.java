package com.github.sd4324530.fastlock;

import com.github.sd4324530.fastlock.redis.JedisFastLockManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author peiyu
 */
public class LockTest {

    private static final Logger log = LoggerFactory.getLogger(LockTest.class);

    public static void main(String[] args) {
        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
        hostAndPortSet.add(new HostAndPort("172.172.178.59", 7000));
        hostAndPortSet.add(new HostAndPort("172.172.178.59", 8000));
        hostAndPortSet.add(new HostAndPort("172.172.178.59", 9000));
        hostAndPortSet.add(new HostAndPort("172.172.178.59", 9001));
        hostAndPortSet.add(new HostAndPort("172.172.178.59", 9002));
        hostAndPortSet.add(new HostAndPort("172.172.178.59", 9003));
        hostAndPortSet.add(new HostAndPort("172.172.178.59", 9004));
        hostAndPortSet.add(new HostAndPort("172.172.178.59", 9005));
        hostAndPortSet.add(new HostAndPort("172.172.178.60", 7000));
        hostAndPortSet.add(new HostAndPort("172.172.178.60", 8000));
        hostAndPortSet.add(new HostAndPort("172.172.178.60", 9000));
        hostAndPortSet.add(new HostAndPort("172.172.178.60", 9001));
        hostAndPortSet.add(new HostAndPort("172.172.178.60", 9002));
        final FastLockManager manager = new JedisFastLockManager(hostAndPortSet);

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                Lock fastLock = manager.getLock("testKey123123123");
                @Override
                public void run() {
                    while (true) {
                        fastLock.lock();
                        try {
                            log.debug("获取锁...");
                            TimeUnit.SECONDS.sleep(1);
//                            if (fastLock.tryLock()) {
//                                log.debug("获取锁...");
//                                TimeUnit.SECONDS.sleep(1);
//                            }
                        } catch (Exception e) {
                            log.error("异常", e);
                        } finally {
                            fastLock.unlock();
                        }
                    }
                }
            }).start();
        }
    }
}
