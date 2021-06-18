package com.lhb.lhbauth.jwt.demo.web.controller;

import com.lhb.lhbauth.jwt.demo.common.ResultModel;
import com.lhb.lhbauth.jwt.demo.constants.FromLoginConstant;
import com.lhb.lhbauth.jwt.demo.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @description 处理登录和授权的控制器
 * @date 2018/12/26 0026 17:31
 */
@Slf4j
@Controller
@SessionAttributes({"authorizationRequest"})
public class AuthPageController {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;


    /**
     * 当用户没登录的时候，会经过这个请求，在这个请求中可以处理一些逻辑
     *
     * @param request  request
     * @param response response
     * @return ResultModel
     * @throws IOException IOException
     */
    @RequestMapping(FromLoginConstant.LOGIN_PAGE)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResultModel requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (null != savedRequest) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是:" + targetUrl);
            redirectStrategy.sendRedirect(request, response, FromLoginConstant.AFTER_LOGING_PAGE);
        }
        //如果访问的是接口资源
        return ResultModel.fail(401, "访问的服务需要身份认证，请引导用户到登录页");
    }


    @RequestMapping(FromLoginConstant.AFTER_LOGING_PAGE)
    public String login() {
        return securityProperties.getOauthLogin().getOauthLogin();
    }


    /**
     * 自定义授权页面，注意：一定要在类上加@SessionAttributes({"authorizationRequest"})
     *
     * @param model   model
     * @param request request
     * @return String
     * @throws Exception Exception
     */
    @RequestMapping("/oauth/confirm_access")
    public String getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes") : request.getAttribute("scopes"));
        List<String> scopeList = new ArrayList<>();
        if (scopes != null) {
            scopeList.addAll(scopes.keySet());
        }
        model.put("scopeList", scopeList);
        return securityProperties.getOauthLogin().getOauthGrant();
    }



    @GetMapping("/social/binding")
    public String binding(){
        return "/demo-binding";
    }

}
