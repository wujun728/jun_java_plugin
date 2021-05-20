/*
 * Copyright (c) 2015 MONKEYK Information Technology Co. Ltd
 * www.monkeyk.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * MONKEYK Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with MONKEYK Information Technology Co. Ltd.
 */
package com.monkeyk.sos.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * 2016/3/8
 * <p/>
 * Restful OAuth API
 * <p/>
 * 扩展默认的OAuth 功能,  提供 Restful API,
 * 可用于在获取access_token时调用
 *
 * @author Wujun
 * @see org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
 * @see org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint
 */
@Controller
public class OAuthRestController implements InitializingBean, ApplicationContextAware {


    private static final Logger LOG = LoggerFactory.getLogger(OAuthRestController.class);

    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthorizationServerTokenServices tokenServices;
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private ResourceServerTokenServices resourceServerTokenServices;


    private AuthenticationManager authenticationManager;

    private OAuth2RequestFactory oAuth2RequestFactory;

    private OAuth2RequestValidator oAuth2RequestValidator = new DefaultOAuth2RequestValidator();
    private WebResponseExceptionTranslator providerExceptionHandler = new DefaultWebResponseExceptionTranslator();

    private AccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();


    /**
     * Verify access_token
     * <p/>
     * Ext. from CheckTokenEndpoint
     *
     * @param value    token
     * @param clientId client_id
     * @return Map
     * @see org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint
     * @since 1.0
     */
    @RequestMapping(value = "/oauth/check_token", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, ?> checkToken(@RequestParam("token") String value, @RequestParam("client_id") String clientId) {

        OAuth2AccessToken token = resourceServerTokenServices.readAccessToken(value);
        if (token == null) {
            throw new InvalidTokenException("Token was not recognised");
        }

        if (token.isExpired()) {
            throw new InvalidTokenException("Token has expired");
        }

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new InvalidClientException("client_id was not recognised");
        }

        OAuth2Authentication authentication = resourceServerTokenServices.loadAuthentication(token.getValue());
        final String authClientId = authentication.getOAuth2Request().getClientId();
        if (!clientId.equals(authClientId)) {
            throw new InvalidClientException("Given client ID does not match authenticated client");
        }
        return accessTokenConverter.convertAccessToken(token, authentication);
    }


    /**
     * Restful API for get access_token
     *
     * @param parameters Map
     * @return OAuth2AccessToken
     */
    @RequestMapping(value = "/oauth/rest_token", method = RequestMethod.POST)
    @ResponseBody
    public OAuth2AccessToken postAccessToken(@RequestBody Map<String, String> parameters) {


        String clientId = getClientId(parameters);
        ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);

        //validate client_secret
        String clientSecret = getClientSecret(parameters);
        if (clientSecret == null || clientSecret.equals("")) {
            throw new InvalidClientException("Bad client credentials");
        } else {
            if (!clientSecret.equals(authenticatedClient.getClientSecret())) {
                throw new InvalidClientException("Bad client credentials");
            }
        }

        TokenRequest tokenRequest = oAuth2RequestFactory.createTokenRequest(parameters, authenticatedClient);

        if (clientId != null && !"".equals(clientId)) {
            // Only validate the client details if a client authenticated during this
            // request.
            if (!clientId.equals(tokenRequest.getClientId())) {
                // double check to make sure that the client ID in the token request is the same as that in the
                // authenticated client
                throw new InvalidClientException("Given client ID does not match authenticated client");
            }
        }

        oAuth2RequestValidator.validateScope(tokenRequest, authenticatedClient);

        final String grantType = tokenRequest.getGrantType();
        if (!StringUtils.hasText(grantType)) {
            throw new InvalidRequestException("Missing grant type");
        }
        if ("implicit".equals(grantType)) {
            throw new InvalidGrantException("Implicit grant type not supported from token endpoint");
        }

        if (isAuthCodeRequest(parameters)) {
            // The scope was requested or determined during the authorization step
            if (!tokenRequest.getScope().isEmpty()) {
                LOG.debug("Clearing scope of incoming token request");
                tokenRequest.setScope(Collections.<String>emptySet());
            }
        }


        if (isRefreshTokenRequest(parameters)) {
            // A refresh token has its own default scopes, so we should ignore any added by the factory here.
            tokenRequest.setScope(OAuth2Utils.parseParameterList(parameters.get(OAuth2Utils.SCOPE)));
        }

        OAuth2AccessToken token = getTokenGranter(grantType).grant(grantType, tokenRequest);
        if (token == null) {
            throw new UnsupportedGrantTypeException("Unsupported grant type: " + grantType);
        }


        return token;

    }

    protected TokenGranter getTokenGranter(String grantType) {

        if ("authorization_code".equals(grantType)) {
            return new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, this.oAuth2RequestFactory);
        } else if ("password".equals(grantType)) {
            return new ResourceOwnerPasswordTokenGranter(getAuthenticationManager(), tokenServices, clientDetailsService, this.oAuth2RequestFactory);
        } else if ("refresh_token".equals(grantType)) {
            return new RefreshTokenGranter(tokenServices, clientDetailsService, this.oAuth2RequestFactory);
        } else if ("client_credentials".equals(grantType)) {
            return new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, this.oAuth2RequestFactory);
        } else if ("implicit".equals(grantType)) {
            return new ImplicitTokenGranter(tokenServices, clientDetailsService, this.oAuth2RequestFactory);
        } else {
            throw new UnsupportedGrantTypeException("Unsupport grant_type: " + grantType);
        }
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<OAuth2Exception> handleException(Exception e) throws Exception {
        LOG.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        return getExceptionTranslator().translate(e);
    }

    @ExceptionHandler(ClientRegistrationException.class)
    public ResponseEntity<OAuth2Exception> handleClientRegistrationException(Exception e) throws Exception {
        LOG.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        return getExceptionTranslator().translate(new BadClientCredentialsException());
    }

    @ExceptionHandler(OAuth2Exception.class)
    public ResponseEntity<OAuth2Exception> handleException(OAuth2Exception e) throws Exception {
        LOG.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        return getExceptionTranslator().translate(e);
    }


    /**
     * Handle InvalidTokenException
     *
     * @param e Exception
     * @return ResponseEntity
     * @throws Exception Exception
     * @see org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint#handleException(Exception)
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<OAuth2Exception> handleInvalidTokenException(InvalidTokenException e) throws Exception {
        LOG.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        return getExceptionTranslator().translate(e);
    }


    private boolean isRefreshTokenRequest(Map<String, String> parameters) {
        return "refresh_token".equals(parameters.get(OAuth2Utils.GRANT_TYPE)) && parameters.get("refresh_token") != null;
    }

    private boolean isAuthCodeRequest(Map<String, String> parameters) {
        return "authorization_code".equals(parameters.get(OAuth2Utils.GRANT_TYPE)) && parameters.get("code") != null;
    }


    protected String getClientId(Map<String, String> parameters) {
        return parameters.get(OAuth2Utils.CLIENT_ID);
    }

    protected String getClientSecret(Map<String, String> parameters) {
        return parameters.get("client_secret");
    }


    private AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Assert.state(clientDetailsService != null, "ClientDetailsService must be provided");
        Assert.state(authenticationManager != null, "AuthenticationManager must be provided");

        oAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    protected WebResponseExceptionTranslator getExceptionTranslator() {
        return providerExceptionHandler;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.authenticationManager == null) {
            this.authenticationManager = (AuthenticationManager) applicationContext.getBean("authenticationManager");
        }
    }
}
