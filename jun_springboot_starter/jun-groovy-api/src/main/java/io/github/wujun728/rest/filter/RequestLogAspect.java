//package io.github.wujun728.rest.filter;
//
//import cn.hutool.core.util.StrUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import net.trueland.scrm.common.constant.WebConsts;
//import net.trueland.scrm.common.context.auth.CrmUserInfoContext;
//import net.trueland.scrm.common.context.threadlocal.ThreadLocalHelper;
//import net.trueland.scrm.common.context.web.WebRequestContext;
//import net.trueland.scrm.common.model.auth.Base;
//import net.trueland.scrm.common.web.property.ScrmRequestProperties;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.core.annotation.Order;
//
//import static cn.hutool.core.text.CharSequenceUtil.isNotBlank;
//import static java.util.Objects.nonNull;
//import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
//
///**
// * 请求日志打印
// */
//@Slf4j
//@Aspect
//@Order(HIGHEST_PRECEDENCE)
//@RequiredArgsConstructor
//public class RequestLogAspect {
////    private final ScrmRequestProperties scrmRequestProperties;
//    /**
//     * 这个线程变量标记用来避免controller#method调controller#method（可能性比较小）重复打印请求日志的情况
//     */
//    private static final String HAS_LOGGED_THREAD_KEY = "RequestLogAspect_Log_Sign";
//
//    @Around("execution(* net.trueland..*(..))  && @within(org.springframework.web.bind.annotation.RequestMapping) "
//            + "&& (execution(@(@org.springframework.web.bind.annotation.RequestMapping *) * *(..)) "
//            + " || @annotation(org.springframework.web.bind.annotation.RequestMapping))")
//    public Object aroundRequestMapping(ProceedingJoinPoint joinPoint) throws Throwable {
//        if (StrUtil.isBlank(WebRequestContext.getRequestURI())){
//            log.trace("no requestUri!");
//            return joinPoint.proceed();
//        }
//        boolean logSign = !hasLogged() && scrmRequestProperties.getLog()
//                .getUriPatterns()
//                .stream()
//                .anyMatch(p -> WebConsts.DEFAULT_ANT_PATH_MATCHER.match(p, WebRequestContext.getRequestURI())) && scrmRequestProperties.getLog()
//                .getIgnoreUriPatterns()
//                .stream()
//                .noneMatch(p -> WebConsts.DEFAULT_ANT_PATH_MATCHER.match(p, WebRequestContext.getRequestURI()));
//
//        long startTime = System.currentTimeMillis();
//        if (logSign)
//            log.info(assembleRequestMessage());
//        ThreadLocalHelper.put(HAS_LOGGED_THREAD_KEY, 1);
//        try {
//            return joinPoint.proceed();
//        } finally {
//            if (logSign)
//                log.info("<------ Response [{}]  ({}ms)", WebRequestContext.getRequestURI(), System.currentTimeMillis() - startTime);
//            ThreadLocalHelper.remove(HAS_LOGGED_THREAD_KEY);
//        }
//    }
//
//    private boolean hasLogged(){
//        return nonNull(ThreadLocalHelper.get(HAS_LOGGED_THREAD_KEY));
//    }
//
//    private String assembleRequestMessage() {
//        StringBuilder msg = new StringBuilder();
//        msg.append("------> Request [");
//        msg.append(WebRequestContext.getMethod()).append(' ').append(WebRequestContext.getRequestURI());
//        msg.append(']');
//        if (scrmRequestProperties.getLog().isIncludeClientInfo() && isNotBlank(WebRequestContext.getRemoteAddr())) {
//            msg.append(", client=").append(WebRequestContext.getRemoteAddr());
//            Base base = CrmUserInfoContext.getBase();
//            if (base != null) {
//                msg.append(", accountId=").append(base.getAccountId()).append(", userId=").append(base.getUserId());
//            }
//        }
//        msg.append(", cURL（bash）=\n").append(RequestLogUtils.getHttpInfo(scrmRequestProperties)).append("\n");
//        return msg.toString();
//    }
//}
