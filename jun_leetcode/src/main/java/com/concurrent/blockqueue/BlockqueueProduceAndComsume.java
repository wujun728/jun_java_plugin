package com.concurrent.blockqueue;

import java.util.concurrent.*;


public class BlockqueueProduceAndComsume {
    public static void main(String[] args)  {
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(5);
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(new Producer("生产者", blockingQueue));
        executor.execute(new Producer("生产者2", blockingQueue));
        executor.execute(new Producer("生产者3", blockingQueue));
        executor.execute(new Consumer("消费者", blockingQueue));
    }
}

class Producer implements Runnable {

    private String name;
    private BlockingQueue<Integer> queue;

    public Producer(String name, BlockingQueue<Integer> queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                int value = (int) (Math.random() * 100);
                queue.put(value);
                System.out.println(name + " 生产数字 -> " + value + "，当前队列有" + queue.size() + "个数");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    String name;
    BlockingQueue<Integer> queue;

    public Consumer(String name, BlockingQueue<Integer> queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            int value = 0;
            while (true) {
                Thread.sleep(1000);
                value = queue.take();
                System.out.println(name + " 消费数字 -> " + value + "，当前队列有" + queue.size() + "个数");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
