package com.zccoder.java.book1.ch4.composing;

import com.zccoder.java.book1.ch0.basic.Immutable;

/**
 * 标题：不可变类<br>
 * 描述：<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
@Immutable
public class Point {

    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
