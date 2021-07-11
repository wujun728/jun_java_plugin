package cc.mrbird.febs.common.authentication;

import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.monitor.service.ISessionService;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IUserDataPermissionService;
import cc.mrbird.febs.system.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 *
 * @author MrBird
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ShiroRealm extends AuthorizingRealm {

    private final ISessionService sessionService;
    private final ShiroLogoutService shiroLogoutService;
    private final IUserDataPermissionService userDataPermissionService;
    private final IUserService userService;

    private RedisCacheManager redisCacheManager;
    private EhCacheManager ehCacheManager;
    @Value("${" + FebsProperties.ENABLE_REDIS_CACHE + "}")
    private boolean enableRedisCache;

    @Autowired(required = false)
    public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    @Autowired(required = false)
    public void setEhCacheManager(EhCacheManager ehCacheManager) {
        this.ehCacheManager = ehCacheManager;
    }

    @PostConstruct
    private void initConfig() {
        setAuthenticationCachingEnabled(false);
        setAuthorizationCachingEnabled(true);
        setCachingEnabled(true);
        setCacheManager(redisCacheManager == null ? ehCacheManager : redisCacheManager);
    }

    /**
     * 授权模块，获取用户角色和权限
     *
     * @param principal principal
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        User user = (User) principal.getPrimaryPrincipal();
        userService.doGetUserAuthorizationInfo(user);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(user.getRoles());
        simpleAuthorizationInfo.setStringPermissions(user.getStringPermissions());
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     *
     * @param token AuthenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        // 通过用户名到数据库查询用户信息
        User user = this.userService.findByName(username);

        if (user == null || !StringUtils.equals(password, user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if (User.STATUS_LOCK.equals(user.getStatus())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        String deptIds = this.userDataPermissionService.findByUserId(String.valueOf(user.getUserId()));
        user.setDeptIds(deptIds);
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        super.onLogout(principals);
        if (enableRedisCache) {
            shiroLogoutService.cleanCacheFragment(principals);
        }
    }

    public void clearCache(Long userId) {
        List<SimplePrincipalCollection> principals = sessionService.getPrincipals(userId);
        if (CollectionUtils.isNotEmpty(principals)) {
            for (SimplePrincipalCollection principal : principals) {
                super.clearCache(principal);
            }
        }
    }
}
