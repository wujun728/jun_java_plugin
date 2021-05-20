package com.monkeyk.sos.service.business;

import com.monkeyk.sos.service.dto.AccessTokenDto;
import com.monkeyk.sos.web.context.SOSContextHolder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.Assert;

import java.util.Map;

import static org.springframework.security.oauth2.common.util.OAuth2Utils.CLIENT_ID;
import static org.springframework.security.oauth2.common.util.OAuth2Utils.GRANT_TYPE;
import static org.springframework.security.oauth2.common.util.OAuth2Utils.SCOPE;

/**
 * 2019/7/5
 *
 * @author Wujun
 * @see org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
 * @since 2.0.1
 */
public abstract class InlineAccessTokenInvoker implements InitializingBean {


    private static final Logger LOG = LoggerFactory.getLogger(InlineAccessTokenInvoker.class);


    protected transient AuthenticationManager authenticationManager = SOSContextHolder.getBean(AuthenticationManager.class);

    protected transient AuthorizationServerTokenServices tokenServices = SOSContextHolder.getBean(AuthorizationServerTokenServices.class);
    ;
    protected transient ClientDetailsService clientDetailsService = SOSContextHolder.getBean(ClientDetailsService.class);


    public InlineAccessTokenInvoker() {
    }


    /**
     * 根据不同的 params 生成 AccessTokenDto
     * <p>
     * 适合以下几类 grant_type:
     * password
     * refresh_token
     * client_credentials
     *
     * @param params Params Map
     * @return AccessTokenDto instance
     */
    public AccessTokenDto invoke(Map<String, String> params) {

        if (params == null || params.isEmpty()) {
            throw new IllegalStateException("Null or empty params");
        }

        String clientId = validateParams(params);

        final ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            LOG.warn("Not found ClientDetails by clientId: {}", clientId);
            return null;
        }

        OAuth2RequestFactory oAuth2RequestFactory = createOAuth2RequestFactory();
        TokenGranter tokenGranter = getTokenGranter(oAuth2RequestFactory);
        LOG.debug("Use TokenGranter: {}", tokenGranter);

        TokenRequest tokenRequest = oAuth2RequestFactory.createTokenRequest(params, clientDetails);
        final OAuth2AccessToken oAuth2AccessToken = tokenGranter.grant(getGrantType(params), tokenRequest);

        if (oAuth2AccessToken == null) {
            LOG.warn("TokenGranter: {} grant OAuth2AccessToken null", tokenGranter);
            return null;
        }
        AccessTokenDto accessTokenDto = new AccessTokenDto(oAuth2AccessToken);
        LOG.debug("Invoked accessTokenDto: {}", accessTokenDto);
        return accessTokenDto;
    }


    /**
     * 校验各类 grant_type时 需要的参数
     *
     * @param params params
     * @return clientId
     */
    protected String validateParams(Map<String, String> params) {
        //validate client_id
        String clientId = params.get(CLIENT_ID);
        if (StringUtils.isBlank(clientId)) {
            throw new IllegalStateException("Null or empty '" + CLIENT_ID + "' from params");
        }

        //validate grant_type
        final String grantType = params.get(GRANT_TYPE);
        if (StringUtils.isBlank(grantType)) {
            throw new IllegalStateException("Null or empty '" + GRANT_TYPE + "' from params");
        }

        //validate scope
        final String scope = params.get(SCOPE);
        if (StringUtils.isBlank(scope)) {
            throw new IllegalStateException("Null or empty '" + SCOPE + "' from params");
        }

        return clientId;
    }


    /**
     * Get grant_type from params
     *
     * @param params Map
     * @return Grant Type
     */
    protected String getGrantType(Map<String, String> params) {
        return params.get(GRANT_TYPE);
    }


    /**
     * Get TokenGranter implement
     *
     * @return TokenGranter
     */
    protected abstract TokenGranter getTokenGranter(OAuth2RequestFactory oAuth2RequestFactory);

    /**
     * Create  OAuth2RequestFactory
     *
     * @return OAuth2RequestFactory instance
     */
    protected OAuth2RequestFactory createOAuth2RequestFactory() {
        return new DefaultOAuth2RequestFactory(this.clientDetailsService);
    }


    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setTokenServices(AuthorizationServerTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.authenticationManager, "authenticationManager is null");
        Assert.notNull(this.tokenServices, "tokenServices is null");

        Assert.notNull(this.clientDetailsService, "clientDetailsService is null");
    }
}
