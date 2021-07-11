package cn.kiiwii.framework.hibernate.aop;

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
public class ServiceAop {
    private static Logger logger = LoggerFactory.getLogger(ServiceAop.class);

    private ThreadLocal<Long> tlocal = new ThreadLocal<Long>();

    @Pointcut("execution(public * cn.kiiwii.framework.hibernate.service.impl.*.*(..))")
    public void webRequestLog() {
    }

    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) {

        long beginTime = System.currentTimeMillis();

        tlocal.set(beginTime);
    }

    @AfterReturning(returning = "result", pointcut = "webRequestLog()")
    public void doAfterReturning(Object result) {
        logger.info("消耗时间: " + (System.currentTimeMillis() - tlocal.get()));
    }

    @Around("webRequestLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("方法环绕start.....");
        Object o = null;
        System.out.println("before---------------------");
        o = pjp.proceed();
        System.out.println("after---------------------");
        return o;
    }
}
