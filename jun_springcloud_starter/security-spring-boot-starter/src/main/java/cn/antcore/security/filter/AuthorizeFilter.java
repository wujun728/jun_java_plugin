package cn.antcore.security.filter;

import cn.antcore.security.annotation.ApiAuthorize;
import cn.antcore.security.entity.UserDetails;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限过滤器
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/26</p>
 **/
public interface AuthorizeFilter {

    boolean check(HttpServletRequest request, HttpServletResponse response, HandlerMethod method, ApiAuthorize authorize, UserDetails userDetails);

}
