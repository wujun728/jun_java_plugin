package com.zccoder.space.interview.gc;

/**
 * GC Roots 示例
 *
 * @author zc
 * @date 2020/05/04
 */
public class GcRootsDemo {

    private byte[] byteArray = new byte[100 * 1024 * 1024];

    /**
     * 类静态属性引用的对象
     */
    private static GcRootsDemo demo2;
    /**
     * 常量引用的对象
     */
    private static final GcRootsDemo demo3 = new GcRootsDemo();

    public static void main(String[] args) {
        method1();
    }

    public static void method1() {
        // 虚拟机栈中引用的对象
        GcRootsDemo demo1 = new GcRootsDemo();
        System.gc();
        System.out.println("第一次GC完成");
    }

}