package com.jun.plugin.oauth.server.web.oauth;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jun.plugin.oauth.server.service.OauthService;
import com.jun.plugin.oauth.server.service.dto.OauthClientDetailsDto;

/**
 * @author Wujun
 */
@Component
public class OauthClientDetailsDtoValidator implements Validator {


    @Autowired
    private OauthService oauthService;

    @Override
    public boolean supports(Class<?> clazz) {
        return OauthClientDetailsDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OauthClientDetailsDto clientDetailsDto = (OauthClientDetailsDto) target;

        validateClientId(clientDetailsDto, errors);
        validateClientSecret(clientDetailsDto, errors);

        validateGrantTypes(clientDetailsDto, errors);
    }

    private void validateGrantTypes(OauthClientDetailsDto clientDetailsDto, Errors errors) {
        final String grantTypes = clientDetailsDto.getAuthorizedGrantTypes();
        if (StringUtils.isEmpty(grantTypes)) {
            errors.rejectValue("authorizedGrantTypes", null, "grant_type(s) is required");
            return;
        }

        if ("refresh_token".equalsIgnoreCase(grantTypes)) {
            errors.rejectValue("authorizedGrantTypes", null, "grant_type(s) 不能只是[refresh_token]");
        }
    }

    private void validateClientSecret(OauthClientDetailsDto clientDetailsDto, Errors errors) {
        final String clientSecret = clientDetailsDto.getClientSecret();
        if (StringUtils.isEmpty(clientSecret)) {
            errors.rejectValue("clientSecret", null, "client_secret is required");
            return;
        }

        if (clientSecret.length() < 8) {
            errors.rejectValue("clientSecret", null, "client_secret 长度至少8位");
        }
    }

    private void validateClientId(OauthClientDetailsDto clientDetailsDto, Errors errors) {
        final String clientId = clientDetailsDto.getClientId();
        if (StringUtils.isEmpty(clientId)) {
            errors.rejectValue("clientId", null, "client_id is required");
            return;
        }

        if (clientId.length() < 5) {
            errors.rejectValue("clientId", null, "client_id 长度至少5位");
            return;
        }

        OauthClientDetailsDto clientDetailsDto1 = oauthService.loadOauthClientDetailsDto(clientId);
        if (clientDetailsDto1 != null) {
            errors.rejectValue("clientId", null, "client_id [" + clientId + "] 已存在");
        }

    }
}