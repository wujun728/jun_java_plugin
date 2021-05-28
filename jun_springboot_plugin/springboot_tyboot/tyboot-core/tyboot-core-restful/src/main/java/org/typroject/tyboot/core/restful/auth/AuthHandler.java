package org.typroject.tyboot.core.restful.auth;

import org.springframework.web.method.HandlerMethod;

/**
 * Created by yaohelang on 2018/6/27.
 */
public interface AuthHandler {


    /**
     * 刷新session之前执行
     * @param handlerMethod
     * @param token
     * @param appKey
     * @param product
     * @return 默认返回false，如果返回真，则会跳过剩下校验过程。
     */
    Boolean doAuth(HandlerMethod handlerMethod, String token, String appKey, String product);
}
