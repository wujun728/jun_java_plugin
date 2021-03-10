package com.demo.weixin.utils;

import com.demo.weixin.exception.WeixinException;
import org.apache.commons.lang3.StringUtils;

/**
 * 微信服务参数校验
 *
 * @since 1.0
 */
public class VerifyParamsUtils {

    private VerifyParamsUtils() {

    }

    public static void notNull(Object object, String message) throws WeixinException {
        if (object == null) {
            throw new WeixinException(-1, message);
        }
    }

    public static void hasText(String text, String message) throws WeixinException {
        if (StringUtils.isBlank(text)) {
            throw new WeixinException(-1, message);
        }
    }

    public static void isTrue(boolean expression, String message) throws WeixinException {
        if (!expression) {
            throw new WeixinException(-1, message);
        }
    }

}
