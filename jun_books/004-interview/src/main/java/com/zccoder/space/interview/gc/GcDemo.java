package com.zccoder.space.interview.gc;

import java.util.Random;

/**
 * GC 示例
 * <p>新生代收集器</p>
 * <p>
 * 1.-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC
 * （DefNew+Tenured）
 * </p>
 * <p>
 * 2.-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC
 * （ParNew+Tenured）
 * </p>
 * <p>
 * 3.-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC
 * （PSYoungGen+ParOldGen）
 * </p>
 * <p>老年代收集器</p>
 * <p>
 * 4.1-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC
 * （PSYoungGen+ParOldGen）
 * </p>
 * <p>
 * 4.2（不加就是默认UseParallelGC）-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags
 * （PSYoungGen+ParOldGen）
 * </p>
 * <p>
 * 5 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC （par new
 * generation+concurrent mark-sweep generation）
 * </p>
 * <p>
 * 6 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
 * </p>
 * <p>
 * 7 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+UseSerialOldGC （理论知道即可，实际中Java8已经被优化掉了，没有了）
 * </p>
 *
 * @author zc
 * @date 2020/05/05
 */
public class GcDemo {

    public static void main(String[] args) {
        System.out.println("GCDemo hello");
        try {
            String str = "zc coder";
            while (true) {
                str += str + new Random().nextInt(77777777) + new Random().nextInt(88888888);
                str.intern();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
