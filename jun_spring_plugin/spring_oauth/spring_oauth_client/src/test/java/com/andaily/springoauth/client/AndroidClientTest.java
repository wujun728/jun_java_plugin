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
package com.andaily.springoauth.client;

import com.andaily.springoauth.infrastructure.httpclient.HttpClientExecutor;
import com.andaily.springoauth.service.dto.AccessTokenDto;
import com.andaily.springoauth.service.dto.UserDto;
import com.andaily.springoauth.service.impl.AccessTokenResponseHandler;
import com.andaily.springoauth.service.impl.UserDtoResponseHandler;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * 2015/11/6
 * <p/>
 * Just for testing Android call flow
 *
 * @author Shengzhao Li
 */
public class AndroidClientTest {


    /**
     * http://localhost:8080/som/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=password&scope=read,write&username=mobile&password=mobile
     *
     * @throws Exception
     */

    @Test(enabled = false)
    public void getAccessToken() throws Exception {

        /*
        * 对于每一类设备(client), clientId, clientSecret 是固定的
        * */
        String clientId = "passw";
        String clientSecret = "passwpassw";

        String authUrl = "http://localhost:8080/som/oauth/token";

        /*
        * 用户在 UI界面上输入 username, password
        * */
        String username = "mobile";
        String password = "mobile";

        String fullUrl = String.format("%s?grant_type=password&scope=read&client_id=%s&client_secret=%s&username=%s&password=%s", authUrl, clientId, clientSecret, username, password);

        HttpClientExecutor httpClientExecutor = new HttpClientExecutor(fullUrl);

        AccessTokenResponseHandler tokenResponseHandler = new AccessTokenResponseHandler();
        httpClientExecutor.execute(tokenResponseHandler);

        final AccessTokenDto accessTokenDto = tokenResponseHandler.getAccessTokenDto();
        assertNotNull(accessTokenDto);

        System.out.println("access_token = " + accessTokenDto.getAccessToken());
        System.out.println(accessTokenDto.getOriginalText());

    }


    /**
     * Get resource from RS server
     *
     * @throws Exception
     */
    @Test(enabled = false)
    public void getResource() throws Exception {

        String accessToken = "e07b43a3-1b33-4b59-b8e0-2c0445f52b3f";

        String resourceUrl = "http://localhost:8080/som/m/user_info";
//        String resourceUrl = "http://localhost:8080/som/unity/user_info";

        String fullUrl = String.format("%s?access_token=%s", resourceUrl, accessToken);

        HttpClientExecutor clientExecutor = new HttpClientExecutor(fullUrl);

        final UserDtoResponseHandler responseHandler = new UserDtoResponseHandler();
        clientExecutor.execute(responseHandler);

        final UserDto userDto = responseHandler.getUserDto();
        assertNotNull(userDto);

        System.out.println(userDto.getOriginalText());
        System.out.println("username = " + userDto.getUsername());

    }


}
