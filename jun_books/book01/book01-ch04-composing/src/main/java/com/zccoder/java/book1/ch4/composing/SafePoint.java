package com.zccoder.java.book1.ch4.composing;

import com.zccoder.java.book1.ch0.basic.GuardedBy;
import com.zccoder.java.book1.ch0.basic.ThreadSafe;

/**
 * 标题：清单4.11 可变的线程安全Point类<br>
 * 描述：<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
@ThreadSafe
public class SafePoint {

    @GuardedBy("this")
    private int x, y;

    public SafePoint(int[] arr) {
        this(arr[0], arr[1]);
    }

    public SafePoint(SafePoint point) {
        this(point.get());
    }

    public SafePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public synchronized int[] get() {
        return new int[]{x, y};
    }

    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
