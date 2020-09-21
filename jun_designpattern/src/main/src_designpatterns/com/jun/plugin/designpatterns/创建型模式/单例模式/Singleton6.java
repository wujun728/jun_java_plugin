package com.jun.plugin.designpatterns.创建型模式.单例模式;

import java.util.Vector;

public class Singleton6 {
    private static Singleton6 instance = null;
    private Vector<?> properties = null;

    public Vector<?> getProperties() {
        return properties;
    }

    private Singleton6() {
    }

    /**
     * 单独为创建对象方法加上synchronized关键字
     * 
     * 作者: zhoubang 日期：2015年10月27日 上午11:24:08
     */
    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new Singleton6();
        }
    }

    /**
     * 获取实例对象的方法 与 创建实例对象的方法分开
     * 
     * 作者: zhoubang 日期：2015年10月27日 上午11:24:30
     * 
     * @return
     */
    public static Singleton6 getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    public void updateProperties() {
        Singleton6 shadow = new Singleton6();
        properties = shadow.getProperties();
    }
}
