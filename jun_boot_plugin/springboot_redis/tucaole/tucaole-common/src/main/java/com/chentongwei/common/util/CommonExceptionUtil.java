package com.chentongwei.common.util;

import com.chentongwei.common.enums.IBaseEnum;
import com.chentongwei.common.enums.msg.ResponseEnum;
import com.chentongwei.common.exception.BussinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 通用的异常处理
 */
public final class CommonExceptionUtil {

    private CommonExceptionUtil() {}

    /**
     * 检查是否为null
     *
     * @param object：obj
     */
    public static final void nullCheck(final Object object) {
        nullCheck(object, ResponseEnum.NULL);
    }

    /**
     * 检查是否为null
     *
     * @param object：obj
     * @param baseEnum：返回消息
     */
    public static final void nullCheck(final Object object, final IBaseEnum baseEnum) {
        if(null == object) {
            throwBussinessException(baseEnum);
        }
    }

    /**
     * 检查是否不为null
     *
     * @param object：obj
     * @param baseEnum：返回消息
     */
    public static final void notNullCheck(final Object object, final IBaseEnum baseEnum) {
        if(null != object) {
            throwBussinessException(baseEnum);
        }
    }

    /**
     * 检查字符串是否为empty、blank
     *
     * @param value:值
     * @param baseEnum:返回消息
     */
    public static final void strNullCheck(final String value, final IBaseEnum baseEnum) {
        if (StringUtils.isEmpty(value) || StringUtils.isBlank(value)) {
            throwBussinessException(baseEnum);
        }
    }

    /**
     * 异常处理：检查集合是否为空
     *
     * @param list
     */
    public static void listCheck(final List<?> list, final IBaseEnum baseEnum) {
        if (CollectionUtils.isEmpty(list)) {
            throwBussinessException(baseEnum);
        }
    }

    /**
     * 检查是否存在
     *
     * @param count > 0：存在
     */
    public static final void existsCheck(final int count, final IBaseEnum baseEnum) {
        if(count > 0) {
            throwBussinessException(baseEnum);
        }
    }
    
    /**
     * 检查是否成功
     *
     * @param flag：true：成功
     */
    public static final void flagCheck(final boolean flag) {
        flagCheck(flag, ResponseEnum.OPERATE_ERROR);
    }

    /**
     * 检查是否成功
     *
     * @param flag：true：成功
     */
    public static final void flagCheck(final boolean flag, final IBaseEnum baseEnum) {
        if(flag) {
            throwBussinessException(baseEnum);
        }
    }

    /**
     * 抛出业务异常
     *
     * @param baseEnum 枚举常量
     */
    private static final void throwBussinessException(final IBaseEnum baseEnum) {
        throw new BussinessException(baseEnum);
    }
}
