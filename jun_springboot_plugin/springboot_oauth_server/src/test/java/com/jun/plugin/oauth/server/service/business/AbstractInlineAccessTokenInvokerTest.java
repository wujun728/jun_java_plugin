package com.jun.plugin.oauth.server.service.business;

import com.jun.plugin.oauth.server.domain.oauth.OauthClientDetails;
import com.jun.plugin.oauth.server.domain.oauth.OauthRepository;
import com.jun.plugin.oauth.server.domain.user.Privilege;
import com.jun.plugin.oauth.server.domain.user.User;
import com.jun.plugin.oauth.server.domain.user.UserRepository;
import com.jun.plugin.oauth.server.infrastructure.AbstractRepositoryTest;
import com.jun.plugin.oauth.server.infrastructure.PasswordHandler;

import static com.jun.plugin.oauth.server.config.OAuth2ServerConfiguration.RESOURCE_ID;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 2019/7/6
 *
 * @author Wujun
 */
public abstract class AbstractInlineAccessTokenInvokerTest extends AbstractRepositoryTest {


    @Autowired
    OauthRepository oauthRepository;

    @Autowired
    UserRepository userRepository;


    String clientId = "client_id_" + RandomStringUtils.random(6, true, true);
    String clientSecret = "client_secret_" + RandomStringUtils.random(6, true, true);


    String username = "user_" + RandomStringUtils.random(6, true, true);
    String password = "password_" + RandomStringUtils.random(6, true, true);


    User createUser() {


        User user = new User(username, PasswordHandler.encode(password), "13300001111", "test@ssss.com");
        user.privileges().add(Privilege.UNITY);
        user.privileges().add(Privilege.USER);
        user.privileges().add(Privilege.MOBILE);

        userRepository.saveUser(user);

        return user;
    }


    OauthClientDetails createClientDetails() {
        OauthClientDetails clientDetails = new OauthClientDetails();
        clientDetails.clientId(clientId)
                .clientSecret(PasswordHandler.encode(clientSecret))
                .authorizedGrantTypes(grantTypes())
                .scope("read")
                .accessTokenValidity(200)
                .resourceIds(RESOURCE_ID);


        oauthRepository.saveOauthClientDetails(clientDetails);
        return clientDetails;
    }

    String grantTypes() {
        return "authorization_code,password,implicit,client_credentials,refresh_token";
    }

}
