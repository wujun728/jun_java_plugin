package com.chentongwei.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 字符串工具类
 */
public final class StringUtil {

    private StringUtil() {}

    /**
     * 判断字符串是否为空
     *
     * @param str：字符串
     */
    public static final boolean isNull(final String str) {
        if (StringUtils.isEmpty(str) || StringUtils.isBlank(str)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 获取6位邮箱随机验证码
     *
     * @return
     */
    public static final String getEmailCode() {
        return String.valueOf(Math.round((Math.random() * 9 + 1) * 100000));
    }
}
