package cn.antcore.security.filter;

import cn.antcore.security.annotation.ApiAuthorize;
import cn.antcore.security.entity.UserDetails;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 权限过滤器
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/26</p>
 **/
public class AuthorizeFilterImpl implements AuthorizeFilter {

    @Override
    public boolean check(HttpServletRequest request, HttpServletResponse response, HandlerMethod method, ApiAuthorize authorize, UserDetails userDetails) {
        if (!userDetails.getAuthority().containsAll(Arrays.asList(authorize.authority()))) {
            return false;
        }
        if (!userDetails.getRoles().containsAll(Arrays.asList(authorize.roles()))) {
            return false;
        }
        return true;
    }
}
