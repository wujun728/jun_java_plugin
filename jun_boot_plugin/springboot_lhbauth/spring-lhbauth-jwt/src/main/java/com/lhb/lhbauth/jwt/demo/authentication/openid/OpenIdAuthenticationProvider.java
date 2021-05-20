package com.lhb.lhbauth.jwt.demo.authentication.openid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Wujun
 * @description
 * @date 2019/1/8 0008 14:46
 */
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    private SocialUserDetailsService myUserDetailsService;

    private UsersConnectionRepository usersConnectionRepository;

    @Override
    public boolean supports(Class<?> authentication) {
        //支持OpenIdAuthenticationToken来验证
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //这个authentication就是OpenIdAuthenticationToken
        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;

        //校验手机号
        Set<String> providerUserIds = new HashSet<>();
        providerUserIds.add((String) authenticationToken.getPrincipal());
        Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(authenticationToken.getProviderId(),providerUserIds);

        if(CollectionUtils.isEmpty(userIds) || userIds.size() != 1){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        String userId = userIds.iterator().next();

        UserDetails user = myUserDetailsService.loadUserByUserId(userId);

        if(user == null ){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }


        //这时候已经认证成功了
        OpenIdAuthenticationToken authenticationResult = new OpenIdAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    public SocialUserDetailsService getMyUserDetailsService() {
        return myUserDetailsService;
    }

    public void setMyUserDetailsService(SocialUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    public UsersConnectionRepository getUsersConnectionRepository() {
        return usersConnectionRepository;
    }

    public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
    }
}
