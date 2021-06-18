package cn.springmvc.mybatis.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.springmvc.mybatis.common.constants.Constants;
import cn.springmvc.mybatis.common.exception.BusinessException;
import cn.springmvc.mybatis.common.utils.salt.Encodes;
import cn.springmvc.mybatis.entity.auth.Permission;
import cn.springmvc.mybatis.entity.auth.Role;
import cn.springmvc.mybatis.entity.auth.User;
import cn.springmvc.mybatis.service.auth.PermissionService;
import cn.springmvc.mybatis.service.auth.RoleService;
import cn.springmvc.mybatis.service.auth.UserService;
import cn.springmvc.mybatis.web.util.MenuUtil;

/**
 * @author Vincent.wang
 *
 */
public class AuthorizingRealmImpl extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(AuthorizingRealmImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("## 正在验证用户登录...");
            }

            UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
            String username = token.getUsername();

            if (StringUtils.isBlank(username)) {
                log.error("## 非法登录 .");
                throw new BusinessException("user.illegal.login.error", "非法用户身份");
            }

            User user = userService.findUserByName(username);
            if (null == user) {
                log.error("## 用户不存在={} .", username);
                throw new BusinessException("user.login.error", "账号或密码错误");
            }

            byte[] salt = Encodes.decodeHex(user.getSalt());

            Principal principal = new Principal();
            principal.setUser(user);
            principal.setRoles(roleService.findRoleByUserId(user.getId()));

            SecurityUtils.getSubject().getSession().setAttribute(Constants.PERMISSION_SESSION, MenuUtil.getMenus(permissionService.getPermissions(user.getId())));

            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, user.getPassword(), ByteSource.Util.bytes(salt), getName());
            return info;
        } catch (AuthenticationException e) {
            log.error("# doGetAuthenticationInfo error , message={}", e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
        Session session = SecurityUtils.getSubject().getSession();

        // ---
        Set<String> permissions = new HashSet<String>();
        Object permisObj = session.getAttribute(Constants.PERMISSION_URL);
        if (null == permisObj) {
            Collection<Permission> pers = permissionService.getPermissions(principal.getUser().getId());
            for (Permission permission : pers) {
                permissions.add(permission.getName());
            }
            session.setAttribute(Constants.PERMISSION_URL, permissions);
        } else {
            permissions = (Set<String>) permisObj;
        }

        Set<String> roleCodes = new HashSet<String>();
        Object roleNameObj = session.getAttribute(Constants.ROLE_CODE);
        if (null == roleNameObj) {
            for (Role role : roleService.findRoleByUserId(principal.getUser().getId())) {
                roleCodes.add(role.getCode());
            }
            session.setAttribute(Constants.ROLE_CODE, roleCodes);
        } else {
            roleCodes = (Set<String>) roleNameObj;
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleCodes);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-1");
        matcher.setHashIterations(1024);
        setCredentialsMatcher(matcher);
    }

}
