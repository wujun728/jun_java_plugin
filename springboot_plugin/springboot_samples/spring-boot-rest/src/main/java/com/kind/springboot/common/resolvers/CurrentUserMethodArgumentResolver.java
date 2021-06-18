package com.kind.springboot.common.resolvers;

import com.kind.springboot.common.annotation.CurrentUser;
import com.kind.springboot.common.config.UserConstants;
import com.kind.springboot.core.domain.UserDo;
import com.kind.springboot.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * Function:增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户. <br/>
 * Date: 2016年8月11日 下午1:11:58 <br/>
 *
 * @author Wujun
 * @see
 * @since JDK 1.7
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        /**
         * 如果参数类型是User并且有CurrentUser注解则支持
         */
        if (parameter.getParameterType().isAssignableFrom(UserDo.class)
                && parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        /**
         * 取出鉴权时存入的登录用户Id
         */
        Long currentUserId = (Long) webRequest.getAttribute(UserConstants.CURRENT_USER_ID,
                RequestAttributes.SCOPE_REQUEST);
        if (currentUserId != null) {
            /**
             * 从数据库中查询并返回
             */
            return userService.getById(currentUserId);
        }
        throw new MissingServletRequestPartException(UserConstants.CURRENT_USER_ID);
    }
}
