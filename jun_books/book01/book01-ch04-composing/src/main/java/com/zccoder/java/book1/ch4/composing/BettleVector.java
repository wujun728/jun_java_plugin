package com.zccoder.java.book1.ch4.composing;

import com.zccoder.java.book1.ch0.basic.ThreadSafe;

import java.util.Vector;

/**
 * 标题：清单4.13 扩展的Vector包含一个“缺少即加入”方法<br>
 * 描述：向已有的线程安全类添加功能<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
@ThreadSafe
public class BettleVector<E> extends Vector<E> {

    private static final long serialVersionUID = -5535580824760838578L;

    public synchronized boolean putIfAbsent(E e) {
        boolean absent = !contains(e);
        if (absent) {
            add(e);
        }
        return absent;
    }
}
