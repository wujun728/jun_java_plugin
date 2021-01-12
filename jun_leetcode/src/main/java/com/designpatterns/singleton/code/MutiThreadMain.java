package com.designpatterns.singleton.code;

/**
 * @author BaoZhou
 * @date 2018/12/27
 */
public class MutiThreadMain {
    public static void main(String[] args) {
        Thread t1 = new Thread(new T());
        Thread t2 = new Thread(new T());
        t1.start();
        t2.start();
        System.out.println("end");
    }

}
