package com.concurrent.juc.aqs;


/**
 * @author BaoZhou
 * @date 2019/9/12 10:51
 */
public class ProducerAndConsumerExampleV2 {
    public static void main(String[] args) {
        ClerkV2 clerk = new ClerkV2();
        ProductorV2 productor = new ProductorV2(clerk);
        ConsumerV2 consumer = new ConsumerV2(clerk);
        new Thread(productor, "生产者A").start();
        new Thread(productor, "生产者B").start();
        new Thread(consumer, "消费者A").start();
        new Thread(consumer, "消费者B").start();
    }
}
    class ClerkV2 {
        private String lock = new String("");
        private int product = 0;

        public void get() {
            synchronized (lock) {
                while (product >= 1) {
                    System.out.println(Thread.currentThread().getName() + ":" + "满了");
                    try {
                        lock.wait();
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + ":" + ++product);
                lock.notifyAll();

            }
        }

        public void sale() {
            synchronized (lock) {
                while (product <= 0) {
                    System.out.println(Thread.currentThread().getName() + ":" + "缺货");
                    try {
                        lock.wait();
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + ":" + --product);
                lock.notifyAll();
            }

        }
    }

    class ProductorV2 implements Runnable {
        ClerkV2 clerk;

        public ProductorV2(ClerkV2 clerk) {
            this.clerk = clerk;
        }

        @Override
        public void run() {
            while (true){
                clerk.get();
            }
        }
    }

    class ConsumerV2 implements Runnable {
        ClerkV2 clerk;

        public ConsumerV2(ClerkV2 clerk) {
            this.clerk = clerk;
        }

        @Override
        public void run() {
            while (true){
                clerk.sale();
            }
        }
    }
