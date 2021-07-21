package org.wf.jwtp.perm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.wf.jwtp.annotation.Logical;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * url自动对应权限 - 简易模式
 * Created by wangfan on 2019-01-21 下午 4:18.
 */
public class SimpleUrlPerm implements UrlPerm {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public UrlPermResult getPermission(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        String perm = request.getRequestURI();
        logger.debug("JwtPermissions: Url Auth " + perm);
        return new UrlPermResult(new String[]{perm}, Logical.OR);
    }

    @Override
    public UrlPermResult getRoles(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        return new UrlPermResult(new String[0], Logical.OR);
    }

}
