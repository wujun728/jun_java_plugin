package com.designpatterns.singleton.code;

/**
 * 双重锁加载
 * 懒汉式不能防止反射
 * @author BaoZhou
 * @date 2018/12/27
 */
public class LazyDoubleCheckSingleton {
    //volatile 不会重排序
    private volatile static LazyDoubleCheckSingleton lazyDoubleCheckSingleton;

    public LazyDoubleCheckSingleton() {
    }

    public static LazyDoubleCheckSingleton getInstance() {
        if (lazyDoubleCheckSingleton == null) {
            synchronized (LazyDoubleCheckSingleton.class) {
                if (lazyDoubleCheckSingleton == null) {
                    lazyDoubleCheckSingleton = new LazyDoubleCheckSingleton();
                    //1.分配内存给这个对象
                    //2.初始化对象
                    //3.设置对象指向到刚分配的内存地址
                    //小心指令重排序
                }
            }
        }
        return lazyDoubleCheckSingleton;
    }
}
