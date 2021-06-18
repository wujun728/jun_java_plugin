package org.typroject.tyboot.core.auth.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.auth.enumeration.IdType;
import org.typroject.tyboot.core.auth.exception.AuthException;
import org.typroject.tyboot.core.auth.face.model.AuthModel;
import org.typroject.tyboot.core.auth.face.model.IdPasswordAuthModel;
import org.typroject.tyboot.core.auth.face.model.LoginInfoModel;
import org.typroject.tyboot.core.auth.face.service.LoginInfoService;
import org.typroject.tyboot.core.foundation.constans.PropertyValueConstants;
import org.typroject.tyboot.core.foundation.enumeration.UserType;
import org.typroject.tyboot.core.foundation.utils.Encrypt;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

@Component("idPasswordLoginAuthenticator")
public class IdPasswordLoginAuthenticator implements LoginAuthenticatorHandler {

    private static final Logger logger = LoggerFactory.getLogger(IdPasswordLoginAuthenticator.class);


    @Autowired
    private LoginInfoService loginInfoService;


    
    public LoginInfoModel doAuthenticate(IdType idType, UserType userType, AuthModel authModel){

        IdPasswordAuthModel idPasswordAuthModel = (IdPasswordAuthModel) authModel;
        String loginId = idPasswordAuthModel.getLoginId();
        String password = idPasswordAuthModel.getPassword();

        if(ValidationUtil.isEmpty(idType)
                ||ValidationUtil.isEmpty(userType)
                ||ValidationUtil.isEmpty(loginId)
                ||ValidationUtil.isEmpty(password))
        {
            throw new AuthException("登录信息不能为空.");
        }

        LoginInfoModel loginInfoModel = this.loginInfoService.selectByLoginId(loginId);


        if(ValidationUtil.isEmpty(loginInfoModel))
            throw new AuthException("找不到用户信息.");

        if(PropertyValueConstants.LOCK_STATUS_LOCK.equals(loginInfoModel.getLockStatus()))
        {
            throw new AuthException("当前账号已经禁用,请联系管理员");
        }
        else if(ValidationUtil.isEmpty(loginInfoModel)
                || !loginInfoModel.getPassword().equals(Encrypt.md5ForAuth(password, loginInfoModel.getSalt())))
        {
            throw new AuthException("用户名或密码有误");
        }
        return loginInfoModel;

    }
}
