package com.chentongwei.common.handler;

import com.chentongwei.common.constant.ResponseEnum;
import com.chentongwei.common.constant.StatusEnum;
import com.chentongwei.common.exception.BussinessException;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 通用的异常处理
 *
 * @author TongWei.Chen 2017-05-18 16:05:28
 */
public final class CommonExceptionHandler {

    private CommonExceptionHandler() {}

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
     * @param responseEnum：返回消息
     */
    public static final void nullCheck(final Object object, final ResponseEnum responseEnum) {
        if(null == object) {
            throwException(responseEnum);
        }
    }

    /**
     * 异常处理：检查集合是否为空
     *
     * @param list
     */
    public static void listCheck(final List<?> list, final ResponseEnum responseEnum) {
        if (CollectionUtils.isEmpty(list)) {
            throwException(responseEnum);
        }
    }

    /**
     * 检查是否存在
     *
     * @param count > 0：存在
     */
    public static final void existsCheck(final int count, final ResponseEnum responseEnum) {
        if(count > 0) {
            throwException(responseEnum);
        }
    }
    
    /**
     * 检查是否成功
     *
     * @param flag：true：成功
     */
    public static final void flagCheck(final boolean flag) {
        if(! flag) {
            throwException(ResponseEnum.OPERATE_ERROR);
        }
    }

    /**
     * 检查用户是否被禁用
     *
     * @param status：0：禁用 1：正常
     */
    public static final void userDisabledCheck(final Integer status) {
        if(! Objects.equals(StatusEnum.USER_ENABLED.getCode(), status)) {
            throwException(ResponseEnum.USER_DISABLED);
        }
    }

    /**
     * 检查用户是否是管理员
     *
     * @param isAdmin：0：普通用户 1：admin
     */
    public static final void isAdminCheck(final boolean isAdmin) {
        if(! isAdmin) {
            throwException(ResponseEnum.IS_NOT_ADMIN);
        }
    }

    /**
     * 抛出异常
     *
     * @param responseEnum 枚举常量
     */
    private static final void throwException(final ResponseEnum responseEnum) {
        throw new BussinessException(responseEnum);
    }
}
