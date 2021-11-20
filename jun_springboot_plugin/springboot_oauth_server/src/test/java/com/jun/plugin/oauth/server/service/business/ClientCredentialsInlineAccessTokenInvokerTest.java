package com.jun.plugin.oauth.server.service.business;

import org.junit.Test;
import org.springframework.security.oauth2.provider.NoSuchClientException;

import com.jun.plugin.oauth.server.service.business.ClientCredentialsInlineAccessTokenInvoker;
import com.jun.plugin.oauth.server.service.dto.AccessTokenDto;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * 2019/7/6
 *
 * @author Wujun
 */
public class ClientCredentialsInlineAccessTokenInvokerTest extends AbstractInlineAccessTokenInvokerTest {


    @Test
    public void invokeNormal() {

        createClientDetails();

        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", "client_credentials");
        params.put("scope", "read");


        ClientCredentialsInlineAccessTokenInvoker accessTokenInvoker = new ClientCredentialsInlineAccessTokenInvoker();
        final AccessTokenDto accessTokenDto = accessTokenInvoker.invoke(params);

        assertNotNull(accessTokenDto);
        assertNotNull(accessTokenDto.getAccessToken());

//        System.out.println(accessTokenDto);

    }

    @Test(expected = NoSuchClientException.class)
    public void invalidClientId() {

        createClientDetails();

        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId + "ssoso");
        params.put("client_secret", clientSecret);
        params.put("grant_type", "client_credentials");
        params.put("scope", "read");


        ClientCredentialsInlineAccessTokenInvoker accessTokenInvoker = new ClientCredentialsInlineAccessTokenInvoker();
        final AccessTokenDto accessTokenDto = accessTokenInvoker.invoke(params);

        assertNotNull(accessTokenDto);
        assertNotNull(accessTokenDto.getAccessToken());

//        System.out.println(accessTokenDto);

    }

    @Test()
    public void invalidClientSecret() {

        createClientDetails();

        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret + "sooe");
        params.put("grant_type", "client_credentials");
        params.put("scope", "read");


        ClientCredentialsInlineAccessTokenInvoker accessTokenInvoker = new ClientCredentialsInlineAccessTokenInvoker();
        final AccessTokenDto accessTokenDto = accessTokenInvoker.invoke(params);

        assertNotNull(accessTokenDto);
        assertNotNull(accessTokenDto.getAccessToken());

//        System.out.println(accessTokenDto);

    }

}