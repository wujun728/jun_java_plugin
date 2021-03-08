package com.monkeyk.sos.service.business;

import com.monkeyk.sos.domain.oauth.OauthClientDetails;
import com.monkeyk.sos.domain.oauth.OauthRepository;
import com.monkeyk.sos.domain.user.Privilege;
import com.monkeyk.sos.domain.user.User;
import com.monkeyk.sos.domain.user.UserRepository;
import com.monkeyk.sos.infrastructure.AbstractRepositoryTest;
import com.monkeyk.sos.infrastructure.PasswordHandler;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static com.monkeyk.sos.config.OAuth2ServerConfiguration.RESOURCE_ID;

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
