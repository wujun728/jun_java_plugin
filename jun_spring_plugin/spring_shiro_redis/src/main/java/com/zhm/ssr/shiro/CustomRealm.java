package com.zhm.ssr.shiro;

import com.zhm.ssr.model.UserInfo;
import com.zhm.ssr.service.UserInfoService;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhm on 2015/7/10.
 */
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserInfoService userInfoService;

    //设置自定义的MD5加密匹配
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        // TODO Auto-generated method stub
        HashedCredentialsMatcher tmp = new HashedCredentialsMatcher("MD5");
        tmp.setStoredCredentialsHexEncoded(true);
        super.setCredentialsMatcher(tmp);
    }

    /**
     * 该方法在需要授权访问资源的时候调用。
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //授权信息本地session缓存。不用每次都去查询。
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        if(session.getAttribute("simpleAuthInfo")==null){
            String currentUsername = (String)super.getAvailablePrincipal(principals);
            SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
            UserInfo user = userInfoService.findByUserid(currentUsername);
            if(null != user&&("黑子的篮球".equals(user.getUsername()))){
                /**
                 * 权限表可以自己建表关联。这里只是演示，就写死了。
                 */
                simpleAuthorInfo.addRole("admin");
                simpleAuthorInfo.addStringPermission("admin:manage");
                session.setAttribute("simpleAuthInfo", simpleAuthorInfo);
                return simpleAuthorInfo;

            }else{
                simpleAuthorInfo.addRole("base");
                simpleAuthorInfo.addStringPermission("base:base");
                session.setAttribute("simpleAuthInfo", simpleAuthorInfo);
                return simpleAuthorInfo;
//                throw new AuthorizationException();
            }
        }
        else{
            return (SimpleAuthorizationInfo)session.getAttribute("simpleAuthInfo");
        }
    }

    /**
     * 该方法在LoginController执行dologin之后调用
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        UserInfo user = userInfoService.findByUserid(token.getUsername());
        if(null != user){
            //不用验证密码对不对，只需要把数据传递过去就行。shiro会帮忙比较
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getMobile(), user.getPassword(), user.getEmail());
            this.setSession("currUser", user);
            return authcInfo;
        }else{
              return null;
        }
    }

    private void setSession(Object key, Object value){
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            Session session = currentUser.getSession();
            if(null != session){
                session.setAttribute(key, value);
            }
        }
    }
}
