package com.zccoder.java.book1.ch1.start;

import com.zccoder.java.book1.ch0.basic.GuardedBy;
import com.zccoder.java.book1.ch0.basic.ThreadSafe;

/**
 * 标题：线程安全的序列生成器<br>
 * 描述：线程安全的序列生成器<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
@ThreadSafe
public class Sequence {

    @GuardedBy("this")
    private int value;

    /**
     * 返回下一个值
     *
     * @return 下一个值
     */
    public synchronized int getNext() {

        /*
         * 自增 value++ 看起来是一个单一的操作
         * 但事实上有三步
         * 1.读取value
         * 2.进行+1运算
         * 3.将运算结果赋值给value
         */
        return value++;
    }

}
