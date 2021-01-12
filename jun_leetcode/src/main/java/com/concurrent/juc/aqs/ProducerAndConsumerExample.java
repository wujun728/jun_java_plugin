package com.concurrent.juc.aqs;

/**
 * 生产者消费者
 * @author BaoZhou
 * @date 2018/7/27
 */
public class ProducerAndConsumerExample {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);
        new Thread(productor, "生产者A").start();
        new Thread(productor, "生产者B").start();
        new Thread(consumer, "消费者A").start();
        new Thread(consumer, "消费者B").start();
    }
}

class Clerk {
    private int product = 0;

    public synchronized void get() {
        while (product >= 1) {
            System.out.println(Thread.currentThread().getName() + ":"+ "满了");
            try {
                this.wait();
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            System.out.println(Thread.currentThread().getName() + ":" + ++product);
            this.notifyAll();

    }

    public synchronized void sale() {
        while (product <= 0) {
            System.out.println(Thread.currentThread().getName() + ":"+ "缺货");
            try {
                this.wait();
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            System.out.println(Thread.currentThread().getName() + ":" + --product);
            this.notifyAll();

    }
}

class Productor implements Runnable {
    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            clerk.get();
        }
    }
}

class Consumer implements Runnable {
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            clerk.sale();
        }
    }
}
