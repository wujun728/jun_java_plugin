package com.kancy.emailplus.spring.boot.aop;

import com.kancy.emailplus.spring.boot.aop.annotation.EmailNotice;
import com.kancy.emailplus.spring.boot.properties.EmailDefinition;
import com.kancy.emailplus.spring.boot.properties.EmailplusProperties;
import com.kancy.emailplus.spring.boot.properties.NoticeProperties;
import com.kancy.emailplus.spring.boot.service.EmailplusService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * EmailNoticeAspect
 *
 * @author kancy
 * @date 2020/2/22 20:16
 */
@Aspect
public class EmailNoticeAspect {
    private static final Logger log = LoggerFactory.getLogger(EmailNoticeAspect.class);

    @Autowired
    private Map<String, EmailNoticeTrigger> emailNoticeTriggerMap;
    @Autowired
    private EmailplusService emailplusService;
    @Autowired
    private EmailplusProperties emailplusProperties;

    /**
     * 声明切点
     */
    @Pointcut("within(@com.kancy.emailplus.spring.boot.aop.annotation.EmailNotice *) " +
            "|| @annotation(com.kancy.emailplus.spring.boot.aop.annotation.EmailNotice))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object monitor(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {

            MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
            EmailNotice annotation = methodSignature.getMethod().getDeclaredAnnotation(EmailNotice.class);
            if (Objects.isNull(annotation)){
                annotation = joinPoint.getTarget().getClass().getAnnotation(EmailNotice.class);
            }

            boolean conditionOnThrowable = false;
            Class<? extends Throwable>[] classes = annotation.classes();
            for (Class<? extends Throwable> throwableClass : classes) {
                if (throwableClass.isAssignableFrom(throwable.getClass())){
                    conditionOnThrowable = true;
                }
            }

            boolean noThrow = false;
            Class<? extends Throwable>[] noThrows = annotation.noThrows();
            for (Class<? extends Throwable> throwableClass : noThrows) {
                if (throwableClass.isAssignableFrom(throwable.getClass())){
                    noThrow = true;
                }
            }

            if (!conditionOnThrowable){
                if (noThrow){
                    return result;
                }
                throw throwable;
            }

            NoticeProperties noticeProperties = emailplusProperties.getEmailNotices().get(annotation.value());
            EmailNoticeTrigger emailNoticeTrigger = findEmailNoticeTrigger(noticeProperties.getTrigger());
            if (Objects.isNull(emailNoticeTrigger)){
                if (noThrow){
                    return result;
                }
                throw throwable;
            }

            if (emailNoticeTrigger.isTrigger(annotation.value())){
                try {
                    // 发送邮件
                    EmailDefinition emailDefinition = emailplusProperties.getEmailDefinitions().get(noticeProperties.getEmailKey());
                    if (Objects.nonNull(emailDefinition.getTemplate())){
                        Map<String, Object> templateData = new HashMap<>();
                        templateData.put("ex", throwable);
                        templateData.put("methodName", methodSignature.getMethod().getName());
                        templateData.put("className", joinPoint.getTarget().getClass().getName());
                        templateData.put("name", annotation.value());
                        templateData.put("notice", noticeProperties);
                        templateData.put("email", emailDefinition);
                        templateData.put("emailKey", noticeProperties.getEmailKey());
                        emailplusService.sendTemplateEmail(noticeProperties.getEmailKey(), templateData);
                    } else {
                        emailplusService.sendSimpleEmail(noticeProperties.getEmailKey());
                    }
                    log.info("Trigger [{}] email notice, and send email [{}] success.", annotation.value(), noticeProperties.getEmailKey());
                } catch (Exception e) {
                    log.warn("Trigger [{}] email notice, but send email [{}] fail.", annotation.value(), noticeProperties.getEmailKey());
                }
            }
            if (noThrow){
                return result;
            }
            throw throwable;
        }
        return result;
    }

    private EmailNoticeTrigger findEmailNoticeTrigger(String beanName) {
        return emailNoticeTriggerMap.getOrDefault(beanName,
                emailNoticeTriggerMap.get(String.format("%s%s", beanName, EmailNoticeTrigger.class.getSimpleName())));
    }

}
