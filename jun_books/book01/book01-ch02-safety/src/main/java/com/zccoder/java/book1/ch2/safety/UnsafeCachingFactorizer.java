package com.zccoder.java.book1.ch2.safety;

import com.zccoder.java.book1.ch0.basic.NotThreadSafe;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 标题：缓存传入的值与其绝对值的关系<br>
 * 描述：没有正确原子化的类<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
@NotThreadSafe
public class UnsafeCachingFactorizer {

    /**
     * 原子引用自身是线程安全的
     */
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger> lastResult = new AtomicReference<>();

    public Integer service(Integer param) {
        // 但方法中存在竞争条件，导致会产生错误的答案
        BigInteger integer = new BigInteger(param.toString());

        // 如果本次传入的数等于上一次传入的数
        if (integer.equals(lastNumber.get())) {
            // 直接返回结果
            return lastResult.get().intValue();
        } else {
            // 计算绝对值并缓存
            BigInteger result = integer.abs();
            lastNumber.set(integer);
            lastResult.set(result);
            return result.intValue();
        }
    }
}
