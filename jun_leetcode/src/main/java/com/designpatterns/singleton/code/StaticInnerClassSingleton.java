package com.designpatterns.singleton.code;

/**
 * 基于静态内部类
 * @author BaoZhou
 * @date 2018/12/27
 */
public class StaticInnerClassSingleton {
    private static class InnerClass{
        private static StaticInnerClassSingleton staticInnerClassSingleton;
    }
    public static StaticInnerClassSingleton getInstance(){
        return InnerClass.staticInnerClassSingleton;
    }
}
