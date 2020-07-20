package com.zccoder.space.interview.oom;

import sun.misc.VM;

import java.nio.ByteBuffer;

/**
 * java.lang.OutOfMemoryError: Direct buffer memory 示例
 *
 * @author zc
 * @date 2020/05/04
 */
public class DirectBufferMemoryDemo {

    public static void main(String[] args) {
        // 配置JVM参数：-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
        double size = VM.maxDirectMemory() / (double) 1024 / 1024;
        System.out.println("配置的MaxDirectMemorySize：" + size + "MB");

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}
