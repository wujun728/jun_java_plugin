package cn.springmvc.jpa.web.util;

import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.springmvc.jpa.common.constants.Constants;
import cn.springmvc.jpa.common.exception.BusinessException;
import cn.springmvc.jpa.entity.Role;
import cn.springmvc.jpa.entity.User;
import cn.springmvc.jpa.security.Principal;

/**
 * @author Vincent.wang
 *
 */
public class WebUtil {

    private static final Logger log = LoggerFactory.getLogger(WebUtil.class);

    /**
     * 获取当前用户
     *
     * @return 用户
     * @throws BopException
     */
    public static User getUser() throws BusinessException {
        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            log.error("## user don't login . user is null.");
            throw new BusinessException("user.illegal.login.error", "用户非法身份登录。");
        }

        User user = principal.getUser();
        if (user == null) {
            log.error("## user don't login . user is null.");
            throw new BusinessException("user.illegal.login.error", "用户非法身份登录。");
        }

        return user;
    }

    /**
     * 获取当前用户所在店铺
     *
     * @return
     * @throws BopException
     */
    public static Collection<Role> getRoles() throws BusinessException {
        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            log.error("## user don't login . user is null.");
            throw new BusinessException("user.illegal.login.error", "用户非法身份登录。");
        }
        return principal.getRoles();
    }

    public static boolean hasManager() throws BusinessException {
        try {
            if (SecurityUtils.getSubject().hasRole(Constants.SYSTEM_ROLE_CODE) || SecurityUtils.getSubject().hasRole(Constants.COMMON_ROLE_CODE)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("## user don't login . user is null.");
            throw new BusinessException("user.illegal.login.error", "用户非法身份登录。");
        }
    }

}
