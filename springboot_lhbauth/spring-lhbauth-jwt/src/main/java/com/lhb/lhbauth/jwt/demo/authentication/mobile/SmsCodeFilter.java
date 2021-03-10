package com.lhb.lhbauth.jwt.demo.authentication.mobile;

import com.lhb.lhbauth.jwt.demo.cahe.redis.VcodeManager;
import com.lhb.lhbauth.jwt.demo.properties.SecurityProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Wujun
 * @description
 * @date 2019/1/2 0002 10:42
 */
@Slf4j
@Data
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    private Set<String> urls = new HashSet<>();
    private SecurityProperties securityProperties;

    private VcodeManager vcodeManager;
    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();


    public SmsCodeFilter(VcodeManager vcodeManager){
        this.vcodeManager = vcodeManager;
    }


    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(
                securityProperties.getCode().getSms().getUrl(), ",");
        for (String configUrl : configUrls) {
            urls.add(configUrl);
        }
        urls.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        boolean action = false;
        for (String url : urls) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }

        if (action) {
            try {
                redisValidate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                e.printStackTrace();
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private void redisValidate(ServletWebRequest request) throws ServletRequestBindingException {

        String mobile = request.getParameter("mobile");
        log.info(mobile);

        System.out.println( vcodeManager.getVcode(mobile));
        if (null == vcodeManager.getVcode(mobile)) {
            throw new ValidateCodeException("验证码已过期" + mobile);
        }

        String codeInRedis = vcodeManager.getVcode(mobile).toString();
        //从请求中拿到imageCode这个参数
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");
        if (!codeInRedis.equals(codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }


        vcodeManager.removeVcode(mobile);


    }
}
