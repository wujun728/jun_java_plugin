package com.zccoder.java.book1.ch3.sharing;

/**
 * 标题：在没有同步的情况下共享变量（不要这样做）<br>
 * 描述：可见性测试<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
public class NoVisibility {

    private static boolean ready;
    private static int number;

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }
}
