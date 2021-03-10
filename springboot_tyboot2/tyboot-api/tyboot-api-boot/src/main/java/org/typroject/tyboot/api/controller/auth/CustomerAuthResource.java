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
import org.typroject.tyboot.core.auth.face.model.IdPasswordAuthModel;
import org.typroject.tyboot.core.foundation.enumeration.UserType;
import org.typroject.tyboot.core.restful.doc.TycloudOperation;
import org.typroject.tyboot.core.restful.doc.TycloudResource;
import org.typroject.tyboot.core.restful.utils.ResponseHelper;
import org.typroject.tyboot.core.restful.utils.ResponseModel;


/**
 * Created by magintursh on 2017-05-03.
 */
@TycloudResource(module = "auth",value = "customer")
@RequestMapping(value = "/v1/auth/customer")
@Api(tags = "auth-公网用户登录验证")
@RestController
public class CustomerAuthResource {
    private final Logger logger = LogManager.getLogger(CustomerAuthResource.class) ;


    @Autowired
    private LoginAuthenticator loginAuthenticator;


    @TycloudOperation( ApiLevel = UserType.ANONYMOUS,needAuth = false)
    @ApiOperation(value="公网用户名密码登录")
    @RequestMapping(value = "/public/idpassword", method = RequestMethod.POST)
    public ResponseModel idPasswordAuthForPublic(@RequestBody IdPasswordAuthModel model) throws Exception
    {
        return   ResponseHelper.buildResponse(
                loginAuthenticator.authLogin(IdType.userName, AuthType.ID_PASSWORD, UserType.PUBLIC,model));
    }









}