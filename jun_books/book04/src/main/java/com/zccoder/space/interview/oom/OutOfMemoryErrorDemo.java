package com.zccoder.space.interview.oom;

import java.util.Random;

/**
 * 内存溢出示例
 *
 * @author zc
 * @date 2020/05/04
 */
public class OutOfMemoryErrorDemo {

    public static void main(String[] args) {
        String str = "zc coder";

        // 故意创建对象导致OOM。需设置JVM启动参数：-Xms10m -Xmx10m
        while (true) {
            str += str + new Random().nextInt(11111111) + new Random().nextInt(22222222);
            str.intern();
        }
    }

}