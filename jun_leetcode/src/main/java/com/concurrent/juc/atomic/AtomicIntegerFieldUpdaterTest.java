package com.concurrent.juc.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public class AtomicIntegerFieldUpdaterTest {
    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterModel> updater1 =
                AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterModel.class, "num");
        AtomicIntegerFieldUpdaterModel model = new AtomicIntegerFieldUpdaterModel();
        System.out.println(updater1.compareAndSet(model, 1, 2));
        LongAdder longAdder = new LongAdder();
        longAdder.add(78L);
        System.out.println(longAdder.longValue());

        AtomicIntegerArray atomicArray = new AtomicIntegerArray(5);
        /**
         * 设置第一个元素的值为5
         */
        atomicArray.set(0, 5);
        /**
         * 第一个元素减去1
         */
        int current = atomicArray.decrementAndGet(0);
        System.out.println("current = " + current);//current = 4
    }


}

