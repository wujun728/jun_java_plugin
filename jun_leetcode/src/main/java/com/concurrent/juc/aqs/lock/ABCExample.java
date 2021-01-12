package com.concurrent.juc.aqs.lock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程轮流输出ABC,利用Condition通知
 * @author BaoZhou
 * @date 2018/7/29
 */
public class ABCExample {
    public static void main(String[] args) {
        LoopTask loopDemo = new LoopTask();
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(3, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        pool.execute(new LoopA(loopDemo));
        pool.execute(new LoopB(loopDemo));
        pool.execute(new LoopC(loopDemo));

    }
}

class LoopA implements Runnable {
    LoopTask loopTask;

    public LoopA(LoopTask loopTask) {
        this.loopTask = loopTask;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            loopTask.loopA(i);
        }
    }
}


class LoopC implements Runnable {
    LoopTask loopTask;

    public LoopC(LoopTask loopTask) {
        this.loopTask = loopTask;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            loopTask.loopC(i);
        }
    }
}

class LoopB implements Runnable {
    LoopTask loopTask;

    public LoopB(LoopTask loopTask) {
        this.loopTask = loopTask;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            loopTask.loopB(i);
        }
    }
}


class LoopTask {
    ReentrantLock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();
    int currentNum = 1;

    public void loopA(int totalLoop) {
        lock.lock();
        try {

            if (currentNum != 1) {
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "A" + "\t" + totalLoop);
            currentNum = 2;
            condition2.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopB(int totalLoop) {
        lock.lock();
        try {

            if (currentNum != 2) {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "B" + "\t" + totalLoop);
            currentNum = 3;
            condition3.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopC(int totalLoop) {

        lock.lock();
        try {

            if (currentNum != 3) {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "C" + "\t" + totalLoop);
            currentNum = 1;
            condition1.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


class LoopPrint {


    static void print(int loopTime, String text) {
        for (int i = 0; i < loopTime; i++) {
            System.out.println(text);
        }
    }

}
