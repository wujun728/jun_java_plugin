package com.java8;

/**
 * 接口中定义默认方法
 * @author BaoZhou
 * @date 2018/8/1
 */
public class MyFunctionImpl implements MyFunction {
    public static void main(String[] args) {
        System.out.println(new MyFunctionImpl().getName());
    }
}
