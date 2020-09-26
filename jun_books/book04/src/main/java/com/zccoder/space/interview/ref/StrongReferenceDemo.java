package com.zccoder.space.interview.ref;

/**
 * 强引用示例
 *
 * @author zc
 * @date 2020/05/04
 */
public class StrongReferenceDemo {

    public static void main(String[] args) {

        // 这样定义的默认就是强引用
        Object object1 = new Object();
        // 引用赋值
        Object object2 = object1;
        // 置空
        object1 = null;

        System.gc();

        System.out.println(object2);

    }
}
