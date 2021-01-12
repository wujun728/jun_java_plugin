package com.designpatterns.singleton.code;

import java.io.Serializable;

/**
 * 饿汉式单例模式
 * @author BaoZhou
 * @date 2018/12/27
 */
public class HungrySingleton implements Serializable {
    private final static HungrySingleton instance;

    static {
        instance = new HungrySingleton();
    }


    public HungrySingleton() {
        if(instance!=null){
            throw new RuntimeException("单例构造器禁止反射");
        }
    }

    public static HungrySingleton getInstance() {
        return instance;
    }

    /**
     * 涉及序列化和反序列化会破坏单例模式
     * @return
     */
    private Object readResolve(){
        return instance;
    }
}
