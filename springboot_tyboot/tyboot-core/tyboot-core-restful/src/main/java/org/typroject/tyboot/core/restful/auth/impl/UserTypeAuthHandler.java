package org.typroject.tyboot.core.restful.auth.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.typroject.tyboot.core.auth.exception.AuthException;
import org.typroject.tyboot.core.auth.face.model.SsoSessionsModel;
import org.typroject.tyboot.core.foundation.enumeration.UserType;
import org.typroject.tyboot.core.restful.auth.AuthWithSessionHandler;
import org.typroject.tyboot.core.restful.doc.TycloudOperation;

/**
 * 用户类型验证，
 * Created by yaohelang on 2018/6/27.
 */
@Component
public class UserTypeAuthHandler implements AuthWithSessionHandler {


    public void doAuth(SsoSessionsModel ssoSessionsModel, HandlerMethod handlerMethod,
                       String token, String appKey, String product) {

        TycloudOperation tycloudOperation = handlerMethod.getMethodAnnotation(TycloudOperation.class);
        UserType userType = UserType.valueOf(ssoSessionsModel.getUserType());

        if (userType.getLevel() < tycloudOperation.ApiLevel().getLevel()) {
            throw new AuthException("用户权限不够.");
        }
    }
}

