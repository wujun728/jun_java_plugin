package com.chentongwei.common.aop;

import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.enums.msg.ResponseEnum;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.entity.io.TokenIO;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.common.util.CommonExceptionUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: AOP拦截Service层，记录慢日志。
 */
@Aspect
@Component
public class Aop {
    /**
     * 慢日志LOG
     */
    private static final Logger LOG = LogManager.getLogger("slowLog");

    /**
     * 切点表达式
     */
    private static final String CUT = "execution (* com.chentongwei.core.*.biz..*Biz.*(..))";

    @Autowired
    private IBasicCache basicCache;

    @Around(CUT)
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        long start = System.currentTimeMillis();
        Result result;
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            if (null != servletRequestAttributes) {
                HttpServletRequest request = servletRequestAttributes.getRequest();
                String userId = request.getHeader("userId");
                String token = request.getHeader("token");

                for (Object arg : args) {
                    if (arg != null) {
                        if (TokenIO.class.isAssignableFrom(arg.getClass())) {
                            //检查当前用户是否登录
                            String tokenStr = basicCache.get(RedisEnum.getLoginedUserKey(userId));
                            CommonExceptionUtil.nullCheck(token, ResponseEnum.USER_NOT_LOGIN);
                            if (! Objects.equals(tokenStr, token)) {
                                throw new BussinessException(ResponseEnum.USER_NOT_LOGIN);
                            }
                            PropertyUtils.setProperty(arg, "userId", userId == null ? -1 : Long.parseLong(userId));
                            PropertyUtils.setProperty(arg, "token", token);
                        }
                    }
                }
            }
            result = (Result)pjp.proceed(args);
        } catch (Throwable e) {
            throw e;
        }
        //慢日志
        long end = System.currentTimeMillis();
        //大于2秒记录
        if (end - start >= 2000) {
            LOG.info("method [" + pjp.getSignature().toString() +"] cost " + (end - start) + "ms ---------------");
        }
        return result;
    }
}