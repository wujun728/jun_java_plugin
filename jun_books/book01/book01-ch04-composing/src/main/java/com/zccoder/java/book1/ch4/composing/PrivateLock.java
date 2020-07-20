package com.zccoder.java.book1.ch4.composing;

import com.zccoder.java.book1.ch0.basic.GuardedBy;

/**
 * 标题：私有锁保护状态<br>
 * 描述：使用私有锁<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
public class PrivateLock {

    private final Object myLock = new Object();
    @GuardedBy("myLock")
    private Widget widget;

    public void doSomething() {
        synchronized (myLock) {
            // 访问或修改 widget 的状态
            widget = new Widget();
            widget.doSomething();
        }
    }
}
