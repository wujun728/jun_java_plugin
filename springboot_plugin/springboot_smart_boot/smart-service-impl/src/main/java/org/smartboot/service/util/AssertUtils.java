package org.smartboot.service.util;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.smartboot.shared.SmartException;
import org.springframework.util.CollectionUtils;

/**
 * 断言工具类
 * 
 * @author Wujun
 * @version AssertUtils.java, v 0.1 2015年11月4日 下午3:22:59 Seer Exp.
 */
public class AssertUtils {
	/**
	 * <p>
	 * 若被检查对象为空白字符串,则抛异常
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 * @since 2.0
	 */
	public static void isNotBlank(String str, String msg) {
		isNotBlank(str, msg, null);
	}

	public static void isNotBlank(String str, String msg, Level level) {
		isNotBlank(str, msg, msg, level);
	}

	/**
	 * @param str
	 * @param msg
	 * @param logMsg
	 *            记录日志的信息
	 * @param level
	 */
	public static void isNotBlank(String str, String msg, String logMsg, Level level) {
		if (StringUtils.isBlank(str)) {
			throw new SmartException(level, msg, logMsg);
		}
	}

	/**
	 * obj对象不为null，若obj为null则抛异常
	 * 
	 * @param obj
	 * @param message
	 *            异常信息
	 */
	public static void isNotNull(Object obj, String message) {
		isNotNull(obj, message, null);
	}

	public static void isNotNull(Object obj, String message, Level level) {
		isNotNull(obj, message, message, level);
	}

	public static void isNotNull(Object obj, String message, String logMessage, Level level) {
		if (obj == null) {
			throw new SmartException(level, message, logMessage);
		}
	}

	/**
	 * Assert a boolean expression, throwing {@code IllegalArgumentException} if
	 * the test result is {@code false}.
	 * 
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0, "The value must be greater than zero");
	 * </pre>
	 * 
	 * @param expression
	 *            a boolean expression
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws SmartException
	 *             if expression is {@code false}
	 */
	public static void isTrue(boolean flag, String message) {
		isTrue(flag, message, null);
	}

	public static void isTrue(boolean flag, String message, Level level) {
		isTrue(flag, message, message, level);
	}

	public static void isTrue(boolean flag, String message, String logMessage, Level level) {
		if (!flag) {
			throw new SmartException(level, message, logMessage);
		}
	}

	public static void isFalse(boolean flag, String message) {
		isFalse(flag, message, null);
	}

	public static void isFalse(boolean flag, String message, Level level) {
		isFalse(flag, message, message, level);
	}

	public static void isFalse(boolean flag, String message, String logMessage, Level level) {
		if (flag) {
			throw new SmartException(level, message, logMessage);
		}
	}

	public static <E> void notEmpty(Collection<E> collection, String message) {
		notEmpty(collection, message, null);
	}

	public static <E> void notEmpty(Collection<E> collection, String message, Level level) {
		notEmpty(collection, message, message, level);
	}

	public static <E> void notEmpty(Collection<E> collection, String message, String logMessage, Level level) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new SmartException(level, message, logMessage);
		}
	}
}
