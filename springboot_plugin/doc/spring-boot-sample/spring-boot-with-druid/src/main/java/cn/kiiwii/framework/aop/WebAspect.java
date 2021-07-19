package cn.kiiwii.framework.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhong on 2016/11/24.
 */
@Aspect
@Component
public class WebAspect {
    private static Logger logger = LoggerFactory.getLogger(WebAspect.class);

    @Pointcut("execution(public * cn.kiiwii.framework.controller.*.*(..))")
    public void webAspect() {
    }

    @Before("webAspect()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String beanName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String uri = request.getRequestURI();
        String remoteAddr = getIpAddr(request);
        String sessionId = request.getSession().getId();
        String user = (String) request.getSession().getAttribute("user");
        String method = request.getMethod();
        String params = "";
        System.out.println(sessionId);
    }

    @AfterReturning(returning = "result", pointcut = "webAspect()")
    public void doAfterReturning(Object result) {
    }

    @Around("webAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("方法环绕start.....");
        Object o = null;
        System.out.println("before---------------------");
        o = pjp.proceed();
        System.out.println("after---------------------");
        return o;
    }

    /**
     * 获取登录用户远程主机ip地址
     *
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
