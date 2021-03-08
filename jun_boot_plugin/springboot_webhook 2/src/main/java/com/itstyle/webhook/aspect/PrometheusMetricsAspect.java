package com.itstyle.webhook.aspect;
import javax.servlet.http.HttpServletRequest;
import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Aspect
@Component
public class PrometheusMetricsAspect {
    private static final Counter requestTotal = Counter.build().name("couter_all").labelNames("api").help
            ("total request couter of api").register();
    private static final Counter requestError = Counter.build().name("couter_error").labelNames("api").help
            ("response Error couter of api").register();
    private static final Histogram histogram = Histogram.build().name("histogram_consuming").labelNames("api").help
            ("response consuming of api").register();

    @Pointcut("@annotation(com.itstyle.webhook.aspect.PrometheusMetrics)")
    public void pcMethod() {
    }

    @Around(value="pcMethod() && @annotation(annotation)")
    public Object MetricsCollector(ProceedingJoinPoint joinPoint, PrometheusMetrics annotation) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        PrometheusMetrics prometheusMetrics = methodSignature.getMethod().getAnnotation(PrometheusMetrics.class);
        if (prometheusMetrics != null) {
            String name;
            if (StringUtils.isNotEmpty(prometheusMetrics.name()) ){
                name = prometheusMetrics.name();
            }else{
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
                name = request.getRequestURI();
            }

            requestTotal.labels(name).inc();
            Histogram.Timer requestTimer = histogram.labels(name).startTimer();
            Object object;
            try {
                object = joinPoint.proceed();
            } catch (Exception e) {
                requestError.labels(name).inc();
                throw e;
            } finally {
                requestTimer.observeDuration();
            }
            return object;
        } else {
            return joinPoint.proceed();
        }
    }
}
