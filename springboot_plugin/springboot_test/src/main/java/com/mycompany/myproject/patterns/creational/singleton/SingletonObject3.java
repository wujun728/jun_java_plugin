package com.mycompany.myproject.patterns.creational.singleton;

/**
 * 3、饿汉式  线程安全的
 */
public class SingletonObject3 {

    private static SingletonObject3 INSTANCE = new SingletonObject3();

    private SingletonObject3(){

    }

    public static SingletonObject3 getIntance(){

        return INSTANCE;
    }

}
