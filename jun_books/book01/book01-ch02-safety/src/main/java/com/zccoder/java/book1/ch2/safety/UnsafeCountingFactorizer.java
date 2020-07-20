package com.zccoder.java.book1.ch2.safety;

import com.zccoder.java.book1.ch0.basic.NotThreadSafe;

/**
 * 标题：计算调用数量而没有必要的同步（不要这样做）<br>
 * 描述：原因是 count++ 很容易遗失更新<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
@NotThreadSafe
public class UnsafeCountingFactorizer {

    private long count = 0;

    public void service(String name) {
        // count++ 不是原子操作。是3个离散操作的简写形式：获得当前值，加1，写回新值。这是一个“读-改-写”操作的实例。
        count++;
        System.out.println("print name :" + name);
    }

    public long getCount() {
        return count;
    }
}
