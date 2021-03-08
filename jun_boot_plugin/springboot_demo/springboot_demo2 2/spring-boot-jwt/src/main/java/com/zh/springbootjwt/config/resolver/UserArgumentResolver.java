package com.zh.springbootjwt.config.resolver;

import com.zh.springbootjwt.config.annotation.CurrentUser;
import com.zh.springbootjwt.model.User;
import com.zh.springbootjwt.service.UserService;
import com.zh.springbootjwt.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * 参数解析器
 * 将当前登陆用户的信息注入到参数里
 * @author Wujun
 * @date 2019/6/25
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(User.class) && methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = nativeWebRequest.getHeader("token");
        if (token != null){
            Integer userId = JwtUtil.getUserId(token);
            if (userId != null){
                User user = this.userService.findByUserId(userId);
                if (user != null){
                    return user;
                }
            }
        }
        return null;
    }
}
