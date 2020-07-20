package com.zccoder.java.book1.ch3.sharing;

import com.zccoder.java.book1.ch0.basic.ThreadSafe;

import java.math.BigInteger;

/**
 * 标题：使用volatile发布不可变对象<br>
 * 描述：使用OneValueCache存储缓存的数字及其绝对值<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
@ThreadSafe
public class VolatileCachedFactorizer {

    private volatile OneValueCache cache = new OneValueCache(null, null);

    public BigInteger service(BigInteger number) {
        BigInteger result = cache.getLastResult(number);
        if (result == null) {
            result = number.abs();
            cache = new OneValueCache(number, result);
        }
        return result;
    }

}
