package org.typroject.tyboot.api.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.typroject.tyboot.core.auth.authentication.LoginAuthenticator;
import org.typroject.tyboot.core.auth.enumeration.AuthType;
import org.typroject.tyboot.core.auth.enumeration.IdType;
import org.typroject.tyboot.core.auth.enumeration.ProvidedAuthType;
import org.typroject.tyboot.core.auth.face.model.AuthModel;
import org.typroject.tyboot.core.auth.face.model.IdPasswordAuthModel;
import org.typroject.tyboot.core.auth.face.model.LoginInfoModel;
import org.typroject.tyboot.core.foundation.enumeration.UserType;
import org.typroject.tyboot.core.restful.doc.TycloudOperation;
import org.typroject.tyboot.core.restful.doc.TycloudResource;
import org.typroject.tyboot.core.restful.utils.ResponseHelper;
import org.typroject.tyboot.core.restful.utils.ResponseModel;

import java.util.HashMap;


/**
 * Created by magintursh on 2017-05-03.
 */
@TycloudResource(module = "auth",value = "agency")
@RequestMapping(value = "/v1/auth/agency")
@Api(tags = "auth-机构用户登录验证",value = "value")
@RestController
public class AgencyAuthResource {
    private final Logger logger = LogManager.getLogger(AgencyAuthResource.class) ;


    @Autowired
    LoginAuthenticator loginAuthenticator;



    @TycloudOperation( ApiLevel = UserType.ANONYMOUS,needAuth = false)
    @ApiOperation(value="机构用户名密码登录")
    @RequestMapping(value = "/agency/idpassword", method = RequestMethod.POST)
    public ResponseModel<LoginInfoModel> idPasswordAuthForAgency(@RequestBody IdPasswordAuthModel model) throws Exception
    {
        return  this.doAuthenticate(IdType.userName, AuthType.ID_PASSWORD, UserType.AGENCY,model);
    }

    private ResponseModel doAuthenticate(IdType idType, ProvidedAuthType authType, UserType userType, AuthModel authModel) throws Exception
    {
        HashMap<String, Object> result = loginAuthenticator.authLogin(idType, authType, userType, authModel);
        return ResponseHelper.buildResponse(result);
    }

}