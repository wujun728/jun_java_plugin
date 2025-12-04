package com.company.project.common.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.company.project.common.exception.BusinessException;

import java.util.Collection;

/**
 * 断言工具类
 */
public class AssertUtil {

    /**
     * true不报错
     *
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }

    /**
     * true不报错
     *
     * @param expression
     */
    public static void isTrue(boolean expression) {
        if (!expression) {
            throw new BusinessException("参数无效/数据异常");
        }
    }

    /**
     * string为空报错
     * 不为空不报错
     *
     * @param s
     * @param message
     */
    public static void isStringNotBlank(String s, String message) {
        isTrue(StringUtils.isNotBlank(s), message);
    }

    public static void isStringNotBlank(String s) {
        isTrue(StringUtils.isNotBlank(s));
    }


    /**
     * string为空不报错
     * 不为空报错
     *
     * @param s
     * @param message
     */
    public static void isStringBlank(String s, String message) {
        isTrue(StringUtils.isBlank(s), message);
    }

    public static void isStringBlank(String s) {
        isTrue(StringUtils.isBlank(s));
    }

    /**
     * 对象为null， 不报错
     * 不为null， 报错
     * @param obj
     * @param errorMessage
     */
    public static void isNull(Object obj, String errorMessage) {
        isTrue(obj == null, errorMessage);
    }

    public static void isNull(Object obj) {
        isTrue(obj == null);
    }

    /**
     * 对象不为null， 不报错
     * 为null， 报错
     * @param obj
     * @param errorMessage
     */
    public static void isExists(Object obj, String errorMessage) {
        isTrue(obj != null, errorMessage);
    }

    public static void isExists(Object obj) {
        isTrue(obj != null);
    }

    /**
     * list或者map 不能为空
     * @param collection
     * @param errorMessage
     */
    public static void hasElements(Collection<?> collection, String errorMessage) {
        isTrue(CollectionUtils.isNotEmpty(collection), errorMessage);
    }

    public static void hasElements(Object[] array, String errorMessage) {
        isTrue(array != null && array.length != 0, errorMessage);
    }


}