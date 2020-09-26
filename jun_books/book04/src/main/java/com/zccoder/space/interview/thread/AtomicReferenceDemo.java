package com.zccoder.space.interview.thread;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用示例
 *
 * @author zc
 * @date 2020/05/02
 */
public class AtomicReferenceDemo {

    public static void main(String[] args) {

        User z3 = new User("z3", 22);
        User li4 = new User("li4", 22);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);

        // 修改成功
        boolean b1 = atomicReference.compareAndSet(z3, li4);
        System.out.println("结果：" + b1 + " \t current data：" + atomicReference.get());

        // 修改失败
        boolean b2 = atomicReference.compareAndSet(z3, li4);
        System.out.println("结果：" + b2 + " \t current data：" + atomicReference.get());
    }

}
