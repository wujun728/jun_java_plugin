package com.jun.plugin.resources.convert;

/**
 * Created by Hong on 2017/12/27.
 */
public interface Convert<T> {

    /**
     * 转换接口
     * @param obj   转换对象
     * @return      转换后对象
     */
    T convert(Object obj);
}
