package com.zccoder.java.book2.ch2.start;

/**
 * <br>
 * 标题: 向控制台输出消息<br>
 * 描述: 2-1<br>
 * 时间: 2018/10/15<br>
 *
 * @author zc
 */
public class Welcome {

    public static void main(String[] args) {
        String greeting = "Welcome to Core Java!";
        System.out.println(greeting);
        for (int i = 0; i < greeting.length(); i++) {
            System.out.print("=");
        }
        System.out.println();
    }

}
