package com.zccoder.java.book1.ch4.composing;

import com.zccoder.java.book1.ch0.basic.NotThreadSafe;

/**
 * 标题：可变Point<br>
 * 描述：<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
@NotThreadSafe
public class MutablePoint {

    public int x, y;

    public MutablePoint() {
        this.x = 0;
        this.y = 0;
    }

    public MutablePoint(MutablePoint point) {
        this.x = point.x;
        this.y = point.y;
    }
}
