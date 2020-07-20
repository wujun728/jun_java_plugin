package com.zccoder.java.book1.ch4.composing;

import com.zccoder.java.book1.ch0.basic.NotThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 标题：清单4.10 NumberRange<br>
 * 描述：<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
@NotThreadSafe
public class NumberRange {

    // 不变约束：lower <= upper

    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    public void setLower(int i) {
        // 警告：不安全的“检查再运行”
        if (i > upper.get()) {
            throw new IllegalArgumentException("can't set lower to " + i + " > upper");
        }
        lower.set(i);
    }

    public void setUpper(int i) {
        // 警告：不安全的“检查再运行”
        if (i < lower.get()) {
            throw new IllegalArgumentException("can't set upper to " + i + " < lower");
        }
        upper.set(i);
    }

    public boolean isInRange(int i) {
        return (i >= lower.get() && i <= upper.get());
    }

}
