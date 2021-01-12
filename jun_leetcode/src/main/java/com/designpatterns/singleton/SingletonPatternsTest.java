package com.designpatterns.singleton;

import com.designpatterns.singleton.code.EnumSingleton;
import com.designpatterns.singleton.code.HungrySingleton;
import com.designpatterns.singleton.code.LazyDoubleCheckSingleton;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

/**
 * @author BaoZhou
 * @date 2018/12/27
 */
public class SingletonPatternsTest {

    /**
     * 双重锁线程安全
     */
    @Test
    public void test() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(3, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 100; i++) {
            pool.execute(new create());
        }
        pool.shutdown();
    }

    public class create implements Runnable {
        @Override
        public void run() {
            LazyDoubleCheckSingleton instance = LazyDoubleCheckSingleton.getInstance();
            System.out.println(Thread.currentThread().getName() + " " + instance);
        }
    }


    /**
     * 普通饿汉单例被序列化会受到影响（如果不加readResolve）
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void test2() throws IOException, ClassNotFoundException {
        HungrySingleton instance = HungrySingleton.getInstance();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("singletonFile"));
        out.writeObject(instance);
        File file = new File("singletonFile");
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        HungrySingleton newInstance = (HungrySingleton) in.readObject();
        System.out.println(newInstance == instance);
    }

    /**
     * 饿汉单例模被反射
     */
    @Test
    public void test3() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = HungrySingleton.class;
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        HungrySingleton object = (HungrySingleton) constructor.newInstance();
        System.out.println(object == HungrySingleton.getInstance());

    }

    /**
     * 枚举单例模式不会被反射
     */
    @Test
    public void test4() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = EnumSingleton.class;
        Constructor constructor = clazz.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        EnumSingleton object = (EnumSingleton) constructor.newInstance("Baozhou", 123);
        System.out.println(object == EnumSingleton.getInstance());
    }


    /**
     * 枚举单例模式不会被序列化影响
     */
    @Test
    public void test5() throws IOException, ClassNotFoundException {
        EnumSingleton instance = EnumSingleton.getInstance();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("singletonFile"));
        out.writeObject(instance);
        File file = new File("singletonFile");
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        EnumSingleton newInstance = (EnumSingleton) in.readObject();
        System.out.println(newInstance == instance);
    }


}
