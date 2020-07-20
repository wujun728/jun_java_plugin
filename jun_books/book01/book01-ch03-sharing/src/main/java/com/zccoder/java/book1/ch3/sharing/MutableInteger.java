package com.zccoder.java.book1.ch3.sharing;

import com.zccoder.java.book1.ch0.basic.NotThreadSafe;

/**
 * 标题：非线程安全的可变整数访问器<br>
 * 描述：数据未同步<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
@NotThreadSafe
public class MutableInteger {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
