package com.zccoder.space.interview.ref;

import java.lang.ref.WeakReference;

/**
 * 弱引用示例
 *
 * @author zc
 * @date 2020/05/04
 */
public class WeakReferenceDemo {

    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);
        System.out.println(o1);
        System.out.println(weakReference.get());

        o1 = null;
        System.gc();

        System.out.println(o1);
        System.out.println(weakReference.get());
    }
}
