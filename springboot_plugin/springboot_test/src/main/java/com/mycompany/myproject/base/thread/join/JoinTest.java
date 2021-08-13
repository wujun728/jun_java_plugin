package com.mycompany.myproject.base.thread.join;

import java.util.concurrent.TimeUnit;

public class JoinTest {

    public void a(Thread joinThread) {
        System.out.println("线程a开始执行...");
        joinThread.start();
        try {

            System.out.println("线程a  终于轮到我了 ...");
            System.out.println("线程a  终于轮到我了 ...");
            System.out.println("线程a  终于轮到我了 ...");
            System.out.println("线程a  终于轮到我了 ...");

            TimeUnit.SECONDS.sleep(10);

            // 当前线程阻塞直到joinThread执行完毕
            System.out.println("线程a你先停停 让我（b）先执行吧， 谢谢 ...");
            joinThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程a执行结束...");
    }

    public void b() {
        System.out.println("加塞线程开始执行...");
        try {
            System.out.println("加塞了  啦啦啦啦啦啦  我是卖报的小红家...");
            System.out.println("加塞了  啦啦啦啦啦啦  我是卖报的小红家...");
            TimeUnit.SECONDS.sleep(50);
            System.out.println("加塞了  啦啦啦啦啦啦  我是卖报的小红家...");
            System.out.println("加塞了  啦啦啦啦啦啦  我是卖报的小红家...");
            System.out.println("加塞了  啦啦啦啦啦啦  我是卖报的小红家...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("加塞线程执行结束...");
    }

    public static void main(String[] args) {
        final JoinTest demo = new JoinTest();
        final Thread joinThread = new Thread() {
            @Override
            public void run() {
                demo.b();
            }
        };

        new Thread() {
            @Override
            public void run() {
                demo.a(joinThread);
            }
        }.start();
    }

}
