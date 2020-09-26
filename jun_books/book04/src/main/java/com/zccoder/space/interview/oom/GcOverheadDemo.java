package com.zccoder.space.interview.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * java.lang.OutOfMemoryError: GC overhead limit exceeded 示例
 *
 * @author zc
 * @date 2020/05/04
 */
public class GcOverheadDemo {

    public static void main(String[] args) {

        int i = 0;
        List<String> list = new ArrayList<>();

        // 配置JVM参数：-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=d:/
        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Throwable e) {
            System.out.println(" i = " + i);
            e.printStackTrace();
        }
    }
}
