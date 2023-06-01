# 基于Consul的分布式锁实现

**原创**

 [2017-04-12](https://blog.didispace.com/spring-cloud-consul-lock-and-semphore/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

我们在构建分布式系统的时候，经常需要控制对共享资源的互斥访问。这个时候我们就涉及到分布式锁（也称为全局锁）的实现，基于目前的各种工具，我们已经有了大量的实现方式，比如：基于Redis的实现、基于Zookeeper的实现。本文将介绍一种基于Consul 的Key/Value存储来实现分布式锁以及信号量的方法。

## 分布式锁实现

基于Consul的分布式锁主要利用Key/Value存储API中的acquire和release操作来实现。acquire和release操作是类似Check-And-Set的操作：

- acquire操作只有当锁不存在持有者时才会返回true，并且set设置的Value值，同时执行操作的session会持有对该Key的锁，否则就返回false
- release操作则是使用指定的session来释放某个Key的锁，如果指定的session无效，那么会返回false，否则就会set设置Value值，并返回true

具体实现中主要使用了这几个Key/Value的API：

- create session：https://www.consul.io/api/session.html#session_create
- delete session：https://www.consul.io/api/session.html#delete-session
- KV acquire/release：https://www.consul.io/api/kv.html#create-update-key

#### 基本流程

[![img](https://blog.didispace.com/assets/consul-lock.png)](https://blog.didispace.com/assets/consul-lock.png)

#### 具体实现

```
public class Lock {
 
    private static final String prefix = "lock/";  // 同步锁参数前缀
 
    private ConsulClient consulClient;
    private String sessionName;
    private String sessionId = null;
    private String lockKey;
 
    /**
     *
     * @param consulClient
     * @param sessionName   同步锁的session名称
     * @param lockKey       同步锁在consul的KV存储中的Key路径，会自动增加prefix前缀，方便归类查询
     */
    public Lock(ConsulClient consulClient, String sessionName, String lockKey) {
        this.consulClient = consulClient;
        this.sessionName = sessionName;
        this.lockKey = prefix + lockKey;
    }
 
    /**
     * 获取同步锁
     *
     * @param block     是否阻塞，直到获取到锁为止
     * @return
     */
    public Boolean lock(boolean block) {
        if (sessionId != null) {
            throw new RuntimeException(sessionId + " - Already locked!");
        }
        sessionId = createSession(sessionName);
        while(true) {
            PutParams putParams = new PutParams();
            putParams.setAcquireSession(sessionId);
            if(consulClient.setKVValue(lockKey, "lock:" + LocalDateTime.now(), putParams).getValue()) {
                return true;
            } else if(block) {
                continue;
            } else {
                return false;
            }
        }
    }
 
    /**
     * 释放同步锁
     *
     * @return
     */
    public Boolean unlock() {
        PutParams putParams = new PutParams();
        putParams.setReleaseSession(sessionId);
        boolean result = consulClient.setKVValue(lockKey, "unlock:" + LocalDateTime.now(), putParams).getValue();
        consulClient.sessionDestroy(sessionId, null);
        return result;
    }
 
    /**
     * 创建session
     * @param sessionName
     * @return
     */
    private String createSession(String sessionName) {
        NewSession newSession = new NewSession();
        newSession.setName(sessionName);
        return consulClient.sessionCreate(newSession, null).getValue();
    }
 
}
```

#### 单元测试

下面单元测试的逻辑：通过线程的方式来模拟不同的分布式服务来竞争锁。多个处理线程同时以阻塞方式来申请分布式锁，当处理线程获得锁之后，Sleep一段随机事件，以模拟处理业务逻辑，处理完毕之后释放锁。

```
public class TestLock {
 
    private Logger logger = Logger.getLogger(getClass());
 
    @Test
    public void testLock() throws Exception  {
        new Thread(new LockRunner(1)).start();
        new Thread(new LockRunner(2)).start();
        new Thread(new LockRunner(3)).start();
        new Thread(new LockRunner(4)).start();
        new Thread(new LockRunner(5)).start();
        Thread.sleep(200000L);
    }
  
    class LockRunner implements Runnable {
 
        private Logger logger = Logger.getLogger(getClass());
        private int flag;
 
        public LockRunner(int flag) {
            this.flag = flag;
        }
 
        @Override
        public void run() {
            Lock lock = new Lock(new ConsulClient(), "lock-session", "lock-key");
            try {
                if (lock.lock(true)) {
                    logger.info("Thread " + flag + " start!");
                    Thread.sleep(new Random().nextInt(3000L));
                    logger.info("Thread " + flag + " end!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
  
}
```

单元测试执行结果如下：

```
2017-04-12 21:28:09,698 INFO  [Thread-0] LockRunner - Thread 1 start!
2017-04-12 21:28:12,717 INFO  [Thread-0] LockRunner - Thread 1 end!
2017-04-12 21:28:13,219 INFO  [Thread-2] LockRunner - Thread 3 start!
2017-04-12 21:28:15,672 INFO  [Thread-2] LockRunner - Thread 3 end!
2017-04-12 21:28:15,735 INFO  [Thread-1] LockRunner - Thread 2 start!
2017-04-12 21:28:17,788 INFO  [Thread-1] LockRunner - Thread 2 end!
2017-04-12 21:28:18,249 INFO  [Thread-4] LockRunner - Thread 5 start!
2017-04-12 21:28:19,573 INFO  [Thread-4] LockRunner - Thread 5 end!
2017-04-12 21:28:19,757 INFO  [Thread-3] LockRunner - Thread 4 start!
2017-04-12 21:28:21,353 INFO  [Thread-3] LockRunner - Thread 4 end!
```

从测试结果我们可以看到，通过分布式锁的形式来控制并发时，多个同步操作只会有一个操作能够被执行，其他操作只有在等锁释放之后才有机会去执行，所以通过这样的分布式锁，我们可以控制共享资源同时只能被一个操作进行执行，以保障数据处理时的分布式并发问题。

#### 优化建议

本文我们实现了基于Consul的简单分布式锁，但是在实际运行时，可能会因为各种各样的意外情况导致unlock操作没有得到正确地执行，从而使得分布式锁无法释放。所以为了更完善的使用分布式锁，我们还必须实现对锁的超时清理等控制，保证即使出现了未正常解锁的情况下也能自动修复，以提升系统的健壮性。那么如何实现呢？请持续关注我的后续分解！

#### 参考文档

Key/Value的API：https://www.consul.io/api/kv.html

选举机制：https://www.consul.io/docs/guides/leader-election.html

#### 实现代码

- GitHub：https://github.com/dyc87112/consul-distributed-lock
- 开源中国：http://git.oschina.net/didispace/consul-distributed-lock