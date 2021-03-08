package org.typroject.tyboot.core.restful.auth;

import org.springframework.web.method.HandlerMethod;
import org.typroject.tyboot.core.auth.face.model.SsoSessionsModel;

/**
 * Created by yaohelang on 2018/6/27.
 */
public interface AuthWithSessionHandler {


    /**
     * 刷新session之后执行
     * @param ssoSessionsModel
     * @param handlerMethod
     * @param token
     * @param appKey
     * @param product
     * @throws Exception
     */
    void doAuth(SsoSessionsModel ssoSessionsModel, HandlerMethod handlerMethod, String token, String appKey, String product);
}
