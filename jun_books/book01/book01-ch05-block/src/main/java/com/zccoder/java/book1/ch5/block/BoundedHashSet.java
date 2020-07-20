package com.zccoder.java.book1.ch5.block;

import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 标题：清单5.14 使用型号量来约束容器<br>
 * 描述：<br>
 * 时间：2018/10/26<br>
 *
 * @author zc
 **/
public class BoundedHashSet<T> {

    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundedHashSet(Set<T> set, Semaphore semaphore) {
        this.set = set;
        this.semaphore = semaphore;
    }

    public boolean add(T o) throws InterruptedException {
        semaphore.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                semaphore.release();
            }
        }
    }

    public boolean remove(Object o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved) {
            semaphore.release();
        }
        return wasRemoved;
    }

}
