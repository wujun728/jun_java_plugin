package cn.com.bluemoon.utils;

import java.util.Collection;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cn.com.bluemoon.common.exception.AssertException;

/**
 * 断言工具
 * Created by Guoqing on 2016/10/22.
 */
public class AssertUtil extends Assert {
    
	/**
	 * 判布尔型
	 * @param expression
	 * @param code
	 * @param message
	 */
    public static void isTrue(boolean expression, int code, String message) {
		if (!expression) {
			throw new AssertException(code,message);
		}
	}

    /**
     * 判对象为空
     * @param object
     * @param code
     * @param message
     */
	public static void isNull(Object object, int code, String message) {
		if (object != null) {
			throw new AssertException(code,message);
		}
	}

	/**
	 * 判对象非空
	 * @param object
	 * @param code
	 * @param message
	 */
	public static void notNull(Object object, int code, String message) {
		if (object == null) {
			throw new AssertException(code,message);
		}
	}

	/**
	 * 判字符串是否有值
	 * @param text
	 * @param code
	 * @param message
	 */
	public static void hasLength(String text, int code, String message) {
		if (!StringUtils.hasLength(text)) {
			throw new AssertException(code,message);
		}
	}

	/**
	 * 判集合是否为空
	 * @param collection
	 * @param code
	 * @param message
	 */
	public static void notEmpty(Collection<?> collection, int code, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new AssertException(code,message);
		}
	}
    
}