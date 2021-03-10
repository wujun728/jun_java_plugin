package com.monkeyk.sos.service.business;

import com.monkeyk.sos.domain.dto.UserJsonDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Collection;

/**
 * 2018/10/14
 *
 * @author Shengzhao Li
 * @since 1.0
 */
public class OauthUserJsonDtoLoader {

    private OAuth2Authentication oAuth2Authentication;

    public OauthUserJsonDtoLoader(OAuth2Authentication oAuth2Authentication) {
        this.oAuth2Authentication = oAuth2Authentication;
    }

    public UserJsonDto load() {

        UserJsonDto userJsonDto = new UserJsonDto();
        userJsonDto.setUsername(oAuth2Authentication.getName());

        final Collection<GrantedAuthority> authorities = oAuth2Authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            userJsonDto.getPrivileges().add(authority.getAuthority());
        }

        return userJsonDto;
    }
}
