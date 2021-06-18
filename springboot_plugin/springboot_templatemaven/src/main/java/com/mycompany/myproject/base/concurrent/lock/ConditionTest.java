package com.mycompany.myproject.base.concurrent.lock;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {
    // 生产 和 消费 的最大总数
    public static int totalCount = 10;
    // 已经生产的产品数
    public static volatile int hasProduceCount = 0;
    // 已经消费的产品数
    public static volatile int hasConsumeCount = 0;
    // 容器最大容量
    public static int containerSize = 3;
    // 使用公平策略的可重入锁，便于观察演示结果
    public static ReentrantLock lock = new ReentrantLock(true);
    public static Condition notEmpty = lock.newCondition();
    public static Condition notFull = lock.newCondition();
    // 容器
    public static LinkedList<Integer> container = new LinkedList<Integer>();
    // 用于标识产品
    public static AtomicInteger idGenerator = new AtomicInteger();

    public static void main(String[] args) {
        Thread p1 = new Thread(new Producer(), "p-1");
        Thread p2 = new Thread(new Producer(), "p-2");
        Thread p3 = new Thread(new Producer(), "p-3");

        Thread c1 = new Thread(new Consumer(), "c-1");
        Thread c2 = new Thread(new Consumer(), "c-2");
        Thread c3 = new Thread(new Consumer(), "c-3");

        c1.start();
        c2.start();
        c3.start();
        p1.start();
        p2.start();
        p3.start();
        try{
            c1.join();
            c2.join();
            c3.join();
            p1.join();
            p2.join();
            p3.join();
        }catch(Exception e){

        }
        System.out.println(" done. ");
    }
    static class Producer implements Runnable{
        @Override
        public void run() {
            while(true){
                lock.lock();
                try{
                    // 容器满了，需要等待非满条件
                    while(container.size() >= containerSize){
                        notFull.await();
                    }

                    // 到这里表明容器未满，但需要再次判断是否已经完成了任务
                    if(hasProduceCount >= totalCount){
                        System.out.println(Thread.currentThread().getName()+" producer exit");
                        return ;
                    }

                    int product = idGenerator.incrementAndGet();
                    // 把生产出来的产品放入容器
                    container.addLast(product);
                    System.out.println(Thread.currentThread().getName() + " product " + product);
                    hasProduceCount++;

                    // 通知消费线程可以去消费了
                    notEmpty.signal();
                } catch (InterruptedException e) {
                }finally{
                    lock.unlock();
                }
            }
        }
    }
    static class Consumer implements Runnable{
        @Override
        public void run() {
            while(true){
                lock.lock();
                try{
                    if(hasConsumeCount >= totalCount){
                        System.out.println(Thread.currentThread().getName()+" consumer exit");
                        return ;
                    }

                    // 一直等待有产品了，再继续往下消费
                    while(container.isEmpty()){
                        notEmpty.await(2, TimeUnit.SECONDS);
                        if(hasConsumeCount >= totalCount){
                            System.out.println(Thread.currentThread().getName()+" consumer exit");
                            return ;
                        }
                    }

                    Integer product = container.removeFirst();
                    System.out.println(Thread.currentThread().getName() + " consume " + product);
                    hasConsumeCount++;

                    // 通知生产线程可以继续生产产品了
                    notFull.signal();
                } catch (InterruptedException e) {
                }finally{
                    lock.unlock();
                }
            }
        }
    }
}
