package com.mycompany.myproject.patterns.creational.singleton;


/**
 * 2、懒汉式，线程安全
 */
public class SingletonObject2 {
    private static SingletonObject2 singletonObject2;

    //private static Object obj= new Object();

    private SingletonObject2(){}

    public synchronized static SingletonObject2 getIntance(){

        if(singletonObject2 == null){
            singletonObject2 = new SingletonObject2();
        }

        return singletonObject2;
    }

    /**
     * 4、双检锁/双重校验锁（DCL，即 double-checked locking）
     * @return
     */
    public static SingletonObject2 getIntance2(){

        if(singletonObject2 == null){

            synchronized (SingletonObject2.class){
            //synchronized (obj){
                if(singletonObject2 == null){
                    singletonObject2 = new SingletonObject2();

                }
            }
        }

        return singletonObject2;
    }
}
