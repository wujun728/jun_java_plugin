package com.andaily.springoauth.service.password;

import com.andaily.springoauth.infrastructure.httpclient.HttpClientExecutor;
import com.andaily.springoauth.service.dto.AccessTokenDto;
import com.andaily.springoauth.service.dto.UserDto;
import com.andaily.springoauth.service.impl.AccessTokenResponseHandler;
import com.andaily.springoauth.service.impl.UserDtoResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle 'password'  type actions
 * <p/>
 * http://localhost:8080/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=password&scope=read,write&username=mobile&password=mobile
 * <p/>
 * How to use?
 * Please see unit-test <code>PasswordOauthHandlerTest</code>
 *
 * @author Wujun
 */
public class PasswordOauthHandler {

    private static final Logger LOG = LoggerFactory.getLogger(PasswordOauthHandler.class);

    public PasswordOauthHandler() {
    }

    /**
     * Step 1:
     * Get access token from Oauth server
     * <p/>
     * Response Data:
     * {"access_token":"9d0a2372-d039-4c1a-b0f9-762be10d531c","token_type":"bearer","refresh_token":"cfe0866d-d712-4ec2-9f23-ddb61443c9db","expires_in":38818,"scope":"read write"}
     * <p/>
     * Error Data:
     * <oauth>
     * <error_description>Bad client credentials</error_description>
     * <error>invalid_client</error>
     * </oauth>
     * <p/>
     * or
     * {"error":"invalid_grant","error_description":"Bad credentials"}
     *
     * @return AccessTokenDto
     */
    public AccessTokenDto getAccessToken(PasswordParams params) {
        final String fullUri = params.getFullUri();
        LOG.debug("Get 'access_token' uri: {}", fullUri);

        HttpClientExecutor executor = new HttpClientExecutor(fullUri);
        AccessTokenResponseHandler responseHandler = new AccessTokenResponseHandler();
        executor.execute(responseHandler);

        return responseHandler.getAccessTokenDto();
    }


    /**
     * Step 2
     * <p/>
     * Call Oauth Server API
     *
     * @param mobileUserInfoUri mobileUserInfoUri
     * @param accessToken       accessToken
     * @return UserDto
     */
    public UserDto getMobileUserDto(String mobileUserInfoUri, String accessToken) {
        LOG.debug("Get Mobile UserDto by uri={},accessToken={}", mobileUserInfoUri, accessToken);

        HttpClientExecutor executor = new HttpClientExecutor(mobileUserInfoUri);
        executor.addRequestParam("access_token", accessToken);

        UserDtoResponseHandler responseHandler = new UserDtoResponseHandler();
        executor.execute(responseHandler);

        return responseHandler.getUserDto();
    }

}