package com.jun.plugin.oauth.server.service.business;

import org.junit.Test;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;

import com.jun.plugin.oauth.server.service.business.PasswordInlineAccessTokenInvoker;
import com.jun.plugin.oauth.server.service.dto.AccessTokenDto;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * 2019/7/6
 *
 * @author Wujun
 */
public class PasswordInlineAccessTokenInvokerTest extends AbstractInlineAccessTokenInvokerTest {


    @Test
    public void invokeNormal() {

        createClientDetails();

        createUser();

        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", "password");
        params.put("scope", "read");
        params.put("username", username);
        params.put("password", password);


        PasswordInlineAccessTokenInvoker accessTokenInvoker = new PasswordInlineAccessTokenInvoker();
        final AccessTokenDto tokenDto = accessTokenInvoker.invoke(params);

        assertNotNull(tokenDto);
        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());

//        System.out.println(accessTokenDto);

    }


    @Test(expected = InvalidGrantException.class)
    public void invalidUsername() {

        createClientDetails();

        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", "password");
        params.put("scope", "read");

        params.put("username", "useraaa");
        params.put("password", "password");

        PasswordInlineAccessTokenInvoker accessTokenInvoker = new PasswordInlineAccessTokenInvoker();
        final AccessTokenDto tokenDto = accessTokenInvoker.invoke(params);

        assertNull(tokenDto);

//        System.out.println(accessTokenDto);

    }


    @Test(expected = IllegalStateException.class)
    public void invalidScope() {

        createClientDetails();
        createUser();

        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", "password");
//        params.put("scope", "read");

        params.put("username", username);
        params.put("password", password);

        PasswordInlineAccessTokenInvoker accessTokenInvoker = new PasswordInlineAccessTokenInvoker();
        final AccessTokenDto tokenDto = accessTokenInvoker.invoke(params);

        assertNull(tokenDto);

//        System.out.println(accessTokenDto);

    }


}