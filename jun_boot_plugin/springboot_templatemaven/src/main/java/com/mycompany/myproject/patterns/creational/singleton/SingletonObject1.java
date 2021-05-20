package com.mycompany.myproject.patterns.creational.singleton;

/**
 * 1、懒汉式，线程不安全
 */
public class SingletonObject1 {

    private static SingletonObject1 singletonObject1;

    private SingletonObject1(){}

    public static SingletonObject1 getIntance(){

        if(singletonObject1 == null){
            singletonObject1 = new SingletonObject1();
        }

        return singletonObject1;
    }
}
