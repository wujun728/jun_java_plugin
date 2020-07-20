package com.zccoder.java.book1.ch2.safety;


import com.zccoder.java.book1.ch0.basic.GuardedBy;
import com.zccoder.java.book1.ch0.basic.ThreadSafe;

import java.math.BigInteger;

/**
 * 标题：缓存传入的值与其绝对值的关系<br>
 * 描述：线程安全的，但响应性令人无法接受（不要这样做）<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
@ThreadSafe
public class SynchronizedFactorizer {

    @GuardedBy("this")
    private BigInteger lastNumber;
    @GuardedBy("this")
    private BigInteger lastResult;

    public synchronized Integer service(Integer param) {
        // 将方法声明为 synchronized，同一时间只有一个线程可以进入service方法。
        // 这将导致性能问题，而非线程安全问题

        BigInteger integer = new BigInteger(param.toString());
        // 如果本次传入的数等于上一次传入的数
        if (integer.equals(lastNumber)) {
            // 直接返回结果
            return lastResult.intValue();
        }
        // 计算绝对值并缓存
        BigInteger result = integer.abs();
        lastNumber = integer;
        lastResult = result;
        return result.intValue();
    }
}
