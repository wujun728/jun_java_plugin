package com.lhb.lhbauth.jwt.demo.authentication.openid;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Wujun
 * @description
 * @date 2019/1/8 0008 14:40
 */
public class OpenIdAuthenticationToken  extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 420L;

    /**
     * openid
     */
    private final Object principal;

    /**
     * 服务提供上ID
     */
    private String providerId;


    OpenIdAuthenticationToken(String openId, String providerId) {
        super(null);
        this.principal =openId;
        this.providerId = providerId;
        setAuthenticated(false);
    }

    OpenIdAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal =principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }


}
