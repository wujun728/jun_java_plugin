# java线程池实现

> 线程池是一种多线程处理形式，处理过程中将任务添加到队列，然后在创建线程后自动启动这些任务。

## 五种Java线程池功能及分析

线程池都继承了ExecutorService的接口

因为继承了ExecutorService接口，ExecutorService是Java提供的用于管理线程池的类。
该类的两个作用：控制线程数量和重用线程。
只有调用了shutdown（）的时候才是正式的终止了这个线程池。


## 四种常见的线程池详解

java通过Executors工厂类提供我们的线程池一共有4种：

`Executors.newFixedThreadPool(int n)` //启动固定线程数的线程池

`Executors.newCacheThreadPool()` //按需分配的线程池

`Executors.newScheduledThreadPool(int n)`//定时，定期执行任务的线程池
 
`Executors.newSingleThreadExecutor()`：//单线程化的线程池。

## 一、单线程化的线程池newSingleThreadExecutor
串行执行所有任务

```java
 /**
     * 单线程化的线程池
     * 串行执行所有任务
     */
    public void singleThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Future<Integer> task = executorService.submit(() -> {
                Thread.currentThread().setName("Thread i = " + index);
                System.out.println("执行单线程化的线程池:" + Thread.currentThread().getName());
                return 50;
            });
            System.out.println("第" + i + "次计算,结果:" + task.get());
        }

        //销毁线程池
        executorService.shutdown();
    }
```

## 二、固定线程数的线程池newFixedThreadPool
fixedThreadPool（int size） 就只有一个参数，size，就是线程池中最大可创建多少个线程。
如下：创建2个线程的fixedThreadPool ，当2个都为活跃的时候，后面的任务会被加入无边界的链式队列，有空闲，就执行任务。

```java
/**
     * 启动固定线程数的线程池
     *
     * @param size 线程池中最大可创建多少个线程
     * @throws ExecutionException   执行异常
     * @throws InterruptedException 中断异常
     */
    public void fixedThreadPool(int size) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        for (int i = 0; i < 10; i++) {
            Future<Integer> task = executorService.submit(() -> {
                System.out.println("执行线程" + Thread.currentThread().getName());
                return 40;
            });
            System.out.println("第" + i + "次计算,结果:" + task.get());
        }

        //销毁线程池
        executorService.shutdown();
    }
```

## 三、按需分配的线程池newCacheThreadPool
CachedThreadPool比fixedThreadPool更快，只要有任务并且其他线程都在活跃态，就会开启一个新的线程。最大线程数：Integer.MAX_VALUE = 0x7fffffff

```java
/**
     * 按需分配的线程池
     * CachedThreadPool比fixedThreadPool更快
     * 只要有任务并且其他线程都在活跃态，就会开启一个新的线程
     * 最大线程数：Integer.MAX_VALUE = 0x7fffffff
     */
    public void cacheThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            Future<Integer> task = executorService.submit(() -> {
                System.out.println("执行线程" + Thread.currentThread().getName());
                return 50;
            });
            System.out.println("第" + i + "次计算,结果:" + task.get());
        }

        //销毁线程池
        executorService.shutdown();
    }
```

## 四、定时执行的线程池newScheduledThreadPool
newScheduledThreadPool有schedule和scheduleAtFixedRate方法用来做定时任务，scheduleAtFixedRate多了一个周期的参数**period**。

```java
schedule(Runnable command,long delay, TimeUnit unit)
```

```java
scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit)
```

代码如下：

```java
/**
     * 定时定期的线程池
     */
    public void scheduledThreadPool() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);

        //延迟3s后运行
        for (int i = 0; i < 10; i++) {
            scheduledThreadPool.schedule(() ->
                    System.out.println("执行线程" + Thread.currentThread().getName()), 10, TimeUnit.SECONDS);
        }

//        scheduledThreadPool.scheduleAtFixedRate(() ->
//                System.out.println("scheduleAtFixedRate：执行线程" + Thread.currentThread().getName()), 1,1, TimeUnit.SECONDS);

        //销毁线程池
        scheduledThreadPool.shutdown();
    }
```

## 自定义线程池newFixedThreadPool
ThreadPoolExecutor()//指定线程数的线程池。


```java
ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) 
```


 自定义线程池，可以用ThreadPoolExecutor类创建，它有多个构造方法来创建线程池。

`corePoolSize` 核心线程池大小
`maximumPoolSize` 最大线程池大小----30
`keepAliveTime` 线程池中超过corePoolSize数目的空闲线程最大存活时间
`TimeUnit` keepAliveTime时间单位----TimeUnit.MINUTES
`workQueue` 阻塞队列
 `threadFactory` 新建线程工厂
 `rejectedExecutionHandler` 当提交任务数超过maxmumPoolSize+workQueue之和时, 任务会交给RejectedExecutionHandler来处理。

**提交任务**

可以向ThreadPoolExecutor提交两种任务：Callable和Runnable。

 1. Callable

 
	该类任务有返回结果，可以抛出异常。 
	通过submit函数提交，返回Future对象。 
	可通过get获取执行结果。

 1. Runnable

    该类任务只执行，无法获取返回结果，并在执行过程中无法抛异常。 
    通过execute提交。

**关闭线程池**

 关闭线程池有两种方式：shutdown和shutdownNow，关闭时，会遍历所有的线程，调用它们的interrupt函数中断线程。但这两种方式对于正在执行的线程处理方式不同。

 1. shutdown()

    仅停止阻塞队列中等待的线程，那些正在执行的线程就会让他们执行结束。

 2. shutdownNow()

    不仅会停止阻塞队列中的线程，而且会停止正在执行的线程。

最大线程数一般设为2N+1最好，N是CPU核数 

```java
public static void main(String[] args) {
        CustomThreadPoolExecutor exec = new CustomThreadPoolExecutor();

        //1. 初始化
        exec.init();

        ExecutorService executorService = exec.getCustomThreadPoolExecutor();

        for (int i = 0; i < 100; i++) {
            System.out.println("提交第" + i + "个任务");
            executorService.execute(() -> {
                try {
                    System.out.println("执行自定义的线程池:" + Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            //销毁
            Thread.sleep(1000);
            exec.destory();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
```

## 源码

码云路径：[https://gitee.com/lhblearn/ThreadPool](https://gitee.com/lhblearn/ThreadPool)
