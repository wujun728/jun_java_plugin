package com.zccoder.java.book1.ch3.sharing;

import com.zccoder.java.book1.ch0.basic.GuardedBy;
import com.zccoder.java.book1.ch0.basic.ThreadSafe;

/**
 * 标题：线程安全的可变整数访问器<br>
 * 描述：数据已同步<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
@ThreadSafe
public class SynchronizedInteger {

    @GuardedBy("this")
    private int value;

    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }
}
