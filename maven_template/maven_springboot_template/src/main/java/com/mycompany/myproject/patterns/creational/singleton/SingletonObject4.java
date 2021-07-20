package com.mycompany.myproject.patterns.creational.singleton;


/**
 * 登记式/静态内部类   线程安全
 */
public class SingletonObject4 {

    private SingletonObject4(){

    }

    public static SingletonObject4 getInstance(){
        return SingletonHolder.INSTANCE;
    }


    public static class SingletonHolder{
        public static SingletonObject4 INSTANCE = new SingletonObject4();
    }
}
