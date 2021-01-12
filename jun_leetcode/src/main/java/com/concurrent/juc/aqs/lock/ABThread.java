package com.concurrent.juc.aqs.lock;

import org.junit.jupiter.api.Test;

public class ABThread {
    @Test
    void abThreadOutput() {
        AbLoopTask abLoopTask = new AbLoopTask();
        AbLoopA abLoopA = new AbLoopA(abLoopTask);
        AbLoopB abLoopB = new AbLoopB(abLoopTask);
        new Thread(abLoopA, "A").start();
        new Thread(abLoopB, "B").start();

    }

    class AbLoopA implements Runnable {
        AbLoopTask loopTask;

        public AbLoopA(AbLoopTask loopTask) {
            this.loopTask = loopTask;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                loopTask.increaseAndPrintA();
            }

        }
    }

    class AbLoopB implements Runnable {
        AbLoopTask loopTask;

        public AbLoopB(AbLoopTask loopTask) {
            this.loopTask = loopTask;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                loopTask.increaseAndPrintB();
            }
        }
    }


    class AbLoopTask {
        int k = 0;
        public int isPrint = 2;

        synchronized void increaseAndPrintA() {
            while (isPrint == 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            k++;
            System.out.println(Thread.currentThread().getName() + "  " + k);
            isPrint = 1;
            notifyAll();
        }

        synchronized void increaseAndPrintB() {
            while (isPrint == 2) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            k++;
            System.out.println(Thread.currentThread().getName() + "  " + k);
            isPrint = 2;
            notifyAll();
        }
    }
}
