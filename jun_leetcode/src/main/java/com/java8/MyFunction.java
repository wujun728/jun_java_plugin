package com.java8;

/**
 * @author BaoZhou
 * @date 2018/7/31
 */
public interface MyFunction {
    default String getName() {
        return "hello";
    }
}
