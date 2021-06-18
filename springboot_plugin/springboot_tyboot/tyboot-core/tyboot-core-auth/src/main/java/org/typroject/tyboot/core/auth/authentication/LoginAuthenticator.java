package org.typroject.tyboot.core.auth.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.typroject.tyboot.core.auth.enumeration.IdType;
import org.typroject.tyboot.core.auth.enumeration.ProvidedAuthType;
import org.typroject.tyboot.core.auth.exception.AuthException;
import org.typroject.tyboot.core.auth.face.model.AuthModel;
import org.typroject.tyboot.core.auth.face.model.LoginHistoryModel;
import org.typroject.tyboot.core.auth.face.model.LoginInfoModel;
import org.typroject.tyboot.core.auth.face.model.SsoSessionsModel;
import org.typroject.tyboot.core.auth.face.service.LoginHistoryService;
import org.typroject.tyboot.core.auth.face.service.SsoSessionsService;
import org.typroject.tyboot.core.foundation.constans.CoreConstans;
import org.typroject.tyboot.core.foundation.constans.ParamsConstants;
import org.typroject.tyboot.core.foundation.constans.PropertyValueConstants;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.context.SpringContextHelper;
import org.typroject.tyboot.core.foundation.enumeration.UserType;
import org.typroject.tyboot.core.foundation.exception.BaseException;
import org.typroject.tyboot.core.foundation.utils.Bean;
import org.typroject.tyboot.core.foundation.utils.CommonUtil;
import org.typroject.tyboot.core.foundation.utils.Sequence;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by magintursh on 2017-07-17.
 */

@Component
public   class LoginAuthenticator {

    private static final Logger logger = LoggerFactory.getLogger(LoginAuthenticator.class);



    @Autowired
    SsoSessionsService ssoSessionsService;

    @Autowired
    LoginHistoryService loginHistoryService;



    private   static LoginAuthenticatorHandler getAuthenticator(ProvidedAuthType authType)  {
        return (LoginAuthenticatorHandler) SpringContextHelper.getBean(authType.getAuthenticator());
    }


    @Transactional(rollbackFor = {Exception.class, BaseException.class})
    public SsoSessionsModel createSession(IdType idType, ProvidedAuthType authType, UserType userType, AuthModel authModel)
    {
        String product = RequestContext.getProduct();
        String requestIP = RequestContext.getRequestIP();
        String userAgent = RequestContext.getUserAgent();


        if(ValidationUtil.isEmpty(product)
                || ValidationUtil.isEmpty(requestIP)
                || ValidationUtil.isEmpty(userAgent)){
            throw new AuthException("请求头信息不完整.");
        }

        SsoSessionsModel ssoSessionsModel  = new SsoSessionsModel();
        ssoSessionsModel.setActionByAgent(userAgent);
        ssoSessionsModel.setActionByIp(requestIP);
        ssoSessionsModel.setActionByProduct(product);
        ssoSessionsModel.setSessionCreateTime(new Date());
        ssoSessionsModel.setSessionExpiration(SsoSessionsService.DEFAULT_SESSION_EXPIRATION);
        ssoSessionsModel.setSessionStatus(PropertyValueConstants.SESSION_STATUS_ACTIVE);
        ssoSessionsModel.setUserType(userType.name());
        ssoSessionsModel.setToken(CommonUtil.getUUID());
        if(UserType.ANONYMOUS.equals(userType))
        {
            ssoSessionsModel.setAgencyCode(CoreConstans.CODE_SUPER_ADMIN);
            ssoSessionsModel.setLoginId(Sequence.generatorUserId());
            ssoSessionsModel.setUserId(ssoSessionsModel.getLoginId());
            ssoSessionsModel.setUserName(userType.getLabel());
        }else
        {
            LoginInfoModel loginInfoModel = getAuthenticator(authType).doAuthenticate(idType, userType, authModel);
            ssoSessionsModel.setAgencyCode(loginInfoModel.getAgencyCode());
            ssoSessionsModel.setLoginId(loginInfoModel.getLoginId());
            ssoSessionsModel.setUserId(loginInfoModel.getUserId());
           // ssoSessionsModel.setUserName(loginInfoModel.get);
            ssoSessionsModel.setUserType(userType.name());

        }

        //保存session和登陆记录
        return createLoginHistory(ssoSessionsModel);
    }



    public HashMap<String,Object> authLogin(IdType idType, ProvidedAuthType authType, UserType userType, AuthModel authModel)
    {
        SsoSessionsModel ssoSessionsModel = this.createSession(idType, authType, userType, authModel);
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put(ParamsConstants.TOKEN, ssoSessionsModel.getToken());
        result.put(ParamsConstants.EXPIRE, ssoSessionsModel.getSessionExpiration());
        result.put(ParamsConstants.USERID, ssoSessionsModel.getUserId());
        return result;
    }



    private SsoSessionsModel createLoginHistory(SsoSessionsModel ssoSessionsModel)
    {


            RequestContext.setExeUserId(ssoSessionsModel.getUserId());
            RequestContext.setAgencyCode(ssoSessionsModel.getAgencyCode());
            RequestContext.setUserType(UserType.valueOf(ssoSessionsModel.getUserType()));

            //清理旧的session
            ssoSessionsService.removeSession(ssoSessionsModel.getActionByProduct(),ssoSessionsModel.getLoginId());

             //新增登陆记录
            LoginHistoryModel loginHistory = Bean.copyExistPropertis(ssoSessionsModel,new LoginHistoryModel());
            loginHistoryService.createLoginHistory(loginHistory);
            return ssoSessionsService.createSession(ssoSessionsModel);
    }







}
