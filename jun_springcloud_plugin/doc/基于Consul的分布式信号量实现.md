# 基于Consul的分布式信号量实现

**原创**

 [2017-04-15](https://blog.didispace.com/spring-cloud-consul-lock-and-semphore-2/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

本文将继续讨论基于Consul的分布式锁实现。信号量是我们在实现并发控制时会经常使用的手段，主要用来限制同时并发线程或进程的数量，比如：Zuul默认情况下就使用信号量来限制每个路由的并发数，以实现不同路由间的资源隔离。

> 信号量(Semaphore)，有时被称为信号灯，是在多线程环境下使用的一种设施，是可以用来保证两个或多个关键代码段不被并发调用。在进入一个关键代码段之前，线程必须获取一个信号量；一旦该关键代码段完成了，那么该线程必须释放信号量。其它想进入该关键代码段的线程必须等待直到第一个线程释放信号量。为了完成这个过程，需要创建一个信号量VI，然后将Acquire Semaphore VI以及Release Semaphore VI分别放置在每个关键代码段的首末端，确认这些信号量VI引用的是初始创建的信号量。如在这个停车场系统中，车位是公共资源，每辆车好比一个线程，看门人起的就是信号量的作用。

## 实现思路

- 信号量存储：semaphore/key
- acquired操作：
  - 创建session
  - 锁定key竞争者：semaphore/key/session
  - 查询信号量：semaphore/key/.lock，可以获得如下内容（如果是第一次创建信号量，将获取不到，这个时候就直接创建）

```
{
    "limit": 3,
    "holders": [
        "90c0772a-4bd3-3a3c-8215-3b8937e36027",
        "93e5611d-5365-a374-8190-f80c4a7280ab"
    ]
}
```

- 如果持有者已达上限，返回false，如果阻塞模式，就继续尝试acquired操作
- 如果持有者未达上限，更新semaphore/key/.lock的内容，将当前线程的sessionId加入到holders中。注意：更新的时候需要设置cas，它的值是“查询信号量”步骤获得的“ModifyIndex”值，该值用于保证更新操作的基础没有被其他竞争者更新。如果更新成功，就开始执行具体逻辑。如果没有更新成功，说明有其他竞争者抢占了资源，返回false，阻塞模式下继续尝试acquired操作
- release操作：
  - 从semaphore/key/.lock的holders中移除当前sessionId
  - 删除semaphore/key/session
  - 删除当前的session

## 流程图

[![img](https://blog.didispace.com/assets/consul-sem.png)](https://blog.didispace.com/assets/consul-sem.png)

## 代码实现

```
public class Semaphore {
 
    private Logger logger = Logger.getLogger(getClass());
 
    private static final String prefix = "semaphore/";  // 信号量参数前缀
 
    private ConsulClient consulClient;
    private int limit;
    private String keyPath;
    private String sessionId = null;
    private boolean acquired = false;
 
    /**
     *
     * @param consulClient consul客户端实例
     * @param limit 信号量上限值
     * @param keyPath 信号量在consul中存储的参数路径
     */
    public Semaphore(ConsulClient consulClient, int limit, String keyPath) {
        this.consulClient = consulClient;
        this.limit = limit;
        this.keyPath = prefix + keyPath;
    }
 
    /**
     * acquired信号量
     *
     * @param block 是否阻塞。如果为true，那么一直尝试，直到获取到该资源为止。
     * @return
     * @throws IOException
     */
    public Boolean acquired(boolean block) throws IOException {
 
        if(acquired) {
            logger.error(sessionId + " - Already acquired");
            throw new RuntimeException(sessionId + " - Already acquired");
        }
 
        // create session
        clearSession();
        this.sessionId = createSessionId("semaphore");
        logger.debug("Create session : " + sessionId);
 
        // add contender entry
        String contenderKey = keyPath + "/" + sessionId;
        logger.debug("contenderKey : " + contenderKey);
        PutParams putParams = new PutParams();
        putParams.setAcquireSession(sessionId);
        Boolean b = consulClient.setKVValue(contenderKey, "", putParams).getValue();
        if(!b) {
            logger.error("Failed to add contender entry : " + contenderKey + ", " + sessionId);
            throw new RuntimeException("Failed to add contender entry : " + contenderKey + ", " + sessionId);
        }
 
        while(true) {
            // try to take the semaphore
            String lockKey = keyPath + "/.lock";
            String lockKeyValue;
 
            GetValue lockKeyContent = consulClient.getKVValue(lockKey).getValue();
 
            if (lockKeyContent != null) {
                // lock值转换
                lockKeyValue = lockKeyContent.getValue();
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] v = decoder.decodeBuffer(lockKeyValue);
                String lockKeyValueDecode = new String(v);
                logger.debug("lockKey=" + lockKey + ", lockKeyValueDecode=" + lockKeyValueDecode);
 
                Gson gson = new Gson();
                ContenderValue contenderValue = gson.fromJson(lockKeyValueDecode, ContenderValue.class);
                // 当前信号量已满
                if(contenderValue.getLimit() == contenderValue.getHolders().size()) {
                    logger.debug("Semaphore limited " + contenderValue.getLimit() + ", waiting...");
                    if(block) {
                        // 如果是阻塞模式，再尝试
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException e) {
                        }
                        continue;
                    }
                    // 非阻塞模式，直接返回没有获取到信号量
                    return false;
                }
                // 信号量增加
                contenderValue.getHolders().add(sessionId);
                putParams = new PutParams();
                putParams.setCas(lockKeyContent.getModifyIndex());
                boolean c = consulClient.setKVValue(lockKey, contenderValue.toString(), putParams).getValue();
                if(c) {
                    acquired = true;
                    return true;
                }
                else
                    continue;
            } else {
                // 当前信号量还没有，所以创建一个，并马上抢占一个资源
                ContenderValue contenderValue = new ContenderValue();
                contenderValue.setLimit(limit);
                contenderValue.getHolders().add(sessionId);
 
                putParams = new PutParams();
                putParams.setCas(0L);
                boolean c = consulClient.setKVValue(lockKey, contenderValue.toString(), putParams).getValue();
                if (c) {
                    acquired = true;
                    return true;
                }
                continue;
            }
        }
    }
 
    /**
     * 创建sessionId
     * @param sessionName
     * @return
     */
    public String createSessionId(String sessionName) {
        NewSession newSession = new NewSession();
        newSession.setName(sessionName);
        return consulClient.sessionCreate(newSession, null).getValue();
    }
 
    /**
     * 释放session、并从lock中移除当前的sessionId
     * @throws IOException
     */
    public void release() throws IOException {
        if(this.acquired) {
            // remove session from lock
            while(true) {
                String contenderKey = keyPath + "/" + sessionId;
                String lockKey = keyPath + "/.lock";
                String lockKeyValue;
 
                GetValue lockKeyContent = consulClient.getKVValue(lockKey).getValue();
                if (lockKeyContent != null) {
                    // lock值转换
                    lockKeyValue = lockKeyContent.getValue();
                    BASE64Decoder decoder = new BASE64Decoder();
                    byte[] v = decoder.decodeBuffer(lockKeyValue);
                    String lockKeyValueDecode = new String(v);
                    Gson gson = new Gson();
                    ContenderValue contenderValue = gson.fromJson(lockKeyValueDecode, ContenderValue.class);
                    contenderValue.getHolders().remove(sessionId);
                    PutParams putParams = new PutParams();
                    putParams.setCas(lockKeyContent.getModifyIndex());
                    consulClient.deleteKVValue(contenderKey);
                    boolean c = consulClient.setKVValue(lockKey, contenderValue.toString(), putParams).getValue();
                    if(c) {
                        break;
                    }
                }
            }
            // remove session key
 
        }
        this.acquired = false;
        clearSession();
    }
 
    public void clearSession() {
        if(sessionId != null) {
            consulClient.sessionDestroy(sessionId, null);
            sessionId = null;
        }
    }
 
    class ContenderValue implements Serializable {
 
        private Integer limit;
        private List<String> holders = new ArrayList<>();
 
        public Integer getLimit() {
            return limit;
        }
 
        public void setLimit(Integer limit) {
            this.limit = limit;
        }
 
        public List<String> getHolders() {
            return holders;
        }
 
        public void setHolders(List<String> holders) {
            this.holders = holders;
        }
 
        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
 
    }
 
}
```

## 单元测试

下面单元测试的逻辑：通过线程的方式来模拟不同的分布式服务来获取信号量执行业务逻辑。由于信号量与简单的分布式互斥锁有所不同，它不是只限定一个线程可以操作，而是可以控制多个线程的并发，所以通过下面的单元测试，我们设置信号量为3，然后同时启动15个线程来竞争的情况，来观察分布式信号量实现的结果如何。

```
public class TestLock {
 
    private Logger logger = Logger.getLogger(getClass());
 
    @Test
    public void testSemaphore() throws Exception {
        new Thread(new SemaphoreRunner(1)).start();
        new Thread(new SemaphoreRunner(2)).start();
        new Thread(new SemaphoreRunner(3)).start();
        new Thread(new SemaphoreRunner(4)).start();
        new Thread(new SemaphoreRunner(5)).start();
        new Thread(new SemaphoreRunner(6)).start();
        new Thread(new SemaphoreRunner(7)).start();
        new Thread(new SemaphoreRunner(8)).start();
        new Thread(new SemaphoreRunner(9)).start();
        new Thread(new SemaphoreRunner(10)).start();
        Thread.sleep(1000000L);
    } 
}
  
public class SemaphoreRunner implements Runnable {
 
    private Logger logger = Logger.getLogger(getClass());
 
    private int flag;
 
    public SemaphoreRunner(int flag) {
        this.flag = flag;
    }
 
    @Override
    public void run() {
        Semaphore semaphore = new Semaphore(new ConsulClient(), 3, "mg-init");
        try {
            if (semaphore.acquired(true)) {
                // 获取到信号量，执行业务逻辑
                logger.info("Thread " + flag + " start!");
                Thread.sleep(new Random().nextInt(10000));
                logger.info("Thread " + flag + " end!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 信号量释放、Session锁释放、Session删除
                semaphore.release();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
INFO  [Thread-6] SemaphoreRunner - Thread 7 start!
INFO  [Thread-2] SemaphoreRunner - Thread 3 start!
INFO  [Thread-7] SemaphoreRunner - Thread 8 start!
INFO  [Thread-2] SemaphoreRunner - Thread 3 end!
INFO  [Thread-5] SemaphoreRunner - Thread 6 start!
INFO  [Thread-6] SemaphoreRunner - Thread 7 end!
INFO  [Thread-9] SemaphoreRunner - Thread 10 start!
INFO  [Thread-5] SemaphoreRunner - Thread 6 end!
INFO  [Thread-1] SemaphoreRunner - Thread 2 start!
INFO  [Thread-7] SemaphoreRunner - Thread 8 end!
INFO  [Thread-10] SemaphoreRunner - Thread 11 start!
INFO  [Thread-10] SemaphoreRunner - Thread 11 end!
INFO  [Thread-12] SemaphoreRunner - Thread 13 start!
INFO  [Thread-1] SemaphoreRunner - Thread 2 end!
INFO  [Thread-3] SemaphoreRunner - Thread 4 start!
INFO  [Thread-9] SemaphoreRunner - Thread 10 end!
INFO  [Thread-0] SemaphoreRunner - Thread 1 start!
INFO  [Thread-3] SemaphoreRunner - Thread 4 end!
INFO  [Thread-14] SemaphoreRunner - Thread 15 start!
INFO  [Thread-12] SemaphoreRunner - Thread 13 end!
INFO  [Thread-0] SemaphoreRunner - Thread 1 end!
INFO  [Thread-13] SemaphoreRunner - Thread 14 start!
INFO  [Thread-11] SemaphoreRunner - Thread 12 start!
INFO  [Thread-13] SemaphoreRunner - Thread 14 end!
INFO  [Thread-4] SemaphoreRunner - Thread 5 start!
INFO  [Thread-4] SemaphoreRunner - Thread 5 end!
INFO  [Thread-8] SemaphoreRunner - Thread 9 start!
INFO  [Thread-11] SemaphoreRunner - Thread 12 end!
INFO  [Thread-14] SemaphoreRunner - Thread 15 end!
INFO  [Thread-8] SemaphoreRunner - Thread 9 end!
```

从测试结果，我们可以发现当信号量持有者数量达到信号量上限3的时候，其他竞争者就开始进行等待了，只有当某个持有者释放信号量之后，才会有新的线程变成持有者，从而开始执行自己的业务逻辑。所以，分布式信号量可以帮助我们有效的控制同时操作某个共享资源的并发数。

## 优化建议

同前文一样，这里只是做了简单的实现。线上应用还必须加入TTL的session清理以及对.lock资源中的无效holder进行清理的机制。

参考文档：https://www.consul.io/docs/guides/semaphore.html

## 实现代码

- GitHub：https://github.com/dyc87112/consul-distributed-lock
- 开源中国：http://git.oschina.net/didispace/consul-distributed-lock