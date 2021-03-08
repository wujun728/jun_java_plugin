package com.zh.springbootshiro.config;

import com.zh.springbootshiro.enums.AppResultCode;
import com.zh.springbootshiro.exception.BusinessException;
import com.zh.springbootshiro.model.User;
import com.zh.springbootshiro.service.PermService;
import com.zh.springbootshiro.service.RoleService;
import com.zh.springbootshiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Wujun
 * @date 2019/6/26
 */
@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermService permService;

    /**
     * 设置密码匹配器
     * 默认不设置的话是以明文匹配，不安全
     * 默认密码是以16进制hex存储，所以生成密码也要生成16进制的字符串toHex()
     * 如果想密码生成base64的字符串需要将此处的以16进制存储设置为false:
     * hashMatcher.setStoredCredentialsHexEncoded(false);
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        //设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        //md5方式加密
        hashMatcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        //散列3次
        hashMatcher.setHashIterations(3);
        super.setCredentialsMatcher(hashMatcher);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        log.info("=================开始认证===============");
        String username = authenticationToken.getPrincipal().toString();
        User user = this.userService.findByUserName(username);
        if (user == null){
            throw new BusinessException(AppResultCode.USER_NOT_EXIST);
        }
        Set<String> roles = this.roleService.findByUserId(user.getId());
        Set<String> perms = this.permService.findByUserId(user.getId());
        user.setRoles(roles);
        user.setPerms(perms);
        //将库里的user信息,密码,盐(如果生成密码加了盐的话)传入构造
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("=================开始授权===============");
        User user = (User) getAvailablePrincipal(principalCollection);
        Set<String> roles = user.getRoles();
        Set<String> perms = user.getPerms();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(perms);
        return info;
    }

}
