package com.concurrent.sync;


import com.concurrent.juc.annotation.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Vector;

/**
 * @author BaoZhou
 * @date 2019/1/6
 */
@Slf4j
@NotThreadSafe
public class VectorDemoThreadUnsafe {
    public static int clientTotal = 1000;

    public static int threadTotal = 200;

    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            vector.add(i);
        }
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < vector.size(); i++) {
                vector.remove(i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < vector.size(); i++) {
                vector.get(i);
            }
        });

        thread1.start();
        thread2.start();
    }

}
