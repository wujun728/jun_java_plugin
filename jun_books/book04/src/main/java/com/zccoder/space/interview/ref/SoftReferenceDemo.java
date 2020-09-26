package com.zccoder.space.interview.ref;

import java.lang.ref.SoftReference;

/**
 * 软引用示例
 *
 * @author zc
 * @date 2020/05/04
 */
public class SoftReferenceDemo {

    public static void main(String[] args) {
        softMemoryNotEnough();
    }

    /**
     * 内存不足的情况
     */
    private static void softMemoryNotEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;

        try {
            // 故意产生大对象并配置小的内存，让它内存不够用了导致OOM，看软引用的回收情况
            // -Xms5m -Xmx5m -XX:+PrintGCDetails
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(o1);
            System.out.println(softReference.get());
        }

    }

    /**
     * 内存充足的情况
     */
    private static void softMemoryEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;
        System.gc();

        System.out.println(o1);
        System.out.println(softReference.get());
    }
}
