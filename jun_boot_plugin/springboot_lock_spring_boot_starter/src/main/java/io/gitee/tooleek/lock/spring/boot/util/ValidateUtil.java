package io.gitee.tooleek.lock.spring.boot.util;

import io.gitee.tooleek.lock.spring.boot.exception.LockValidateException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证帮助类
 *
 * @author Wujun
 * @version 1.1.0
 * @apiNote 知识改变命运，技术改变世界
 * @since 2018-12-23 15:32
 */
public class ValidateUtil {

    /**
     * @param exceptionClazz 异常类
     * @param message        提示信息
     * @return RuntimeException
     */
    private static <T extends RuntimeException> T constructException(Class<T> exceptionClazz, String message) throws LockValidateException {
        T exception;
        Constructor<T> constructor;
        try {
            constructor = exceptionClazz.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            throw new LockValidateException("缺少String为参数的构造函数", e);
        }
        try {
            exception = constructor.newInstance(message);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new LockValidateException(e.getMessage(), e);
        }
        return exception;
    }

    /**
     * 处理验证结果的私有方法，减少重复代码量
     *
     * @param result         结果本身
     * @param exceptionClass 需要抛出的异常类实例
     * @param msg            需要返回的消息
     * @param <T>            需要抛出的异常类类型
     * @return 结果
     */
    private static <T extends RuntimeException> boolean getResult(boolean result, Class<T> exceptionClass, String msg) {
        // 如果结果为false，并且需要抛出异常
        if (!result && exceptionClass != null) {
            throw constructException(exceptionClass, msg);
        }
        return result;
    }

    /**
     * 表达式如果不为真，则抛出指定的异常
     *
     * @param result         结果本身
     * @param exceptionClass 需要抛出的异常类实例
     * @param msg            需要返回的消息
     * @param <T>            需要抛出的异常类类型
     * @return 结果
     */
    public static <T extends RuntimeException> boolean isTrue(boolean result, Class<T> exceptionClass, String msg) {
        return getResult(result, exceptionClass, msg == null ? "表达式不为真" : msg);
    }

    /**
     * 判断字符串是否符合正则表达式，正则标志位使用0
     *
     * @param data           被校验的对象
     * @param expression     正则表达式
     * @param exceptionClass 抛出的异常类型，为空时则不抛出异常
     * @param msg            异常时的报错信息，如果为null，则为默认信息
     * @param <T>            异常类型必须是RuntimeException的派生类
     * @return 是否匹配
     */
    public static <T extends RuntimeException> boolean matchExpression(String data, String expression, Class<T> exceptionClass, String msg) {
        return matchExpression(data, expression, 0, exceptionClass, msg);
    }

    /**
     * 判断字符串是否符合正则表达式
     *
     * @param data           被校验的对象
     * @param expression     正则表达式
     * @param expFlag        正则表达式验证标志位，java.util.regex.Pattern中存在指定常量，默认使用0
     * @param exceptionClass 抛出的异常类型，为空时则不抛出异常
     * @param msg            异常时的报错信息，如果为null，则为默认信息
     * @param <T>            异常类型必须是RuntimeException的派生类
     * @return 是否匹配
     */
    public static <T extends RuntimeException> boolean matchExpression(String data, String expression, Integer expFlag, Class<T> exceptionClass, String msg) {
        boolean result = ValidateUtil.strNotEmpty(data, exceptionClass, "被校验字符串不可为空");
        if (!result) {
            return getResult(false, exceptionClass, msg == null ? "字符串不符合目标正则表达式规则" : msg);
        }
        result = ValidateUtil.strNotEmpty(expression, exceptionClass, "表达式不可为空");
        if (!result) {
            return getResult(false, exceptionClass, msg == null ? "字符串不符合目标正则表达式规则" : msg);
        }

        // 编译正则表达式
        Pattern pattern = Pattern.compile(expression, expFlag);
        Matcher matcher = pattern.matcher(data);
        return getResult(matcher.matches(), exceptionClass, msg == null ? "字符串不符合目标正则表达式规则" : msg);
    }

    /**
     * 非空校验
     *
     * @param data           被校验的对象
     * @param exceptionClass 抛出的异常类型，为空时则不抛出异常
     * @param msg            异常时的报错信息，如果为null，则为默认信息
     * @param <T>            异常类型必须是RuntimeException的派生类
     */
    public static <T extends RuntimeException> boolean notNull(Object data, Class<T> exceptionClass, String msg) {
        return getResult(data != null, exceptionClass, msg == null ? "内容不可为null" : msg);
    }

    /**
     * 字符串不可为空的校验
     *
     * @param data           被校验的对象
     * @param exceptionClass 抛出的异常类型，为空时则不抛出异常
     * @param msg            异常时的报错信息，如果为null，则为默认信息
     * @param <T>            异常类型必须是RuntimeException的派生类
     */
    public static <T extends RuntimeException> boolean strNotEmpty(String data, Class<T> exceptionClass, String msg) {
        return getResult(strNotEmpty(data), exceptionClass, msg == null ? "内容不可为空" : msg);
    }

    /**
     * 字符串不可为空的校验
     *
     * @param data 要校验的数据
     * @return [true] 不为空，[false] 为空
     */
    public static boolean strNotEmpty(String data) {
        return null != data && !"".equals(data);
    }

    /**
     * 字符串不可为空的校验，空字符也会被判定为空字符串，但不会改变入参字符串本身 true使用spring的StringUtils.trimWhitespace
     *
     * @param data           被校验的对象
     * @param exceptionClass 抛出的异常类型，为空时则不抛出异常
     * @param msg            异常时的报错信息，如果为null，则为默认信息
     * @param <T>            异常类型必须是RuntimeException的派生类
     */
    public static <T extends RuntimeException> boolean strNotEmptyWithTrim(String data, Class<T> exceptionClass, String msg) {
        return getResult(strNotEmptyWithTrim(data), exceptionClass, msg == null ? "内容不可为空" : msg);
    }

    /**
     * 字符串不可为空的校验，空字符也会被判定为空字符串，但不会改变入参字符串本身
     *
     * @param data 要校验的数据
     * @return [true] 不为空，[false] 为空
     */
    public static boolean strNotEmptyWithTrim(String data) {
        return null != data && !"".equals(data.trim());
    }

    /**
     * 校验整数是否大于某个值
     *
     * @param data           被校验的数据
     * @param destNum        被比较的目标值
     * @param containDest    是否包含等于目标值
     * @param exceptionClass 抛出的异常类型，为空时则不抛出异常
     * @param msg            异常时的报错信息，如果为null，则为默认信息
     * @param <T>            异常类型必须是RuntimeException的派生类
     */
    public static <T extends RuntimeException> boolean intGreaterThan(int data, int destNum, boolean containDest, Class<T> exceptionClass, String msg) {
        return longGreaterThan(data, destNum, containDest, exceptionClass, msg);
    }

    /**
     * 校验整数是否小于某个值
     *
     * @param data           被校验的数据
     * @param destNum        被比较的目标值
     * @param containDest    是否包含等于目标值
     * @param exceptionClass 抛出的异常类型，为空时则不抛出异常
     * @param msg            异常时的报错信息，如果为null，则为默认信息
     * @param <T>            异常类型必须是RuntimeException的派生类
     */
    public static <T extends RuntimeException> boolean intLessThan(int data, int destNum, boolean containDest, Class<T> exceptionClass, String msg) {
        return longLessThan(data, destNum, containDest, exceptionClass, msg);
    }

    /**
     * 校验整数是否小于某个值
     *
     * @param data           被校验的数据
     * @param destNum        被比较的目标值
     * @param containDest    是否包含等于目标值
     * @param exceptionClass 抛出的异常类型，为空时则不抛出异常
     * @param msg            异常时的报错信息，如果为null，则为默认信息
     * @param <T>            异常类型必须是RuntimeException的派生类
     */
    public static <T extends RuntimeException> boolean longLessThan(long data, long destNum, boolean containDest, Class<T> exceptionClass, String msg) {
        boolean result = containDest ? data <= destNum : data < destNum;
        String remind = result ? "值应小于等于" : "值应小于";
        return getResult(result, exceptionClass, msg == null ? remind + destNum : msg);
    }

    /**
     * 校验整数是否大于某个值
     *
     * @param data           被校验的数据
     * @param destNum        被比较的目标值
     * @param containDest    是否包含等于目标值
     * @param exceptionClass 抛出的异常类型，为空时则不抛出异常
     * @param msg            异常时的报错信息，如果为null，则为默认信息
     * @param <T>            异常类型必须是RuntimeException的派生类
     */
    public static <T extends RuntimeException> boolean longGreaterThan(long data, long destNum, boolean containDest, Class<T> exceptionClass, String msg) {
        boolean result = containDest ? data >= destNum : data > destNum;
        String remind = result ? "值应大于等于" : "值应大于";
        return getResult(result, exceptionClass, msg == null ? remind + destNum : msg);
    }

    /**
     * map不可为空的校验
     *
     * @param data           被校验的对象
     * @param exceptionClass 抛出的异常类型，为空时则不抛出异常
     * @param msg            异常时的报错信息，如果为null，则为默认信息
     * @param <T>            异常类型必须是RuntimeException的派生类
     */
    public static <T extends RuntimeException> boolean mapNotEmpty(Map<Object, T> data, Class<T> exceptionClass, String msg) {
        boolean result = null != data && data.size() > 0;
        return getResult(result, exceptionClass, msg == null ? "字典不可为空" : msg);
    }

    /**
     * collection不可为空的校验
     *
     * @param data           被校验的对象
     * @param exceptionClass 抛出的异常类型，为空时则不抛出异常
     * @param msg            异常时的报错信息，如果为null，则为默认信息
     * @param <T>            异常类型必须是RuntimeException的派生类
     */
    public static <T extends RuntimeException> boolean collectionNotEmpty(Collection<T> data, Class<T> exceptionClass, String msg) {
        return getResult(collectionNotEmpty(data), exceptionClass, msg == null ? "集合不可为空" : msg);
    }

    /**
     * collection不可为空的校验
     *
     * @param data 要校验的数据
     * @return 校验结果
     */
    public static boolean collectionNotEmpty(Collection<?> data) {
        return data != null && data.size() > 0;
    }

}
