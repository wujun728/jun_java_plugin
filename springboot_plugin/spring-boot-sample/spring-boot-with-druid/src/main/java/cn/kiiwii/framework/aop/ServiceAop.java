package cn.kiiwii.framework.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zhong on 2016/11/24.
 */
@Aspect
@Component
public class ServiceAop {
    private static Logger logger = LoggerFactory.getLogger(ServiceAop.class);

   /* private ThreadLocal<OperatorLog> tlocal = new ThreadLocal<OperatorLog>();

    @Autowired
    private OptLogService optLogService;*/

    @Pointcut("execution(public * cn.kiiwii.framework.service.impl.*.*(..))")
    public void webRequestLog() {}

    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) {
        ThreadLocal threadLocal;
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
        /*try {

            long beginTime = System.currentTimeMillis();

            // 接收到请求，记录请求内容
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
            if ("POST".equals(method)) {
                Object[] paramsArray = joinPoint.getArgs();
                params = argsArrayToString(paramsArray);
            } else {
                Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                params = paramsMap.toString();
            }

            logger.debug("uri=" + uri + "; beanName=" + beanName + "; remoteAddr=" + remoteAddr + "; user=" + user
                    + "; methodName=" + methodName + "; params=" + params);

            OperatorLog optLog = new OperatorLog();
            optLog.setBeanName(beanName);
            optLog.setCurUser(user);
            optLog.setMethodName(methodName);
            optLog.setParams(params != null ? params.toString() : "");
            optLog.setRemoteAddr(remoteAddr);
            optLog.setSessionId(sessionId);
            optLog.setUri(uri);
            optLog.setRequestTime(beginTime);
            tlocal.set(optLog);

        } catch (Exception e) {
            logger.error("***操作请求日志记录失败doBefore()***", e);
        }*/
    }

    @AfterReturning(returning = "result", pointcut = "webRequestLog()")
    public void doAfterReturning(Object result) {
        /*try {
            // 处理完请求，返回内容
            OperatorLog optLog = tlocal.get();
            optLog.setResult(result.toString());
            long beginTime = optLog.getRequestTime();
            long requestTime = (System.currentTimeMillis() - beginTime) / 1000;
            optLog.setRequestTime(requestTime);

            System.out.println("请求耗时：" + requestTime + optLog.getUri() + "   **  " + optLog.getParams() + " ** "
                    + optLog.getMethodName());
            System.out.println("RESPONSE : " + result);

            optLogService.saveLog(optLog);
        } catch (Exception e) {
            logger.error("***操作请求日志记录失败doAfterReturning()***", e);
        }*/
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

    /**
     * 请求参数拼装
     *
     * @param paramsArray
     * @return
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                Object jsonObj = paramsArray[i];
                params += jsonObj.toString() + " ";
            }
        }
        return params.trim();
    }
}
