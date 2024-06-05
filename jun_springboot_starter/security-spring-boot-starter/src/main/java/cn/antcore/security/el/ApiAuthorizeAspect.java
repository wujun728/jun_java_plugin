package cn.antcore.security.el;

import cn.antcore.security.annotation.ApiAuthorize;
import cn.antcore.security.exception.AuthorizeNotConditionException;
import cn.antcore.security.web.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class ApiAuthorizeAspect {

    private ExpressionEvaluator<Boolean> evaluator = new ExpressionEvaluator<>();

    @Around("@annotation(cn.antcore.security.annotation.ApiAuthorize) || @annotation(cn.antcore.security.web.GetRest)" +
            "|| @annotation(cn.antcore.security.web.PostRest) || @annotation(cn.antcore.security.web.DeleteRest) " +
            "|| @annotation(cn.antcore.security.web.PutRest) || @annotation(cn.antcore.security.web.OptionsRest)" +
            "|| @annotation(cn.antcore.security.web.PatchRest) || @annotation(cn.antcore.security.web.TraceRest)" +
            "|| @annotation(cn.antcore.security.web.HeadRest)")
    public Object condition(ProceedingJoinPoint joinPoint) throws Throwable {
        Boolean conditionResult = getCondition(joinPoint); // 获取
        if (conditionResult) {
            return joinPoint.proceed();
        }
        throw new AuthorizeNotConditionException("条件未匹配");
    }

    /**
     * 获取注解
     *
     * @param joinPoint 连接点
     * @return 注解
     */
    private String getApiAuthorize(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiAuthorize authorize = method.getAnnotation(ApiAuthorize.class);
        if (authorize == null) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof GetRest) {
                    return ((GetRest) annotation).condition();
                } else if (annotation instanceof PostRest) {
                    return ((PostRest) annotation).condition();
                } else if (annotation instanceof PutRest) {
                    return ((PutRest) annotation).condition();
                } else if (annotation instanceof DeleteRest) {
                    return ((DeleteRest) annotation).condition();
                } else if (annotation instanceof OptionsRest) {
                    return ((OptionsRest) annotation).condition();
                } else if (annotation instanceof PatchRest) {
                    return ((PatchRest) annotation).condition();
                } else if (annotation instanceof TraceRest) {
                    return ((TraceRest) annotation).condition();
                } else if (annotation instanceof HeadRest) {
                    return ((HeadRest) annotation).condition();
                }
            }
        }
        return authorize.condition();
    }

    /**
     * 获取EL表达式执行后的值
     *
     * @param joinPoint
     * @return
     */
    private Boolean getCondition(JoinPoint joinPoint) {
        String condition = getApiAuthorize(joinPoint);
        if (StringUtils.isEmpty(condition)) {
            return true;
        }
        Object[] args = joinPoint.getArgs();
        if (args == null) {
            args = new Object[0];
        }
        EvaluationContext evaluationContext = evaluator.createEvaluationContext(joinPoint.getTarget(), joinPoint.getTarget().getClass(), ((MethodSignature) joinPoint.getSignature()).getMethod(), args);
        AnnotatedElementKey methodKey = new AnnotatedElementKey(((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getTarget().getClass());
        return evaluator.condition(condition, methodKey, evaluationContext, Boolean.class);
    }
}
