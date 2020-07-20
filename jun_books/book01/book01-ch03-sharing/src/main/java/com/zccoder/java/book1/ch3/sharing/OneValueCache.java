package com.zccoder.java.book1.ch3.sharing;

import com.zccoder.java.book1.ch0.basic.Immutable;

import java.math.BigInteger;

/**
 * 标题：在不可变的容器中缓存数字和它的绝对值<br>
 * 描述：使用volatile发布不可变对象<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
@Immutable
public class OneValueCache {

    private final BigInteger lastNumber;
    private final BigInteger lastResult;

    public OneValueCache(BigInteger lastNumber, BigInteger lastResult) {
        this.lastNumber = lastNumber;
        this.lastResult = lastResult;
    }

    public BigInteger getLastResult(BigInteger number) {
        if (lastNumber == null || !lastNumber.equals(number)) {
            return number;
        }
        return lastResult;
    }
}
