package com.mycompany.myproject.base.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentranLockTest {

    public static void main(String[] args) throws InterruptedException{

        ReentrantLock reentrantLock = new ReentrantLock();

        Thread currentThread = new Thread(new Runnable() {
            @Override
            public void run() {

                reentrantLock.lock();

                try{
                    TimeUnit.MINUTES.sleep(60l);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }

            }
        }, "Current-Thread");

        currentThread.start();

        TimeUnit.SECONDS.sleep(2);

        Thread queueThread1 = new Thread(new Runnable() {
            @Override
            public void run() {

                reentrantLock.lock();

                try{
                    TimeUnit.MINUTES.sleep(60l);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }

            }
        }, "Queue-thread-1");

        queueThread1.start();
        TimeUnit.SECONDS.sleep(2);


        Thread queueThread2 = new Thread(new Runnable() {
            @Override
            public void run() {

                reentrantLock.lock();

                try{
                    TimeUnit.MINUTES.sleep(60l);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }

            }
        }, "Queue-thread-2");

        //queueThread2.start();
    }
    
}
