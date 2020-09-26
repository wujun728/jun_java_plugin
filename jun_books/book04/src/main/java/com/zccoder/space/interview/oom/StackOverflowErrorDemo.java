package com.zccoder.space.interview.oom;

/**
 * 栈溢出示例
 *
 * @author zc
 * @date 2020/05/04
 */
public class StackOverflowErrorDemo {

    public static void main(String[] args) {
        stackOverflowError();
    }

    private static void stackOverflowError() {
        stackOverflowError();
    }

}