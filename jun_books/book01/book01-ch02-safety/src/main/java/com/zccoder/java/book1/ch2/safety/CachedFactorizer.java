package com.zccoder.java.book1.ch2.safety;


import com.zccoder.java.book1.ch0.basic.GuardedBy;
import com.zccoder.java.book1.ch0.basic.ThreadSafe;

import java.math.BigInteger;

/**
 * 标题：缓存传入的值与其绝对值的关系<br>
 * 描述：通过缩小synchronized块的范围来维护线程安全性，可以提高并发性能<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
@ThreadSafe
public class CachedFactorizer {

    @GuardedBy("this")
    private BigInteger lastNumber;
    @GuardedBy("this")
    private BigInteger lastResult;
    @GuardedBy("this")
    private long hits;
    @GuardedBy("this")
    private long cacheHits;

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHitsRatio() {
        return (double) cacheHits / (double) hits;
    }

    public Integer service(Integer param) {
        BigInteger integer = new BigInteger(param.toString());
        BigInteger result = null;

        synchronized (this) {
            ++hits;
            if (integer.equals(lastNumber)) {
                ++cacheHits;
                result = lastResult;
            }
        }

        if (result == null) {
            result = integer.abs();
            synchronized (this) {
                lastNumber = integer;
                lastResult = result;
            }
        }
        return result.intValue();
    }
}
