package com.monkeyk.sos.service.business;

import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;

/**
 * 2019/7/5
 * <p>
 * <p>
 * grant_type = password
 *
 * @author Wujun
 * @since 2.0.1
 */
public class PasswordInlineAccessTokenInvoker extends InlineAccessTokenInvoker {


    public PasswordInlineAccessTokenInvoker() {
    }

    @Override
    protected TokenGranter getTokenGranter(OAuth2RequestFactory oAuth2RequestFactory) {
        return new ResourceOwnerPasswordTokenGranter(this.authenticationManager, this.tokenServices, this.clientDetailsService, oAuth2RequestFactory);
    }



}
