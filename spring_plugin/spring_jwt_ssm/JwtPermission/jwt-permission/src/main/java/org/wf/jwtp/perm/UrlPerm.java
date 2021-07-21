package org.wf.jwtp.perm;

import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * url自动对应权限接口
 * Created by wangfan on 2018-12-27 下午 4:46.
 */
public interface UrlPerm {

    UrlPermResult getPermission(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler);

    UrlPermResult getRoles(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler);

}
