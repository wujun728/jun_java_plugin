package com.lhb.lhbauth.jwt.demo.authentication.openid;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Wujun
 * @description
 * @date 2019/1/8 0008 14:52
 */
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String openIdParameter = "openId";
    private String providerIdParameter = "providerId";

    /**
     * 是否只处理post请求
     */
    private boolean postOnly = true;


    public OpenIdAuthenticationFilter(){
        //要拦截的请求
        super(new AntPathRequestMatcher("/authentication/openid", "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String openId = this.obtainOpenId(request);
            String providerId = this.obtainProvider(request);
            if (openId == null) {
                openId = "";
            }

            if(providerId == null){
                providerId = "";
            }


            openId = openId.trim();
            providerId = providerId.trim();


            //把手机号传进SmsCodeAuthenticationToken
            OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openId, providerId);
            this.setDetails(request, authRequest);
            //调用AuthenticationManager
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }









    /**
     * 获取openId
     *
     * @param request request
     * @return String
     */
    private String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(this.openIdParameter);
    }

    private String obtainProvider(HttpServletRequest request){
        return request.getParameter(this.providerIdParameter);
    }


    private void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setOpenIdParameter(String openIdParameter) {
        Assert.hasText(openIdParameter, "Username parameter must not be empty or null");
        this.openIdParameter = openIdParameter;
    }


    public final String getOpenIdParameter() {
        return this.openIdParameter;
    }


    public String getProviderIdParameter() {
        return providerIdParameter;
    }

    public void setProviderIdParameter(String providerIdParameter) {
        this.providerIdParameter = providerIdParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
