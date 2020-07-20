package com.zccoder.java.book1.ch5.block;

import java.util.Vector;

/**
 * 标题：同步容器演示<br>
 * 描述：<br>
 * 时间：2018/10/26<br>
 *
 * @author zc
 **/
public class VectorDemo {

    // 操作Vector的复合操作可能导致混乱的结果

    public static Object getLastNotSafe(Vector list) {
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    }

    public static void deleteLastNotSafe(Vector list) {
        int lastIndex = list.size() - 1;
        list.remove(lastIndex);
    }


    // 使用客户端加锁，对Vector进行复合操作

    public static Object getLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size() - 1;
            return list.get(lastIndex);
        }
    }

    public static void deleteLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size() - 1;
            list.remove(lastIndex);
        }
    }


    // 迭代中可能抛出ArrayIndexOutOfBoundsException

    public static void forEachNotSafe(Vector list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("do something: " + list.get(i));
        }
    }


    // 使用客户端加锁进行迭代

    public static void forEach(Vector list) {
        synchronized (list) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("do something: " + list.get(i));
            }
        }
    }
}
