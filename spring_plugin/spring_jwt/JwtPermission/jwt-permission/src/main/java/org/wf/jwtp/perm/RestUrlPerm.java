package org.wf.jwtp.perm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.wf.jwtp.annotation.Logical;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * url自动对应权限 - RESTful模式
 * Created by wangfan on 2019-01-21 下午 4:19.
 */
public class RestUrlPerm implements UrlPerm {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public UrlPermResult getPermission(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        Method method = handler.getMethod();
        List<String> perms = new ArrayList<String>();
        // 获取Method上的注解
        String[] urls, types, urlPres;
        if (method.getAnnotation(GetMapping.class) != null) {
            urls = method.getAnnotation(GetMapping.class).value();
            types = new String[]{"get"};
        } else if (method.getAnnotation(PostMapping.class) != null) {
            urls = method.getAnnotation(PostMapping.class).value();
            types = new String[]{"post"};
        } else if (method.getAnnotation(PutMapping.class) != null) {
            urls = method.getAnnotation(PutMapping.class).value();
            types = new String[]{"put"};
        } else if (method.getAnnotation(DeleteMapping.class) != null) {
            urls = method.getAnnotation(DeleteMapping.class).value();
            types = new String[]{"delete"};
        } else if (method.getAnnotation(RequestMapping.class) != null) {
            urls = method.getAnnotation(RequestMapping.class).value();
            types = new String[]{"get", "post", "put", "delete"};
        } else {
            urls = new String[0];
            types = new String[0];
        }
        // 获取Class上的注解
        Class<?> clazz = method.getDeclaringClass();
        if (clazz.getAnnotation(GetMapping.class) != null) {
            urlPres = clazz.getAnnotation(GetMapping.class).value();
        } else if (clazz.getAnnotation(PostMapping.class) != null) {
            urlPres = clazz.getAnnotation(PostMapping.class).value();
        } else if (clazz.getAnnotation(PutMapping.class) != null) {
            urlPres = clazz.getAnnotation(PutMapping.class).value();
        } else if (clazz.getAnnotation(DeleteMapping.class) != null) {
            urlPres = clazz.getAnnotation(DeleteMapping.class).value();
        } else if (clazz.getAnnotation(RequestMapping.class) != null) {
            urlPres = clazz.getAnnotation(RequestMapping.class).value();
        } else {
            urlPres = new String[0];
        }
        // 生成权限标识
        for (String type : types) {
            for (String url : urls) {
                StringBuilder sb = new StringBuilder();
                sb.append(type);
                sb.append(":");
                if (urlPres.length > 0) {
                    if (!urlPres[0].startsWith("/")) {
                        sb.append("/");
                    }
                    sb.append(urlPres[0]);
                }
                if (!url.startsWith("/")) {
                    sb.append("/");
                }
                sb.append(url);
                perms.add(sb.toString());
            }
        }
        String[] arrays = perms.toArray(new String[0]);
        logger.debug("JwtPermissions: RESTful Auth " + Arrays.toString(arrays));
        return new UrlPermResult(arrays, Logical.OR);
    }

    @Override
    public UrlPermResult getRoles(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        return new UrlPermResult(new String[0], Logical.OR);
    }

}
