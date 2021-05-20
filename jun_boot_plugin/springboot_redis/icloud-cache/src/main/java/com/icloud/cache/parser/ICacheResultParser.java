package com.icloud.cache.parser;

import java.lang.reflect.Type;

/**
 * cache结果解析
 * <p/>
 * 解决问题：
 *
 * @author Wujun
 * @version 1.0
 * @date 2018年5月6日
 * @since 1.7
 */
public interface ICacheResultParser {
    /**
     * 解析结果
     *
     * @param value
     * @param returnType
     * @param origins
     * @return
     */
    public Object parse(String value, Type returnType, Class<?>... origins);
}
