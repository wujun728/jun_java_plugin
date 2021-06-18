package com.monkeyk.sos.service.business;

import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;

/**
 * 2019/7/5
 * <p>
 * <p>
 * grant_type = refresh_token
 *
 * @author Wujun
 * @since 2.0.1
 */
public class RefreshTokenInlineAccessTokenInvoker extends InlineAccessTokenInvoker {


    public RefreshTokenInlineAccessTokenInvoker() {
    }

    @Override
    protected TokenGranter getTokenGranter(OAuth2RequestFactory oAuth2RequestFactory) {
        return new RefreshTokenGranter(this.tokenServices, this.clientDetailsService, oAuth2RequestFactory);
    }


}
