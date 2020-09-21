package com.jun.plugin.designpatterns.创建型模式.单例模式;

public class Singleton3 {
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static Singleton3 instance = null;

    /* 私有构造方法，防止被实例化 */
    private Singleton3() {
    }

    /* 静态工程方法，创建实例 */
    public static Singleton3 getInstance() {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return instance;
    }
    
    
    
    /**
     * 优化了 getInstance() 方法，似乎解决了 Singleton2.java 中提到的问题，将synchronized关键字加在了内部，也就是说当调用的时候是不需要加锁的，只有在instance为null，并创建对象的时候才需要加锁，性能有一定的提升。
     * 
     * 但是，这样的情况，还是有可能有问题的，看下面的情况：
     * 在Java指令中创建对象和赋值操作是分开进行的，也就是说instance = new Singleton();语句是分两步执行的。
     * 但是JVM并不保证这两个操作的先后顺序，也就是说有可能JVM会为新的Singleton实例分配空间，然后直接赋值给instance成员，然后再去初始化这个Singleton实例。这样就可能出错了。
     * 
     * 我们以A、B两个线程为例：
     * 【1】：A、B线程同时进入了第一个if判断
     * 【2】：A首先进入synchronized块，由于instance为null，所以它执行instance = new Singleton();
     * 【3】：由于JVM内部的优化机制，JVM先画出了一些分配给Singleton实例的空白内存，并赋值给instance成员（注意此时JVM没有开始初始化这个实例），然后A离开了synchronized块。
     * 【4】：B进入synchronized块，由于instance此时不是null，因此它马上离开了synchronized块并将结果返回给调用该方法的程序。
     * 【5】：此时B线程打算使用Singleton实例，却发现它没有被初始化，于是错误发生了。
     * 
     * 所以，程序还是有可能发生错误，其实程序在运行过程是很复杂的，从这点我们就可以看出，尤其是在写多线程环境下的程序更有难度，有挑战性。
     * 
     * 我们对该程序做进一步优化，请点击查看  Singleton4.java
     */
}
