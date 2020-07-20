package com.zccoer.java.book3.ch2.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 * 标题: 实战：OutOfMemoryError异常<br>
 * 描述: JVM参数：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError<br>
 * 时间: 2019/04/02<br>
 *
 * @author zc
 */
public class HeapOom {

    static class OomObject {

    }

    public static void main(String[] args) {
        List<OomObject> list = new ArrayList<>(16);
        while (true) {
            list.add(new OomObject());
        }
    }
}
