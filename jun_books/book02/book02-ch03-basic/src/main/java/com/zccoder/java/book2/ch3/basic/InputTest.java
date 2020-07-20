package com.zccoder.java.book2.ch3.basic;

import java.util.Scanner;

/**
 * <br>
 * 标题: 输入输出<br>
 * 描述: 3.7.1<br>
 * 时间: 2018/10/17<br>
 *
 * @author zc
 */
public class InputTest {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入姓名：");
        String name = scanner.nextLine();

        System.out.println("请输入年龄：");
        int age = scanner.nextInt();

        // 输出结果
        System.out.println("Hello, " + name + ". Next year, you'll be " + (age + 1));

    }

}
