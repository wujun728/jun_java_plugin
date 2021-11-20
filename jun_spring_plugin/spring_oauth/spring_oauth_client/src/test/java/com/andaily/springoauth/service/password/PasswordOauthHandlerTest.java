package com.andaily.springoauth.service.password;

import com.andaily.springoauth.service.dto.AccessTokenDto;
import com.andaily.springoauth.service.dto.UserDto;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Shengzhao Li
 */
public class PasswordOauthHandlerTest {


    private PasswordOauthHandler passwordOauthHandler;


    @BeforeTest
    public void before() {
        this.passwordOauthHandler = new PasswordOauthHandler();
    }


    /**
     * Test variables from  'spring-oauth-client.properties'
     *
     * @throws Exception
     */
    @Test(enabled = false)
    public void getAccessToken() throws Exception {
        final String accessTokenUri = "http://localhost:8080/spring-oauth-server/oauth/token";

        /*
        *  Test case 1:  All variable are right
        * */
        PasswordParams params = new PasswordParams(accessTokenUri)
                .setClientId("mobile-client")
                .setClientSecret("mobile")
                .setUsername("mobile")
                .setPassword("mobile");

        final AccessTokenDto accessToken = passwordOauthHandler.getAccessToken(params);
        assertNotNull(accessToken);
        assertNotNull(accessToken.getAccessToken());

//        assertTrue(accessToken.getExpiresIn() > 0);
        assertFalse(accessToken.error());
        System.out.println("Test Case 1: " + accessToken);

        /*
        *  Test case 2:  'client_id' is wrong
        * */

        PasswordParams params2 = new PasswordParams(accessTokenUri)
                .setClientId("mobile-client-wrong")
                .setClientSecret("mobile")
                .setUsername("mobile")
                .setPassword("mobile");

        final AccessTokenDto accessToken2 = passwordOauthHandler.getAccessToken(params2);
        assertNotNull(accessToken2);
        assertTrue(accessToken2.error());
        System.out.println("Test Case 2: " + accessToken2);

        /*
        *  Test case 3:  'client_secret' is wrong
        * */

        PasswordParams params3 = new PasswordParams(accessTokenUri)
                .setClientId("mobile-client")
                .setClientSecret("mobile-wrong")
                .setUsername("mobile")
                .setPassword("mobile");

        final AccessTokenDto accessToken3 = passwordOauthHandler.getAccessToken(params3);
        assertNotNull(accessToken3);
        assertTrue(accessToken3.error());
        System.out.println("Test Case 3: " + accessToken3);

        /*
        *  Test case 4:  'username' is wrong
        * */

        PasswordParams params4 = new PasswordParams(accessTokenUri)
                .setClientId("mobile-client")
                .setClientSecret("mobile")
                .setUsername("mobile-wrong")
                .setPassword("mobile");

        final AccessTokenDto accessToken4 = passwordOauthHandler.getAccessToken(params4);
        assertNotNull(accessToken4);
        assertTrue(accessToken4.error());
        System.out.println("Test Case 4: " + accessToken4);

        /*
        *  Test case 5:  'password' is wrong
        * */

        PasswordParams params5 = new PasswordParams(accessTokenUri)
                .setClientId("mobile-client")
                .setClientSecret("mobile")
                .setUsername("mobile")
                .setPassword("mobile-wrong");

        final AccessTokenDto accessToken5 = passwordOauthHandler.getAccessToken(params5);
        assertNotNull(accessToken5);
        assertTrue(accessToken5.error());
        System.out.println("Test Case 5: " + accessToken5);


    }


    @Test(enabled = false)
    public void getMobileUserDto() throws Exception {
        final String accessTokenUri = "http://localhost:8080/spring-oauth-server/oauth/token";

        PasswordParams params = new PasswordParams(accessTokenUri)
                .setClientId("mobile-client")
                .setClientSecret("mobile")
                .setUsername("mobile")
                .setPassword("mobile");

        final AccessTokenDto accessToken = passwordOauthHandler.getAccessToken(params);
        assertNotNull(accessToken);

        //URI from ' spring-oauth-client.properties'
        String mobileUserInfoUri = "http://localhost:8080/spring-oauth-server/m/user_info";

        /*
        * Test case 1:  normally
        * */

        final UserDto userDto = passwordOauthHandler.getMobileUserDto(mobileUserInfoUri, accessToken.getAccessToken());
        assertNotNull(userDto);
        assertFalse(userDto.error());

        assertNotNull(userDto.getUsername());
        assertNotNull(userDto.getUuid());

        /*
       * Test case 2:  illegal access_token
       * */

        final UserDto userDto2 = passwordOauthHandler.getMobileUserDto(mobileUserInfoUri, UUID.randomUUID().toString());
        assertNotNull(userDto2);
        assertTrue(userDto2.error());
        System.out.println(userDto2);


    }


}