package com.zccoder.space.interview.gc;

import java.util.Random;

/**
 * G1GC 示例
 *
 * @author zc
 * @date 2020/05/06
 */
public class G1GcDemo {

    public static void main(String[] args) {
        // -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
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
